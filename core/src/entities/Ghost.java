package entities;

import com.badlogic.gdx.math.MathUtils;

import nw.Main;
import nw.Timer;

public class Ghost extends Monster {
	
	public Timer hauntTimer = new Timer(50, true);
	private Timer life = new Timer(360, true);
	private float y = 0.8f;
	private float yClamp = 0.06f;

	public Ghost(float posX, float posY) {
		super(posX, posY);
		setup("sprites/ghost.PNG", "sprites/ghost.PNG");
		friction = 0.8f;
		airSpeed = 0.5f;
		gravity = 0;
		damage = 0.0f;
		collision = Collision.GHOST;
		timerList.add(hauntTimer);
		radius = radius * 8;
		
		if(Main.hardMode){
			setup("sprites/ghosthard.PNG", "sprites/ghosthard.PNG");
			damage = 0.02f;
			airSpeed = 0.36f;
			yClamp = 0.12f;
		}
		
		else{
			timerList.add(life);
		}
	}
	
	@Override
	protected void handleTouchHelper(Entity e){
		if (!hauntTimer.timeUp()){
			velocity.y = 0;
			return;
		}
		velocity.y = MathUtils.clamp(velocity.y, -y, y);
		if (e instanceof Player) {
			gravity = MathUtils.clamp((e.position.y - position.y)/128f, -yClamp, yClamp);
			if (isTouching(e, 4)){
				Player p = (Player) e;
				p.hurt(damage, this);
			}
		}
	}
	
	@Override
	protected void handleMovement(float f){
		super.handleMovement(f);
		if (life.timeUp()) {
			setRemove();
		}
	}
	
	@Override
	public void jump(){
		/* doesn't jump */
	}

}
