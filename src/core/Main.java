package core;

import core.kernel.Game;
import core.kernel.ModelLoader;
import core.scene.Scene;
import core.utils.Audio;
import modules.map.Map;

public class Main {

	public static void main(String[] args) {
		Game game = new Game();
		game.getEngine().createWindow(1280, 720, 640, 360, false);
		game.init();
		ModelLoader.load();
		Audio.load();
		Scene.load("mainScene", true);
		Map.getInstance();
		game.launch();
	}

}
