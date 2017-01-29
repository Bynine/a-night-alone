package entities;

import nw.Main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;

public class Statue extends Monster {

	public Statue(float posX, float posY) {
		super(posX, posY);
		stunImage = new Sprite(new Texture(Gdx.files.internal("sprites/statue.PNG")));
		walk = makeAnimation("sprites/statue.PNG", 1, 1, 20f, PlayMode.LOOP);
		image = new Sprite(walk.getKeyFrame(0));
		airSpeed = 0;
		jumpStrength = 0;
		radius = -radius - TILE*2;
		damage = 0;
		jumpTimer.setTime(0);
		stunTimer.setTime(0);
		
		if (Main.hardMode){
			
		}
	}
	
	@Override
	protected void handleTouchHelper(Entity e){
		return; // doesn't get touched
	}

}
