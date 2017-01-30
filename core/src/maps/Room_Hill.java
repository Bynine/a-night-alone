package maps;

import nw.Main;

import com.badlogic.gdx.Gdx;

import entities.Breakable;
import entities.Cat;
import entities.Player;
import entities.Portal;
import entities.Slime;
import entities.Zombie;

public class Room_Hill extends Room {
	
	public Room_Hill(Level superLevel) {
		super(superLevel);
		map = tmxMapLoader.load("maps/hill.tmx");
		startPosition.x = TILE * 4;
		startPosition.y = TILE * 6;
		roomMusic = Gdx.audio.newMusic(Gdx.files.internal("music/emptyecho2.mp3"));
		clouds = true;
	}
	
	@Override
	public void initEntities(Player player) {
		super.initEntities(player);
		if (notCat()) {
			Cat c = new Cat(TILE*13, TILE*7);
			c.jump();
			c.prepJump();
			entityList.add(c);
		}
		
		entityList.add(new Breakable(TILE*8, TILE*6));
		entityList.add(new Breakable(TILE*10, TILE*6));
		entityList.add(new Breakable(TILE*42, TILE*26));
		entityList.add(new Breakable(TILE*45, TILE*26));
		
		if (Main.hardMode){
			entityList.add(new Zombie(TILE*13, TILE*7));
			entityList.add(new Zombie(TILE*18, TILE*11));
			entityList.add(new Slime(TILE*25, TILE*14));
			entityList.add(new Slime(TILE*31, TILE*14));
			entityList.add(new Zombie(TILE*27, TILE*19));
			entityList.add(new Zombie(TILE*43, TILE*26));
			entityList.add(new Zombie(TILE*38, TILE*26));
		}
		
		entityList.add(new Portal(0, TILE*6, superLevel.getRoom(1), TILE*151, TILE*41));
		entityList.add(new Portal(TILE*51, TILE*26, superLevel.getRoom(2), TILE*3, TILE*13));
	}

}