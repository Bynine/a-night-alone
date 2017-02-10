package entities;

import nw.Main;

public class Slime extends Monster {

	public Slime(float posX, float posY) {
		super(posX, posY);
		setup("sprites/slime.PNG", "sprites/slimestun.PNG");
		airSpeed = 0.2f;
		airSpeed += (.25f + Math.random())/15;
		jumpStrength = 0;
		damage = 0.5f;
		jumpTimer.setTime(60);
		stunTimer.setTime(120);

		if (Main.hardMode){
			setup("sprites/slimehard.PNG", "sprites/slimestunhard.PNG");
			damage = 0.78f;
			airSpeed = 0.4f;
			airSpeed += (.5f + Math.random())/10;
		}
	}

}
