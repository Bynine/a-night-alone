package maps;

import nw.Main;

import com.badlogic.gdx.Gdx;

import entities.*;

public class Room_InsideChurch extends Room{

	public Room_InsideChurch(Level superLevel) {
		super(superLevel);
		map = tmxMapLoader.load("maps/insidechurch.tmx");
		startPosition.x = TILE * 4;
		startPosition.y = TILE * 15;
		roomMusic = Gdx.audio.newMusic(Gdx.files.internal("music/emptyecho3.mp3"));
		r = 0.05f;
		b = 0.1f;
		g = 0.1f;
		a = 0.3f;
		clouds = true;
	}
	
	@Override
	public void initEntities(Player player) {
		super.initEntities(player);
		if (notCat()) entityList.add(new Cat(TILE*7, TILE*15));
		if (!Main.gotShred3 && !Main.hardMode) entityList.add(new Shred(TILE*67, TILE*15, 3));
		entityList.add(new TrapFloor(TILE*23, TILE*14));
		entityList.add(new TrapFloor(TILE*35, TILE*14));
		entityList.add(new TrapFloor(TILE*48, TILE*14));
		entityList.add(new TrapFloor(TILE*56, TILE*14));
		entityList.add(new TrapFloor(TILE*67, TILE*14));
		
		if (Main.hardMode){
			entityList.add(new Dog(TILE*60, TILE*15));
		}
		
		entityList.add(new Portal(0, TILE*15, superLevel.getRoom(2), TILE*67, TILE*13));
		
		entityList.add(new Portal(TILE*23, 0, superLevel.getRoom(4), TILE*9, TILE*30));
		entityList.add(new Portal(TILE*24, 0, superLevel.getRoom(4), TILE*9, TILE*30));
		entityList.add(new Portal(TILE*25, 0, superLevel.getRoom(4), TILE*9, TILE*30));
		
		entityList.add(new Portal(TILE*35, 0, superLevel.getRoom(4), TILE*21, TILE*30));
		entityList.add(new Portal(TILE*36, 0, superLevel.getRoom(4), TILE*21, TILE*30));
		entityList.add(new Portal(TILE*37, 0, superLevel.getRoom(4), TILE*21, TILE*30));
		
		entityList.add(new Portal(TILE*48, 0, superLevel.getRoom(5), TILE*25, TILE*43));
		entityList.add(new Portal(TILE*49, 0, superLevel.getRoom(5), TILE*26, TILE*43));
		entityList.add(new Portal(TILE*50, 0, superLevel.getRoom(5), TILE*27, TILE*43));
		
		entityList.add(new Portal(TILE*56, 0, superLevel.getRoom(4), TILE*33, TILE*30));
		entityList.add(new Portal(TILE*57, 0, superLevel.getRoom(4), TILE*33, TILE*30));
		entityList.add(new Portal(TILE*58, 0, superLevel.getRoom(4), TILE*33, TILE*30));
		
		entityList.add(new Portal(TILE*67, 0, superLevel.getRoom(4), TILE*49, TILE*30));
		entityList.add(new Portal(TILE*68, 0, superLevel.getRoom(4), TILE*49, TILE*30));
		entityList.add(new Portal(TILE*69, 0, superLevel.getRoom(4), TILE*49, TILE*30));
		
		entityList.add(new Portal(TILE*94, 0, superLevel.getRoom(5), TILE*50, TILE*43));
		entityList.add(new Portal(TILE*95, 0, superLevel.getRoom(5), TILE*50, TILE*43));
	}

}
