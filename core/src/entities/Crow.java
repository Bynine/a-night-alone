package entities;

import java.util.List;

import com.badlogic.gdx.math.Rectangle;

public class Crow extends Monster {

	public Crow(float posX, float posY) {
		super(posX, posY);
		setup("sprites/slime.PNG", "sprites/slimestun.PNG");
	}
	
	@Override
	public void update(float f, List<Rectangle> rectangleList, List<Entity> entityList, Player p, int deltaTime){	
		super.update(f, rectangleList, entityList, p, deltaTime);
		
	}

}
