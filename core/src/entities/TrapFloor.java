package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import nw.Timer;

public class TrapFloor extends Entity{
	
	Timer life = new Timer(10, true);
	boolean started = false;
	private Sound breakNoise = Gdx.audio.newSound(Gdx.files.internal("sfx/break2.wav"));

	public TrapFloor(float posX, float posY) {
		super(posX, posY);
		timerList.add(life);
		image = new Sprite(new Texture(Gdx.files.internal("sprites/trapfloor.PNG")));
		collision = Collision.SOLID;
	}
	
	@Override
	protected void updatePosition(float f, Player p){ /* doesn't move */ }
	
	@Override
	protected void handleTouchHelper(Entity e){
		if (!started) life.countDown();
		if (e.getClass() == Player.class && !started) {
			if (isTouching(e, -2)){
				life.restart();
				started = true;
			}
		}
		
		if (isTouching(e, -1) && e.getClass() == Projectile.class){
			life.setTime(2);
			life.restart();
			started = true;
		}
		
		if (life.timeUp()) {
			breakNoise.play(0.75f);
			setRemove();
		}
	}

}
