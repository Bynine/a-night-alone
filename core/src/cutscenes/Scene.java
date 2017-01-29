package cutscenes;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class Scene {
	private Texture image;
	private Sound sfx;
	
	Scene(Texture image, Sound sfx){
		this.image = image;
		this.sfx = sfx;
	}
	
	public Texture getImage(){
		return image;
	}
	
	public Sound getSFX(){
		return sfx;
	}
}
