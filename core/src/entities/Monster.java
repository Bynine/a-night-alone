package entities;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.Rectangle;

import nw.Timer;
import nw.RandomTimer;

public abstract class Monster extends Entity{

	protected Timer stunTimer = new Timer(120, false);
	protected Timer turnTimer = new Timer(30, false);
	protected Timer speedModTimer = new RandomTimer(30, true);
	protected Timer jumpTimer = new RandomTimer(40, true);
	private float speedMultiplier = 1f;
	protected Sprite stunImage;
	protected float oneBlock = 3.85f;
	protected float twoBlock = 6f;
	protected int radius = 8 * TILE;
	private int buttTouchCounter = 0;
	protected float damage = 0;
	private float jumpSensitivity = .5f;
	private boolean buttTouch = false;
	protected Animation walk;
	private Sound stunNoise = Gdx.audio.newSound(Gdx.files.internal("sfx/break4.wav"));

	public Monster(float posX, float posY){
		super(posX, posY);
		timerList.addAll(Arrays.asList(stunTimer, turnTimer, speedModTimer, jumpTimer));
		state = State.RUN;
		runSpeed = 0.01f;
		friction = 0.5f;
		collision = Collision.CREATURE;
	}

	public void reactToPlayer(Player p){
		if (radius < 0 && Math.abs(p.position.x - position.x) < -radius) {
			return;
		}
		if (turnTimer.timeUp() && stunTimer.timeUp()){
			turnTimer.restart();
			if (p.getPosition().x < position.x && direction == Direction.RIGHT) flip();
			else if (p.getPosition().x > position.x && direction == Direction.LEFT) flip();
			if (Math.abs(velocity.x) < airSpeed*(jumpSensitivity) && jumpTimer.timeUp() && !isTouching(p, 0) ) {
				jumpTimer.restart();
				jump();
			}
		}
	}

	private boolean isThisCloseTo(Entity e){
		return Math.abs(e.position.x - position.x) < radius && Math.abs(e.position.y - position.y) < radius;
	}

	@Override
	public void update(float f, List<Rectangle> rectangleList, List<Entity> entityList, Player p, int deltaTime){		
		reactToPlayer(p);
		if (speedModTimer.timeUp()){
			speedMod();
			speedModTimer.restart();
			buttTouchCounter = 0;
		}
		super.update(f, rectangleList, entityList, p, deltaTime);
		if (buttTouchCounter > 0) buttTouch = true;
		else buttTouch = false;
		
		if (!stunTimer.timeUp()) {	
			setImage(stunImage);
			if (direction == Direction.LEFT) image.flip(true, false);
		}
		else setAnimation(walk, deltaTime);
	}

	@Override
	protected void updatePosition(float f, Player p){
		if (isThisCloseTo(p)) position.x += velocity.x;	
		position.y += velocity.y;
	}

	@Override
	protected void handleMovement(float f){
		if (stunTimer.timeUp()) super.handleMovement(f);
		if (!stunTimer.timeUp()) {
			jumpTimer.restart();
			velocity.x = 0;
		}
		velocity.x *= speedMultiplier;
	}

	@Override
	protected void handleTouchHelper(Entity e){
		super.handleTouchHelper(e);
		if (stunTimer.timeUp() && e instanceof Projectile) {
			if (isTouching(e, 0)){
				stunNoise.play(0.5f);
				e.setRemove();
				stunTimer.restart();
			}
		}
		if (stunTimer.timeUp() && e instanceof Player) {
			if (isTouching(e, 4)){
				Player p = (Player) e;
				p.hurt(damage, this);
			}
		}
		if (e instanceof Monster && e != this) {
			Monster m = (Monster) e;
			if (isTouching(e, 0) && m.getClass() == this.getClass()){	
				buttTouchCounter++;
			}
		}
	}
	
	protected void speedMod(){
		if (buttTouch) speedMultiplier = (float) (.5 + (Math.random()*2)/3);
		else speedMultiplier = (float) (0.875 + Math.random()/4);
	}
	
	protected void setup(String normImageStr, String stunImageStr){
		stunImage = new Sprite(new Texture(Gdx.files.internal(stunImageStr)));
		walk = makeAnimation(normImageStr, 2, 1, 20f, PlayMode.LOOP);
		image = new Sprite(walk.getKeyFrame(0));
	}

}
