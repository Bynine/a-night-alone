package cutscenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Cutscene_Altar extends Cutscene{
	
	public Cutscene_Altar(){
		super();
		cutsceneMusic = Gdx.audio.newMusic(Gdx.files.internal("music/altar cutscene 2.mp3"));
		sceneList.add(new Scene(new Texture(Gdx.files.internal("scenes/altar1.PNG")), null));
		sceneList.add(new Scene(new Texture(Gdx.files.internal("scenes/altar2.PNG")), null));
		sceneList.add(new Scene(new Texture(Gdx.files.internal("scenes/altar3.PNG")), null));
		sceneList.add(new Scene(new Texture(Gdx.files.internal("scenes/altar4.PNG")), null));
		sceneList.add(new Scene(new Texture(Gdx.files.internal("scenes/altar5.PNG")), null));
		sceneList.add(new Scene(new Texture(Gdx.files.internal("scenes/altar6.PNG")), null));
		sceneList.add(new Scene(new Texture(Gdx.files.internal("scenes/altar7.PNG")), null));
		sceneList.add(new Scene(new Texture(Gdx.files.internal("scenes/altar8.PNG")), null));
	}
	
	@Override
	public Cutscene nextCutscene() {
		return new Cutscene_Ending();
	}
}
