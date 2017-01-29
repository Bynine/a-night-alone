package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Unbreakable extends Breakable{

	public Unbreakable(float posX, float posY) {
		super(posX, posY);
		image = new Sprite(new Texture(Gdx.files.internal("sprites/block.PNG")));
	}
	
	@Override
	protected void handleTouchHelper(Entity e){ /* doesn't break */ }
	
}
