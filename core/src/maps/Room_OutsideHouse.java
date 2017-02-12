package maps;

import nw.Main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import entities.*;

public class Room_OutsideHouse extends Room{

	int baseLevel = 17;
	Cat c = new Cat(TILE*28, TILE*baseLevel);
	int makeCat = 3;

	public Room_OutsideHouse(Level superLevel) {
		super(superLevel);
		startPosition.x = TILE * 26;
		startPosition.y = TILE * baseLevel;
		map = tmxMapLoader.load("maps/outsidehouse.tmx");
		roomMusic = Gdx.audio.newMusic(Gdx.files.internal("music/empty.mp3"));
		clouds = true;
	}

	@Override
	public void initEntities(Player player) {
		super.initEntities(player);
		if (notCat()) {
			entityList.add(c);
			makeCat--;
		}

		if (!Main.hardMode){
			entityList.add(new HardModeActivator(TILE*28, TILE*(baseLevel+5)));
		}

		Breakable b = new Breakable(TILE*61, TILE*(baseLevel-4));
		b.noGhosts();
		entityList.add(b);
		entityList.add(new Portal(TILE*69, TILE*(baseLevel-3), superLevel.getRoom(1), TILE*2, TILE*43));

		if (Main.gotCat){
			Portal homeSweetHome = new Portal(TILE*25, TILE*(baseLevel), superLevel.getRoom(0), TILE*26, TILE*baseLevel);
			homeSweetHome.doesCutscene();
			homeSweetHome.getImage().set(new Sprite(new Texture(Gdx.files.internal("sprites/glow door.PNG"))));
			entityList.add(homeSweetHome);
			entityList.add(new Window(TILE*27, TILE*(baseLevel + 1)));
		}
	}

	@Override
	public void update(){
		super.update();
		if (!entityList.contains(c) && makeCat > 0){
			entityList.add(c);
			makeCat--;
		}
	}

}
