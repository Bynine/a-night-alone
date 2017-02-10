package entities;

import nw.Main;

public class Foul extends Monster {

	public Foul(float posX, float posY) {
		super(posX, posY);
		setup("sprites/black.PNG", "sprites/blackstun.PNG");
		airSpeed = 0.8f;
		airSpeed += (.5f + Math.random())/10;
		jumpStrength = twoBlock;
		radius = radius*2;
		damage = 0.85f;
		jumpTimer.setTime(15);
		stunTimer.setTime(90);
		
		if (Main.hardMode){
			setup("sprites/blackhard.PNG", "sprites/blackstunhard.PNG");
			//radius = radius * 8;
			airSpeed = 1.28f;
			airSpeed += (.5f + Math.random())/10;
			jumpTimer.setTime(8);
			stunTimer.setTime(80);
		}
	}

}
