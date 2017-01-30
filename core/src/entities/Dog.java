package entities;

import nw.Main;

public class Dog extends Monster {

	public Dog(float posX, float posY) {
		super(posX, posY);
		setup("sprites/dog.PNG", "sprites/dogstun.PNG");
		walk.setFrameDuration(10);
		airSpeed = 2.8f;
		airSpeed += (.5f + Math.random())/10;
		jumpStrength = oneBlock;
		radius = radius * 4;
		damage = 0.65f;
		jumpTimer.setTime(6);
		stunTimer.setTime(80);
		
		if (Main.hardMode){
			setup("sprites/doghard.PNG", "sprites/dogstunhard.PNG");
			walk.setFrameDuration(6);
			airSpeed = 3.6f;
			airSpeed += (.5f + Math.random())/10;
			jumpTimer.setTime(1);
			stunTimer.setTime(60);
		}
	}

}