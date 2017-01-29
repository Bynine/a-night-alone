package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Heart extends Entity{

	public Heart(float posX, float posY) {
		super(posX, posY);
		image = new Sprite(new Texture(Gdx.files.internal("sprites/heart.PNG")));
	}

	protected void handleTouchHelper(Entity e){
		if (e.getClass() == Player.class) {
			if (isTouching(e, 2)){
				Player p = (Player) e;
				p.heal();
			}
		}
	}

}
