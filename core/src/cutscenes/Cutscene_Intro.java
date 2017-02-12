package cutscenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Cutscene_Intro extends Cutscene{
	
	public Cutscene_Intro(){
		super();
		cutsceneMusic = Gdx.audio.newMusic(Gdx.files.internal("music/house2.mp3"));
		sceneList.add(new Scene(new Texture(Gdx.files.internal("scenes/beginning1.PNG")), null));
		sceneList.add(new Scene(new Texture(Gdx.files.internal("scenes/beginning2.PNG")), null ));
		sceneList.add(new Scene(new Texture(Gdx.files.internal("scenes/beginning3.PNG")), null ));
		sceneList.add(new Scene(new Texture(Gdx.files.internal("scenes/beginning4.PNG")), null ));
		sceneList.add(new Scene(new Texture(Gdx.files.internal("scenes/beginning5.PNG")), null ));
		sceneList.add(new Scene(new Texture(Gdx.files.internal("scenes/beginning6.PNG")), null));
		sceneList.add(new Scene(new Texture(Gdx.files.internal("scenes/beginning7.PNG")), null));
	}

	@Override
	public Cutscene nextCutscene() {
		return new Cutscene_Altar();
	}
}
