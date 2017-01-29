package entities;

import nw.Main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Breakable extends Entity{
	
	private Sound breakNoise = Gdx.audio.newSound(Gdx.files.internal("sfx/break3.wav"));

	public Breakable(float posX, float posY){
		super(posX, posY);
		image = new Sprite(new Texture(Gdx.files.internal("sprites/breakcross.PNG")));
		collision = Collision.SOLID;
	}

	@Override
	protected void updatePosition(float f, Player p){ /* doesn't move */ }

	@Override
	protected void handleTouchHelper(Entity e){
		if (isTouching(e, -1) && e.getClass() == Projectile.class){
			breakNoise.play();
			for (int i = 0; i < 5; ++i){
				Main.addEntity(new Chunk(position.x + image.getWidth()/2, position.y + image.getHeight()/2));
			}
			e.setRemove();
			setRemove();
		}
	}

}
