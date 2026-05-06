package inf112.mayhem.app;

import org.lwjgl.system.Configuration;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.utils.Os;
import com.badlogic.gdx.utils.SharedLibraryLoader;
import inf112.mayhem.controller.AppController;

public class MayhemMain {
	
	public static void main(String[] args) {
		MayhemMain.class.getClassLoader().setDefaultAssertionStatus(true);

		if (SharedLibraryLoader.os == Os.MacOsX) {
			Configuration.GLFW_LIBRARY_NAME.set("glfw_async");
		}

		Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
		cfg.setTitle("Gun Mayhem 3");
		cfg.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());

		ApplicationListener appListener = new AppController();
		new Lwjgl3Application(appListener, cfg);
	}
}
