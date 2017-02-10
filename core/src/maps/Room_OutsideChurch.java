package maps;

import nw.Main;

import com.badlogic.gdx.Gdx;

import entities.*;

public class Room_OutsideChurch extends Room {

	public Room_OutsideChurch(Level superLevel) {
		super(superLevel);
		map = tmxMapLoader.load("maps/outsidechurch.tmx");
		roomMusic = Gdx.audio.newMusic(Gdx.files.internal("music/emptyecho1.mp3"));
		startPosition.x = TILE * 4;
		startPosition.y = TILE * 13;
		clouds = true;
	}

	@Override
	public void initEntities(Player player) {
		super.initEntities(player);
		
		if (notCat()){
			Cat cat = new Cat(TILE*8, TILE*13);
			entityList.add(cat);
			cat.jump();
			cat.prepJump();
		}
		
		if (Main.hardMode){
			entityList.add(new Slime(TILE*14, TILE*13));
			entityList.add(new Zombie(TILE*17, TILE*13));
			entityList.add(new Slime(TILE*18, TILE*13));
			entityList.add(new Slime(TILE*21, TILE*13));
			entityList.add(new Slime(TILE*25, TILE*13));
			entityList.add(new Slime(TILE*28, TILE*13));
			entityList.add(new Zombie(TILE*31, TILE*13));
			entityList.add(new Slime(TILE*32, TILE*13));
			entityList.add(new Slime(TILE*35, TILE*13));
			entityList.add(new Zombie(TILE*37, TILE*13));
			entityList.add(new Slime(TILE*39, TILE*13));
			entityList.add(new Zombie(TILE*41, TILE*13));
			entityList.add(new Zombie(TILE*60, TILE*20));
			entityList.add(new Slime(TILE*59, TILE*20));
			entityList.add(new Zombie(TILE*62, TILE*23));
		}
		
		Monster statue = new Statue(TILE*48, TILE*14);
		statue.flip();
		entityList.add(statue);
		entityList.add(new Portal(0, TILE*13, superLevel.getRoom(6), TILE*48, TILE*26));
		entityList.add(new Portal(TILE*69, TILE*13, superLevel.getRoom(3), TILE*4, TILE*15));
		entityList.add(new Breakable(TILE*15, TILE*13));
		entityList.add(new Breakable(TILE*19, TILE*13));
		entityList.add(new Breakable(TILE*22, TILE*13));
		entityList.add(new Breakable(TILE*26, TILE*13));
		entityList.add(new Breakable(TILE*29, TILE*13));
		entityList.add(new Breakable(TILE*33, TILE*13));
		entityList.add(new Breakable(TILE*36, TILE*13));
		entityList.add(new Breakable(TILE*40, TILE*13));
	}
}
