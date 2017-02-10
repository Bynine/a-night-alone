package cutscenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Cutscene_Finale extends Cutscene {
	
	public Cutscene_Finale(){
		super();
		sceneList.add(new Scene(new Texture(Gdx.files.internal("sprites/hardeyesheet.PNG")), null));
	}

	@Override
	public Cutscene nextCutscene() {
		return new Cutscene_Intro();
	}

}
