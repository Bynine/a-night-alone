package entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nw.Timer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {
	public static final int TILE = 16;
	protected final Vector2 position = new Vector2();
	protected final Vector2 velocity = new Vector2();
	public State state;
	public Direction direction = Direction.RIGHT;
	protected Sprite image;
	protected float gravity = -0.4f;
	protected float jumpStrength = 6f;
	protected float runSpeed = 0.32f;
	protected float airSpeed = 0.31f;
	protected float friction = 0.87f;
	protected float crouchFriction = 0.75f;
	protected Collision collision;
	protected final int collisionCheck = 4;
	protected final float softening = .8f;
	protected final float terminalVelocity = -8f;
	protected boolean toRemove = false;
	public boolean inFront = false;
	final List<Rectangle> tempRectangleList = new ArrayList<Rectangle>();
	final List<Timer> timerList = new ArrayList<Timer>();

	public Entity(float posX, float posY){
		position.x = posX;
		position.y = posY;
	}

	public void update(float f, List<Rectangle> rectangleList, List<Entity> entityList, Player p, int deltaTime, boolean down){
		handleDirection(f);
		handleMovement(f);
		limitingForces(rectangleList, entityList);
		handleTouch(entityList);
		updateTimers();
		updatePosition(f, p);
		updateImage(deltaTime);
	}

	protected void handleDirection(float f){
		//
	}

	protected void handleMovement(float f){
		if (state == State.RUN && isGrounded()) velocity.x += direct() * runSpeed;
		else velocity.x += direct() * airSpeed;
	}

	private void handleTouch(List<Entity> entityList){
		for (Entity e: entityList){
			handleTouchHelper(e);
		}
	}

	protected void handleTouchHelper(Entity e){
		/* */
	}

	protected void updatePosition(float f, Player p){
		position.x += velocity.x;
		position.y += velocity.y;
	}

	protected void updateImage(int deltaTime){
		image.setX(position.x);
		image.setY(position.y);
	}

	protected void updateTimers(){
		for (Timer t: timerList) t.countUp();
	}

	protected void limitingForces(List<Rectangle> mapRectangleList, List<Entity> entityList){
		velocity.y += gravity;
		if (state == State.CROUCH) velocity.x *= crouchFriction; 
		else velocity.x *= friction;
		if (velocity.y < terminalVelocity) velocity.y = terminalVelocity;
		setupRectangles(mapRectangleList, entityList);
		checkWalls();
		checkFloor();
	}

	protected void setupRectangles(List<Rectangle> mapRectangleList, List<Entity> entityList){
		tempRectangleList.clear();
		tempRectangleList.addAll(mapRectangleList);
		for (Entity en: entityList){
			if (en.getCollision() == Collision.SOLID) {
				tempRectangleList.add(en.getImage().getBoundingRectangle());
			}
		}
	}

	protected void checkWalls(){
		for (int i = 0; i < collisionCheck; ++i)
			if (checkCollision(position.x + velocity.x, position.y)) {
				velocity.x *= softening;
			}
		if (checkCollision(position.x + velocity.x, position.y)) {
			velocity.x = 0;
		}
	}

	protected void checkFloor(){
		for (int i = 0; i < collisionCheck; ++i)
			if (checkCollision(position.x, position.y + velocity.y)) {
				if (velocity.y < 0 && isAerial()) ground();
				velocity.y *= softening;
			}
		if (checkCollision(position.x, position.y + velocity.y)) velocity.y = 0;
		if (checkCollision(position.x + velocity.x, position.y + velocity.y)) velocity.y = 0; // checks for diagonal floor
	}

	protected boolean checkCollision(float x, float y){
		if (collision == Collision.GHOST) return false;
		boolean ignoreRectangle;
		for (Rectangle r : tempRectangleList){
			ignoreRectangle = false;
			Rectangle thisR = getHurtBox(x, y);
			if (r.getHeight() <= 1 && r.getY() - this.getPosition().y > 0) ignoreRectangle = true;
			if (!ignoreRectangle && Intersector.overlaps(thisR, r) && thisR != r) return true;
		}
		return false;
	}
	
	protected Rectangle getHurtBox(float x, float y){
		Rectangle r = image.getBoundingRectangle();
		r.setX(x);
		r.setY(y);
		return r;
	}

	public void flip(){
		if (direction == Direction.LEFT){
			setDirection(Direction.RIGHT);
			image.setFlip(false, false);
		}
		else{
			setDirection(Direction.LEFT);
			image.setFlip(true, false);
		}
	}
	
	public void ground(){
		state = State.STAND;
	}

	public void jump(){
		if (isGrounded()){
			getVelocity().y += getJumpStrength();
			state = State.AIR;
		}
	}
	
	protected void setAnimation(Animation ani, int deltaTime){
		image.setRegion(ani.getKeyFrame(deltaTime));
		if (direction == Direction.LEFT) image.flip(true, false);
	}
	
	protected void setImage(TextureRegion tr){
		boolean flipped = image.isFlipX();
		float x = image.getX();
		float y = image.getY();
		image = new Sprite(tr);
		image.setFlip(flipped, false);
		image.setX(x);
		image.setY(y);
	}

	protected int direct(){
		if (direction == Direction.RIGHT) return 1;
		else return -1;
	}

	public boolean isOOB(int mapWidth, int mapHeight) {
		int OOBGrace = 2;
		if (position.x < (0 - image.getWidth()*OOBGrace) || (mapWidth + image.getWidth()*OOBGrace) < position.x) return true;
		if (position.y < (0 - image.getHeight()*OOBGrace) || (mapHeight + image.getHeight()*OOBGrace) < position.y) return true;
		return false;
	}

	public boolean isTouching(Entity en, int decrement){
		Rectangle hitboxRect = en.getImage().getBoundingRectangle();
		hitboxRect.setWidth(hitboxRect.getWidth() - decrement);
		hitboxRect.setHeight(hitboxRect.getHeight() - decrement);
		hitboxRect.setX(hitboxRect.getX() + decrement/2);
		hitboxRect.setY(hitboxRect.getY() + decrement/2);
		return Intersector.overlaps(image.getBoundingRectangle(), hitboxRect);
	}
	
	public static Animation makeAnimation(String address, int cols, int rows, float speed, PlayMode playMode){
		Texture sheet = new Texture(Gdx.files.internal(address));
		TextureRegion[][] tmp = TextureRegion.split(sheet, sheet.getWidth()/cols, sheet.getHeight()/rows);
		TextureRegion[] frames = new TextureRegion[cols * rows];
		int index = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				frames[index++] = tmp[i][j];
			}
		}
		Animation animation = new Animation(speed, frames);
		animation.setPlayMode(playMode);
		return animation;
	}

	public Vector2 getPosition() { return position; }
	public Vector2 getVelocity() { return velocity; }
	public Direction getDirection() { return direction; }
	public void setDirection (Direction d) { direction = d; }
	public Collision getCollision() { return collision; }
	public Sprite getImage() { return image; }
	public void setRemove() { toRemove = true; }
	public boolean toRemove() { return toRemove; }
	public float getGravity() { return gravity; }
	public float getJumpStrength() { return jumpStrength; }
	public float getRunSpeed() { return runSpeed; }
	public float getAirSpeed() { return airSpeed; }
	public float getFriction() { return friction; }

	public enum Direction{ LEFT, RIGHT }
	public enum State{ STAND, RUN, AIR, CROUCH, CROUCHAIR }
	enum Collision{ SOLID, CREATURE, GHOST }

	private final ArrayList<State> groundedStates = new ArrayList<State>(Arrays.asList(State.STAND, State.RUN, State.CROUCH)); 
	private final ArrayList<State> aerialStates = new ArrayList<State>(Arrays.asList(State.AIR, State.CROUCHAIR)); 
	private final ArrayList<State> crouchStates = new ArrayList<State>(Arrays.asList(State.CROUCH, State.CROUCHAIR)); 
	public boolean isGrounded(){ return groundedStates.contains(state) && velocity.y == 0; }
	private final int aboveGround = 1;
	public boolean isAerial(){ return aerialStates.contains(state) || 
			(checkCollision(position.x, position.y + aboveGround) && checkCollision(position.x - 14, position.y + aboveGround)); }
	public boolean isCrouching() { return crouchStates.contains(state); }

}
