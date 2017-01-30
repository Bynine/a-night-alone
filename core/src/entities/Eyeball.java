package entities;

import nw.Main;
import nw.Timer;

public class Eyeball extends Monster{
	
	Timer life = new Timer(80, true);

	public Eyeball(float posX, float posY, Player p) {
		super(posX, posY);
		setup("sprites/eyesheet.PNG", "sprites/eyestun.PNG");
		airSpeed = 2.7f;
		airSpeed += (.5f + Math.random())/10;
		jumpStrength = 0;
		radius = radius * 5;
		damage = 0.45f;
		jumpTimer.setTime(60);
		stunTimer.setTime(120);
		
		if (Main.hardMode){
			damage = 1.8f;
			setup("sprites/hardeyesheet.PNG", "sprites/eyestun.PNG");
		}
		
		flip();
		updateImage();
		inFront = true;
		collision = Collision.GHOST;
		gravity = 0;
		double angle = getAngle(posX, posY, p);
		velocity.y -= angle*2f;
		airSpeed -= p.getVelocity().x/3;
		timerList.add(life);
	}

	public static float getAngle(float posX, float posY, Player p){
		float deltaX = (p.getPosition().x + p.getVelocity().x) - posX;
		float deltaY = (p.getPosition().y + p.getVelocity().y) - posY;
		return (float)Math.atan(deltaY / deltaX);
	}

	@Override
	public void reactToPlayer(Player p){
		/* doesn't turn */
	}
	
	@Override
	protected void speedMod(){
		/* speed don't mod*/
	}

	@Override
	public void updatePosition(float f, Player p){		
		if (stunTimer.timeUp()) {	
			position.x += velocity.x;	
			position.y += velocity.y;
		}
		if (life.timeUp()) setRemove();
	}

}
