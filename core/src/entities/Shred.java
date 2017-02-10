package entities;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.Rectangle;

import nw.Main;

public class Shred extends Entity{

	private final int num;
	private Sound collect = Gdx.audio.newSound(Gdx.files.internal("sfx/collect2.wav"));
	protected Animation anim = makeAnimation("sprites/shred.PNG", 2, 1, 20f, PlayMode.LOOP);

	public Shred(float posX, float posY, int num) {
		super(posX, posY);
		image = new Sprite(anim.getKeyFrame(0));
		updateImage(0);
		this.num = num;
	}

	@Override
	protected void updatePosition(float f, Player p){
		/* doesn't move */ 
	}

	@Override
	public void update(float f, List<Rectangle> rectangleList, List<Entity> entityList, Player p, int deltaTime){	
		super.update(f, rectangleList, entityList, p, deltaTime);
		setAnimation(anim, deltaTime);
	}

	protected void handleTouchHelper(Entity e){
		if (e instanceof Player && isTouching(e, 2)) {
			Main.setShred(num);
			setRemove();
			collect.play(0.5f);
		}
	}

}
