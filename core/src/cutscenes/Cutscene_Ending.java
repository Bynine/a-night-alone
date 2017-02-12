package cutscenes;

import nw.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Cutscene_Ending extends Cutscene{
	
	public Cutscene_Ending(){
		super();
		cutsceneMusic = Gdx.audio.newMusic(Gdx.files.internal("music/house2.mp3"));
		sceneList.add(new Scene(new Texture(Gdx.files.internal("scenes/ending1.PNG")), null));
		sceneList.add(new Scene(new Texture(Gdx.files.internal("scenes/ending2.PNG")), null));
		sceneList.add(new Scene(new Texture(Gdx.files.internal("scenes/ending3.PNG")), null));
		if (Main.hardMode){
			sceneList.add(new Scene(new Texture(Gdx.files.internal("scenes/ending4.PNG")), null));
			sceneList.add(new Scene(new Texture(Gdx.files.internal("scenes/ending5.PNG")), null));
		}
	}
	
	@Override
	public Cutscene nextCutscene() {
		return new Cutscene_Finale();
	}
	
	@Override
	public boolean updateScene(){
		boolean b = super.updateScene();
		if (b) Main.setGameState(Main.GameState.SHREDS);
		return b;
	}
}
