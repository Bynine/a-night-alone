package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Window extends Entity {

	public Window(float posX, float posY) {
		super(posX, posY);
		image = new Sprite(new Texture(Gdx.files.internal("sprites/window.PNG")));
	}
	
	@Override
	protected void updatePosition(float f, Player p){ /* doesn't move */ }
	
	@Override
	protected void handleTouchHelper(Entity e){ /* doesn't break */ }

}
