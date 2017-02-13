package entities;

import nw.Main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Breakable extends Entity{
	
	private boolean ghosts = true;
	private double ghostChance = 0.4;
	
	private Sound breakNoise = Gdx.audio.newSound(Gdx.files.internal("sfx/break3.wav"));

	public Breakable(float posX, float posY){
		super(posX, posY);
		image = new Sprite(new Texture(Gdx.files.internal("sprites/breakcross.PNG")));
		collision = Collision.SOLID;
		if (Main.hardMode) ghostChance = 0.6;
	}

	@Override
	protected void updatePosition(float f, Player p){ /* doesn't move */ }

	@Override
	protected void handleTouchHelper(Entity e){
		if (isTouching(e, -1) && e instanceof Projectile){
			breakNoise.play(0.75f);
			for (int i = 0; i < 5; ++i){
				Main.addEntity(new Chunk(position.x + image.getWidth()/2, position.y + image.getHeight()/2));
			}
			if(Math.random() < ghostChance && ghosts){
				//Main.addEntity(new Ghost(position.x + image.getWidth()/2, position.y - 16));
			}
			e.setRemove();
			setRemove();
		}
	}
	
	public void noGhosts(){
		ghosts = false;
	}

}
