package entities;

import java.util.List;

import nw.Timer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.Rectangle;

public class Cat extends Entity {

	private Animation walk = makeAnimation("sprites/cat.PNG", 2, 1, 8f, PlayMode.LOOP);
	Timer life = new Timer(60, true);
	Timer jump = new Timer(70, true);
	Sound meow = Gdx.audio.newSound(Gdx.files.internal("sfx/meow.wav"));

	public Cat(float posX, float posY) {
		super(posX, posY);
		image = new Sprite(walk.getKeyFrame(0));
		airSpeed = 0.54f;
		timerList.add(life);
		timerList.add(jump);
	}

	@Override
	public void update(float f, List<Rectangle> rectangleList, List<Entity> entityList, Player p, int deltaTime, boolean b){
		if (life.getTime() == 5) meow.play();
		super.update(f, rectangleList, entityList, p, deltaTime, false);
		setAnimation(walk, deltaTime);
		if (life.timeUp()) setRemove();
		if (jump.timeUp()) {
			jump.restart();
			jump();
		}
	}

	@Override
	public void jump(){
		getVelocity().y += getJumpStrength();
		state = State.AIR;
	}
	
	public void prepJump(){
		jump.setTime(30);
	}

}
