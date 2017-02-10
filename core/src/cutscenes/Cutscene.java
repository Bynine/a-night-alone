package cutscenes;

import java.util.ArrayList;
import java.util.List;

import nw.Main;
import nw.Timer;


import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
//import com.badlogic.gdx.audio.Sound;

public abstract class Cutscene {

	protected final List<Scene> sceneList = new ArrayList<Scene>();
	public final Timer sceneTimer = new Timer(45, true);
	private int scenePosition = 0;
	protected Music cutsceneMusic = Gdx.audio.newMusic(Gdx.files.internal("music/empty.mp3"));;
	
	public Cutscene(){
		if (Main.debug) sceneTimer.setTime(10);
	}

	public Scene getScene(){
		if (!cutsceneMusic.isPlaying()) {
			cutsceneMusic.setVolume(0.5f);
			cutsceneMusic.play();
		}
		Scene scene = sceneList.get(sceneList.size() - 1);
		try{ scene = sceneList.get(scenePosition); }
		catch (Exception e){ System.out.println("Tried to get scene that doesn't exist"); }
		return scene;
	}

	public boolean updateScene(){
		Scene s = getScene();
		if (sceneTimer.timeUp() && scenePosition >= sceneList.size() - 1) { // cutscene over!
			cutsceneMusic.stop();
			return true;
		}
		else if (sceneTimer.timeUp()){
			scenePosition++;
			sceneTimer.restart();
			if (s.getSFX() != null && !s.playedSound) {
				s.playSound();
			}
			return false;
		}
		return false;
	}

	public void updateCutscene(){ sceneTimer.countUp(); }

	public abstract Cutscene nextCutscene();

}
