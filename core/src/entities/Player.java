package entities;

import java.util.List;

import nw.Main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
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
	private Sound landNoise = Gdx.audio.newSound(Gdx.files.internal("sfx/land4.wav"));
	private float deadZone = .5f;
	private float noZone = .1f;
	boolean landed = true;
	private Animation walk = makeAnimation("sprites/playersheet.PNG", 4, 1, 10f, PlayMode.LOOP);

	public Player(Main nightWalk){
		super(16, 216);
		if (Main.gotCat) walk = makeAnimation("sprites/playersheetcat.PNG", 4, 1, 10f, PlayMode.LOOP);
		nw = nightWalk;
		image = new Sprite(walk.getKeyFrame(0));
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
		airSpeed = 0.3f;
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
	public void update(float f, List<Rectangle> rectangleList, List<Entity> entityList, Player p, int deltaTime){
		super.update(f, rectangleList, entityList, p, deltaTime);
		for (Entity e: entityList) checkPortal(e);
		if (isAerial()) state = State.AIR;
		else if (Math.abs(velocity.x) > 0.1) state = State.RUN;
		else state = State.STAND;

		if (state == State.RUN) setAnimation(walk, deltaTime);
		else setImage(image);
		
		if (isGrounded() && !jumpTimer.timeUp()) jump();

		if (isAerial()) landed = false;
		
		if (health < MAXHEALTH) health += recovery;
	}

	@Override
	protected void handleDirection(float f){
		if (Math.abs(f) < noZone) return;
		if (f < 0 && getDirection() == Direction.RIGHT) flip();
		else if (f > 0 && getDirection() == Direction.LEFT) flip();
	}

	@Override
	protected void handleMovement(float f){
		if (Math.abs(f) < deadZone) return;
		if (state == State.RUN && isGrounded()) velocity.x += f * getRunSpeed();
		else velocity.x += f * airSpeed;
	}

	public void setPosition(Vector2 startPosition) {
		if (velocity.y > 0)  velocity.y = 0;
		position.x = startPosition.x;	
		position.y = startPosition.y;
		updateImage();
	}

	public void hurt(float damage, Monster m) { 
		int yKnockback = 1;
		int xKnockback = 3;
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

}

