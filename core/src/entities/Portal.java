package entities;

import nw.Main;
import maps.Room;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Portal extends Entity {
	private Room room;
	protected Vector2 destination = new Vector2();
	private boolean setCat;
	boolean cutscene = false;

	public Portal(float x, float y, Room room, float desX, float desY){
		super(x, y);
		image = new Sprite(new Texture(Gdx.files.internal("sprites/portal.PNG")));
		this.room = room;
		destination.x = desX;
		destination.y = desY;
	}

	@Override
	protected void updatePosition(float f, Player p){ /* doesn't move */ }

	public Room getRoom(){ 
		return room; 
	}

	public Vector2 getDestination(Player p){ 
		if (setCat) {
			Main.gotCat = true;
			p.putCatOn();
		}
		if (cutscene){
			Main.setGameState(Main.GameState.CUTSCENE);
		}
		return destination; 
	}

	public void doesSetCat(){
		setCat = true;
	}
	
	public void doesCutscene(){
		cutscene = true;
	}

}
