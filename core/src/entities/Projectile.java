package entities;

import nw.Main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

import nw.Timer;

public class Projectile extends Entity {

	int dir;
	Timer life = new Timer(120, true);
	float speed = 2.75f;
	private Sound land = Gdx.audio.newSound(Gdx.files.internal("sfx/rock.wav"));
	TextureRegion chunkTexture = new TextureRegion();

	public Projectile(Player p){
		super(p.getPosition().x, p.getPosition().y);
		if (p.getDirection() == Direction.RIGHT) dir = 1;
		else dir = -1;
		chunkTexture.setRegion(new Texture(Gdx.files.internal("sprites/rockchunk.PNG")));
		speed += dir*p.getVelocity().x/2;
		this.position.x = position.x + 4 + (8*dir);
		this.position.y = position.y + 8;
		gravity = -0.065f;
		image = new Sprite(new Texture(Gdx.files.internal("sprites/rock.PNG")));
		collision = Collision.CREATURE;		
		friction = 1;
		float verticalAdd = MathUtils.clamp(p.getVelocity().y/4, 0, 0.5f);
		velocity.y = 0.95f + verticalAdd;
		timerList.add(life);
		updateImage(0);
	}

	@Override
	protected void handleMovement(float f){
		velocity.x = dir * speed;
		velocity.y += gravity;
		if (life.timeUp()) {
			land.play();
			setRemove();
		}
	}
	
	@Override
	public void setRemove(){
		super.setRemove();
		for (int i = 0; i < 3; ++i){
			Chunk c = new Chunk(position.x + image.getWidth()/2, position.y + image.getHeight()/2);
			c.setImage(chunkTexture);
			Main.addEntity(c);
		}
	}

	@Override
	protected boolean checkCollision(float x, float y){
		int wiggleRoom = 2;
		boolean hitWall1 = super.checkCollision(x - wiggleRoom, y - wiggleRoom);
		boolean hitWall2 = super.checkCollision(x + wiggleRoom, y - wiggleRoom);
		boolean hitWall3 = super.checkCollision(x - wiggleRoom, y + wiggleRoom);
		boolean hitWall4 = super.checkCollision(x + wiggleRoom, y + wiggleRoom);
		boolean hitWall = hitWall1 && hitWall2 && hitWall3 && hitWall4;
		if (hitWall && life.getTime() > 2) {
			life.setTime(2);
		}
		return hitWall;
	}
}
