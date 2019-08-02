package core;

import core.kernel.Game;
import core.kernel.ModelLoader;
import core.scene.Scene;
import core.utils.Audio;

public class Main {

	public static void main(String[] args) {
		Game game = new Game();
		game.getEngine().createWindow(1280, 720, 320, 180, false);
		game.init();
		ModelLoader.load();
		Audio.load();
		Scene.load("mainScene", true);
		game.launch();
	}

}
