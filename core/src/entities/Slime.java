package entities;

import nw.Main;

public class Slime extends Monster {

	public Slime(float posX, float posY) {
		super(posX, posY);
		setup("sprites/slime.PNG", "sprites/slimestun.PNG");
		airSpeed = 0.2f;
		airSpeed += (.25f + Math.random())/15;
		jumpStrength = 0;
		damage = 0.75f;
		jumpTimer.setTime(60);
		stunTimer.setTime(120);

		if (Main.hardMode){
			airSpeed = 0.3f;
			airSpeed += (.25f + Math.random())/15;
		}
	}

}
