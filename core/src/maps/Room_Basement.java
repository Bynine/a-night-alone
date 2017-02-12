package maps;

import nw.Main;

import com.badlogic.gdx.Gdx;

import entities.Foul;
import entities.Player;
import entities.Portal;
import entities.Slime;
import entities.Unbreakable;

public class Room_Basement extends Room {
	
	private final int groundFloor = 8;
	private final int ceiling = 31;

	public Room_Basement(Level superLevel) {
		super(superLevel);
		startPosition.x = TILE * 70;
		startPosition.y = TILE * (groundFloor-5);
		map = tmxMapLoader.load("maps/basement.tmx");
		roomMusic = Gdx.audio.newMusic(Gdx.files.internal("music/panic.mp3"));
		r = 0.0f;
		b = 0.3f;
		g = 0.1f;
		a = 0.45f;
	}
	
	@Override
	public void initEntities(Player player) {
		super.initEntities(player);
		entityList.add(new Foul(TILE*4, TILE*groundFloor));
		entityList.add(new Foul(TILE*16, TILE*groundFloor));
		entityList.add(new Foul(TILE*23, TILE*groundFloor));
		entityList.add(new Foul(TILE*30, TILE*groundFloor));
		entityList.add(new Foul(TILE*39, TILE*groundFloor));
		entityList.add(new Foul(TILE*45, TILE*groundFloor));
		
		if (Main.hardMode){
			entityList.add(new Foul(TILE*6, TILE*groundFloor));
			entityList.add(new Foul(TILE*36, TILE*groundFloor));
			entityList.add(new Foul(TILE*49, TILE*groundFloor));
			entityList.add(new Slime(TILE*6, TILE*(groundFloor+10)));
			entityList.add(new Slime(TILE*6, TILE*(groundFloor+15)));
			entityList.add(new Slime(TILE*19, TILE*(groundFloor+8)));
			entityList.add(new Slime(TILE*25, TILE*(groundFloor+7)));
			entityList.add(new Slime(TILE*31, TILE*(groundFloor+6)));
			entityList.add(new Slime(TILE*37, TILE*(groundFloor+5)));
			entityList.add(new Slime(TILE*50, TILE*(groundFloor+10)));
			entityList.add(new Foul(TILE*46, TILE*(groundFloor-5)));
			entityList.add(new Foul(TILE*50, TILE*(groundFloor-5)));
			entityList.add(new Foul(TILE*54, TILE*(groundFloor-5)));
		}
		
		else{
			entityList.add(new Unbreakable(TILE*54, TILE*(groundFloor-5)));
			entityList.add(new Unbreakable(TILE*54, TILE*(groundFloor-4)));
		}
		
		entityList.add(new Portal(TILE*6, TILE*ceiling, superLevel.getRoom(3), TILE*7, TILE*21));
		entityList.add(new Portal(TILE*7, TILE*ceiling, superLevel.getRoom(3), TILE*7, TILE*21));
		entityList.add(new Portal(TILE*8, TILE*ceiling, superLevel.getRoom(3), TILE*7, TILE*21));
		
		entityList.add(new Portal(TILE*74, 0, superLevel.getRoom(5), TILE*55, TILE*41));
		entityList.add(new Portal(TILE*75, 0, superLevel.getRoom(5), TILE*56, TILE*41));
	}
	
}
