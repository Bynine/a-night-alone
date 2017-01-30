package maps;

import nw.Main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import entities.Foul;
import entities.Player;
import entities.Portal;
import entities.Slime;
import entities.Unbreakable;

public class Room_Catacombs extends Room {
	
	Sound meowEcho = Gdx.audio.newSound(Gdx.files.internal("sfx/meowecho.wav"));

	public Room_Catacombs(Level superLevel) {
		super(superLevel);
		map = tmxMapLoader.load("maps/catacombs.tmx");
		roomMusic = Gdx.audio.newMusic(Gdx.files.internal("music/dark.mp3"));
		bigBorder = true;
		r = 0.1f;
		b = 0.1f;
		g = 0.1f;
		a = 0.45f;
		startPosition.x = TILE * 61;
		startPosition.y = TILE * 5;
	}
	
	@Override
	public void initEntities(Player player) {
		super.initEntities(player);
		if (notCat()) meowEcho.play();
		entityList.add(new Slime(TILE*24, TILE*17));
		entityList.add(new Slime(TILE*26, TILE*17));
		entityList.add(new Foul(TILE*10, TILE*31));
		entityList.add(new Foul(TILE*30, TILE*4));
		entityList.add(new Foul(TILE*23, TILE*12));
		entityList.add(new Foul(TILE*37, TILE*12));
		entityList.add(new Slime(TILE*18, TILE*12));
		entityList.add(new Slime(TILE*20, TILE*12));
		entityList.add(new Foul(TILE*43, TILE*5));
		entityList.add(new Foul(TILE*48, TILE*5));
		entityList.add(new Foul(TILE*51, TILE*5));
		entityList.add(new Foul(TILE*12, TILE*20));
		entityList.add(new Foul(TILE*41, TILE*21));
		
		if (Main.hardMode){
			entityList.add(new Foul(TILE*41, TILE*35));
			entityList.add(new Foul(TILE*45, TILE*35));
			
			entityList.add(new Foul(TILE*27, TILE*17));
			entityList.add(new Foul(TILE*29, TILE*17));
			entityList.add(new Foul(TILE*45, TILE*5));
			entityList.add(new Foul(TILE*50, TILE*5));
			entityList.add(new Slime(TILE*11, TILE*8));
			entityList.add(new Slime(TILE*13, TILE*8));
			entityList.add(new Unbreakable(TILE*6, TILE*14));
			entityList.add(new Unbreakable(TILE*7, TILE*14));
		}
		
		else{
			entityList.add(new Unbreakable(TILE*24, TILE*11));
			entityList.add(new Unbreakable(TILE*24, TILE*10));
			entityList.add(new Unbreakable(TILE*24, TILE*9));
		}
		
		if (Main.gotCat){
			entityList.add(new Unbreakable(TILE*53, TILE*5));
			entityList.add(new Unbreakable(TILE*53, TILE*6));
			entityList.add(new Unbreakable(TILE*54, TILE*5));
			entityList.add(new Unbreakable(TILE*62, TILE*5));
			entityList.add(new Unbreakable(TILE*62, TILE*6));
		}
		
		else{
			entityList.add(new Unbreakable(TILE*54, TILE*7));
			entityList.add(new Unbreakable(TILE*55, TILE*7));
			entityList.add(new Unbreakable(TILE*56, TILE*7));
		}
		
		Portal altarPortal = new Portal(TILE*64, TILE*5, superLevel.getRoom(5), TILE*61, TILE*5);
		altarPortal.doesCutscene();
		altarPortal.doesSetCat();
		entityList.add(altarPortal);
		
		entityList.add(new Portal(TILE*5, TILE*44, superLevel.getRoom(3), TILE*7, TILE*21));
		entityList.add(new Portal(TILE*6, TILE*44, superLevel.getRoom(3), TILE*7, TILE*21));
		entityList.add(new Portal(TILE*7, TILE*44, superLevel.getRoom(3), TILE*7, TILE*21));
		
		entityList.add(new Portal(TILE*54, TILE*44, superLevel.getRoom(4), TILE*70, TILE*3));
		entityList.add(new Portal(TILE*55, TILE*44, superLevel.getRoom(4), TILE*70, TILE*3));
		entityList.add(new Portal(TILE*56, TILE*44, superLevel.getRoom(4), TILE*70, TILE*3));
	}

}
