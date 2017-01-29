package entities;

import nw.Main;
import nw.Timer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class HardModeActivator extends Entity{

	private Timer wait = new Timer(180, true);
	private Sound unlock = Gdx.audio.newSound(Gdx.files.internal("sfx/collect.wav")); // TODO: unlock SFX
	private boolean unlocked = false;

	public HardModeActivator(float x, float y){
		super(x, y);
		image = new Sprite(new Texture(Gdx.files.internal("sprites/portal.PNG")));
	}

	@Override
	protected void updatePosition(float f, Player p){ 
		if (wait.timeUp() && !unlocked && !Main.hardMode){
			Main.setHardMode();
			unlock.play(0.5f);
			unlocked = true;
		}
	}


	@Override
	protected void handleTouchHelper(Entity e){
		if (e.isTouching(this, 0) && e instanceof Player){
			wait.countUp();
		}
	}

}
