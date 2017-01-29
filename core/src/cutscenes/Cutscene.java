package cutscenes;

import java.util.ArrayList;
import java.util.List;

import nw.Timer;

import com.badlogic.gdx.audio.Music;

public abstract class Cutscene {

	protected final List<Scene> sceneList = new ArrayList<Scene>();
	public final Timer sceneTimer = new Timer(30, true);
	private int scenePosition = 0;
	Music cutsceneMusic;

	public Scene getScene(){
		Scene scene = sceneList.get(sceneList.size() - 1);
		try{ scene = sceneList.get(scenePosition); }
		catch (Exception e){ System.out.println("Tried to get scene that doesn't exist"); }
		return scene;
	}

	public boolean updateScene(){
		if (scenePosition >= sceneList.size() - 1) { // cutscene over!
			return true;
		}
		else if (sceneTimer.timeUp()){
			if (getScene().getSFX() != null) {
				getScene().getSFX().play();
			}
			scenePosition++;
			sceneTimer.restart();
			return false;
		}
		return false;
	}

	public void updateCutscene(){ sceneTimer.countUp(); }

	public abstract Cutscene nextCutscene();

}
