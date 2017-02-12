package entities;

import java.util.List;

import nw.Main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import nw.Timer;

public class Player extends Entity{

	Main nw;

	private float health = 3;
	public final float MAXHEALTH = 3;
	private final float recovery = 0.0025f;
	private Timer invincible = new Timer(30, false);
	private Timer jumpTimer = new Timer(6, false);
	private Sound hurtNoise = Gdx.audio.newSound(Gdx.files.internal("sfx/hurt.wav"));
	private Sound landNoise = Gdx.audio.newSound(Gdx.files.internal("sfx/land6.wav"));
	private float deadZone = .5f;
	private float noZone = .2f;
	boolean landed = true;
	private Animation walk = makeAnimation("sprites/playersheet.PNG", 4, 1, 10f, PlayMode.LOOP);
	private TextureRegion crouchImage = new TextureRegion(new Texture(Gdx.files.internal("sprites/playercrouch.PNG")));
	private TextureRegion jumpImage = new TextureRegion(new Texture(Gdx.files.internal("sprites/playerjump.PNG")));
	private Sprite standImage;

	public Player(Main nightWalk){
		super(16, 216);
		if (Main.gotCat) {
			walk = makeAnimation("sprites/playersheetcat.PNG", 4, 1, 10f, PlayMode.LOOP);
			crouchImage = new TextureRegion(new Texture(Gdx.files.internal("sprites/playercrouchcat.PNG")));
			jumpImage = new TextureRegion(new Texture(Gdx.files.internal("sprites/playerjumpcat.PNG")));
		}
		nw = nightWalk;
		image = new Sprite(walk.getKeyFrame(0));
		standImage = new Sprite(walk.getKeyFrame(0));
		position.x = TILE * 40;
		position.y = TILE * 40;
		velocity.x = 0;
		velocity.y = 0;
		state = State.STAND;
		direction = Direction.RIGHT;
		collision = Collision.CREATURE;
		timerList.add(invincible);
		timerList.add(jumpTimer);

		jumpStrength = 5.3f;
		gravity = -0.32f;
		runSpeed = 0.3f;
		airSpeed = 0.31f;
	}

	private void checkPortal(Entity e){
		if (e.getClass() == Portal.class && this.isTouching(e, 0)){
			Portal p = (Portal) e;
			if (p.cutscene) nw.transition(p.getRoom(), p.getDestination(this), true);
			else nw.transition(p.getRoom(), p.getDestination(this), false);
		}
	}

	public void prepJump(){
		if (jumpTimer.timeUp()) jumpTimer.restart();
	}

	@Override
	public void jump(){
		if (isCrouching()) return;
		getVelocity().y += getJumpStrength();
		state = State.AIR;
		jumpTimer.stop();
	}

	@Override
	public void ground(){
		super.ground();
		if (velocity.y < -5f && !landed){
			landNoise.stop();
			landNoise.play(0.4f);
		}
		landed = true;
	}

	@Override
	public void update(float f, List<Rectangle> rectangleList, List<Entity> entityList, Player p, int deltaTime, boolean down){
		for (Entity e: entityList) checkPortal(e);
		
		changeState(down);
		makeHeadRoom();
		
		super.update(f, rectangleList, entityList, p, deltaTime, down);
		
		switch(state){
		case RUN: setAnimation(walk, deltaTime); break;
		case AIR: setImage(jumpImage); break;
		case CROUCH: case CROUCHAIR: setImage(crouchImage); break;
		default: setImage(standImage); break;
		}
		
		if (isGrounded() && !jumpTimer.timeUp()) jump();
		if (isAerial()) landed = false;
		if (health < MAXHEALTH) health += recovery;
		
		System.out.println(state);
	}
	
	void makeHeadRoom(){
		boolean a = checkCollision(position.x, position.y + 16);
		boolean b = checkCollision(position.x, position.y - 8);
		if (state == State.STAND && a && !b){
			state = State.CROUCHAIR;
			setImage(crouchImage);
		}
	}
	
	void changeState(boolean down){
		if (isAerial()) {
			if (state == State.CROUCH) state = State.CROUCHAIR;
			else state = State.AIR;
			return;
		}
		else if ((state == State.CROUCH || state == State.CROUCHAIR) && down) return;
		else if (Math.abs(velocity.x) > 0.1) state = State.RUN;
		else state = State.STAND;
	}

	@Override
	protected void handleDirection(float f){
		if (Math.abs(f) < noZone) return;
		if (f < 0 && getDirection() == Direction.RIGHT) flip();
		else if (f > 0 && getDirection() == Direction.LEFT) flip();
	}

	@Override
	protected void handleMovement(float f){
		if (state == State.CROUCH || Math.abs(f) < deadZone) return;
		else if (state == State.RUN && isGrounded()) velocity.x += f * getRunSpeed();
		else velocity.x += f * airSpeed;
	}

	public void setPosition(Vector2 startPosition) {
		if (velocity.y > 0)  velocity.y = 0;
		position.x = startPosition.x;	
		position.y = startPosition.y;
		updateImage(0);
	}

	public void hurt(float damage, Monster m) { 
		float yKnockback = 1.2f;
		float xKnockback = 3.6f;
		if (isCrouching()){
			yKnockback = 0.5f;
			xKnockback = 1.5f;
		}
		if (invincible.timeUp()){
			velocity.y += yKnockback;
			if (m.getPosition().x < position.x) velocity.x = xKnockback;
			else velocity.x = -xKnockback;
			hurtNoise.play();
			health -= damage;
			invincible.restart();
		}
	}

	public void reset(){
		health = MAXHEALTH;
		velocity.x = 0;
		velocity.y = 0;
	}

	public void putCatOn(){
		walk = makeAnimation("sprites/playersheetcat.PNG", 4, 1, 10f, PlayMode.LOOP);
	}

	public float getHealth() { return health; }

	public boolean setCrouch(){
		if (!isAerial()) state = State.CROUCH;
		else state = State.CROUCHAIR;
		return !isAerial();
	}
	
	@Override
	protected Rectangle getHurtBox(float x, float y){
		int thin = 2;
		Rectangle r = image.getBoundingRectangle();
		r.setWidth(r.getWidth() - thin);
		r.setX(x + thin/2);
		r.setY(y);
		return r;
	}

}

