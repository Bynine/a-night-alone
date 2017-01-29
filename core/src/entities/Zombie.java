package entities;

import nw.Main;

public class Zombie extends Monster {

	public Zombie(float posX, float posY) {
		super(posX, posY);
		setup("sprites/zombie.PNG", "sprites/zombiestun.PNG");
		airSpeed = 0.4f;
		airSpeed += (.5f + Math.random())/10;
		jumpStrength = oneBlock;
		damage = 0.75f;
		jumpTimer.setTime(30);
		stunTimer.setTime(120);

		if (Main.hardMode){
			radius = (radius * 3)/2;
			airSpeed = 0.5f;
			airSpeed += (.5f + Math.random())/10;
			jumpStrength = twoBlock;
			jumpTimer.setTime(20);
			stunTimer.setTime(100);
		}
	}

}

