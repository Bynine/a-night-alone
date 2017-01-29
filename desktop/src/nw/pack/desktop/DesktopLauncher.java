package nw.pack.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import nw.Main;

public class DesktopLauncher {
	
	public static void main (String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.addIcon("sprites/icon16.PNG", FileType.Classpath);
		cfg.addIcon("sprites/icon32.PNG", FileType.Classpath);
		cfg.title = "A Night Alone";
		cfg.width = (int) Main.SCREENWIDTH;
		cfg.height= (int) Main.SCREENHEIGHT;
		new LwjglApplication(new Main(), cfg);
	}
	
}