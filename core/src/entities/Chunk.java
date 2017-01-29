package entities;

import java.util.Random;

import nw.Timer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Chunk extends Entity{
	
	Timer life = new Timer(60, true);
	Random random = new Random();
	int dir;
	float speed;

	public Chunk(float posX, float posY) {
		super(posX, posY);
		image = new Sprite(new Texture(Gdx.files.internal("sprites/chunk.PNG")));
		if (random.nextBoolean()) dir = 1;
		else dir = -1;
		speed = (float) (1.8f * (1.1 - Math.random()));
		velocity.y = (float) (2.7f * (1.3 - Math.random()));
		collision = Collision.CREATURE;
		timerList.add(life);
		friction = 0.99f;
		gravity = -0.1f;
	}
	
	@Override
	protected void handleMovement(float f){
		velocity.x = dir * speed;
		velocity.y += gravity;
		if (life.timeUp()) {
			setRemove();
		}
	}
	
	@Override
	protected boolean checkCollision(float x, float y){
		int wiggleRoom = 1;
		boolean hitWall1 = super.checkCollision(x - wiggleRoom, y - wiggleRoom);
		boolean hitWall2 = super.checkCollision(x + wiggleRoom, y - wiggleRoom);
		boolean hitWall3 = super.checkCollision(x - wiggleRoom, y + wiggleRoom);
		boolean hitWall4 = super.checkCollision(x + wiggleRoom, y + wiggleRoom);
		boolean hitWall = hitWall1 && hitWall2 && hitWall3 && hitWall4;
		if (hitWall && life.getTime() > 1) {
			life.setTime(1);
		}
		return hitWall;
	}
	
}
