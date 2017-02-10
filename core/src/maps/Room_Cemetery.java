package maps;

import nw.Main;

import com.badlogic.gdx.Gdx;

import entities.Breakable;
import entities.Cat;
import entities.Dog;
import entities.Portal;
import entities.Player;
import entities.Shred;
import entities.Slime;
import entities.Unbreakable;
import entities.Zombie;

public class Room_Cemetery extends Room {

	public Room_Cemetery(Level superLevel) {
		super(superLevel);
		startPosition.x = TILE * 3;
		startPosition.y = TILE * 43;
		map = tmxMapLoader.load("maps/cemetery.tmx");
		roomMusic = Gdx.audio.newMusic(Gdx.files.internal("music/night.mp3"));
		clouds = true;
	}
	
	@Override
	public void initEntities(Player player) {
		super.initEntities(player);
		if (notCat()) entityList.add(new Cat(TILE*5, TILE*43));
		if (!Main.gotShred1 && !Main.hardMode) entityList.add(new Shred(TILE*48, TILE*42, 1));
		if (!Main.gotShred2 && !Main.hardMode) entityList.add(new Shred(TILE*72, TILE*48, 2));
		
		entityList.add(new Slime(TILE*20, TILE*41));
		entityList.add(new Zombie(TILE*27, TILE*27));
		entityList.add(new Zombie(TILE*23, TILE*27));
		entityList.add(new Zombie(TILE*18, TILE*26));
		entityList.add(new Slime(TILE*53, TILE*31));
		entityList.add(new Slime(TILE*50, TILE*31));
		entityList.add(new Zombie(TILE*49, TILE*41));
		entityList.add(new Dog(TILE*76, TILE*44));
		entityList.add(new Zombie(TILE*90, TILE*41));
		entityList.add(new Zombie(TILE*112, TILE*42));
		entityList.add(new Zombie(TILE*119, TILE*41));
		entityList.add(new Zombie(TILE*126, TILE*41));
		entityList.add(new Zombie(TILE*128, TILE*41));
		entityList.add(new Zombie(TILE*135, TILE*41));
		entityList.add(new Zombie(TILE*139, TILE*41));
		
		Breakable b1 = new Breakable(TILE*72, TILE*44);
		Breakable b2 = new Breakable(TILE*79, TILE*44);
		b1.noGhosts();
		b2.noGhosts();
		entityList.add(b1);
		entityList.add(b2);
		entityList.add(new Breakable(TILE*143, TILE*41));
		
		entityList.add(new Portal(0, TILE*43, superLevel.getRoom(0), TILE*67, TILE*13));
		entityList.add(new Portal(TILE*154, TILE*41, superLevel.getRoom(6), TILE*4, TILE*6));
		
		if (Main.hardMode){
			entityList.add(new Zombie(TILE*14, TILE*41));
			entityList.add(new Slime(TILE*26, TILE*41));
			entityList.add(new Slime(TILE*28, TILE*41));
			entityList.add(new Slime(TILE*32, TILE*41));
			entityList.add(new Zombie(TILE*14, TILE*34));
			entityList.add(new Zombie(TILE*38, TILE*37));
			entityList.add(new Slime(TILE*55, TILE*31));
			entityList.add(new Zombie(TILE*55, TILE*41));
			entityList.add(new Zombie(TILE*72, TILE*49));
			entityList.add(new Zombie(TILE*75, TILE*44));
			entityList.add(new Zombie(TILE*100, TILE*41));
			entityList.add(new Zombie(TILE*105, TILE*42));
			
			entityList.add(new Unbreakable(TILE*18, TILE*33));
			entityList.add(new Unbreakable(TILE*26, TILE*32));
			entityList.add(new Unbreakable(TILE*26, TILE*33));
		}
		
	}

}
