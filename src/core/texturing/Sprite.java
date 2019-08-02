package core.texturing;

import java.io.File;
import java.util.HashMap;

import core.kernel.CoreEngine;
import core.kernel.ModelLoader;
import core.model.Mesh;

public class Sprite implements Cloneable{

	private Texture2D[]	textures;
	private float		frameTime	= 0;
	private float		index;
	private static Mesh	mesh		= ModelLoader.get("quad", 0).getMesh();

	public Sprite(int frameRate, Texture2D... textures) {
		frameTime = 1 / frameRate;
		this.textures = textures;
	}

	float startTime = System.nanoTime();

	public void update() {
		index += frameTime * (CoreEngine.deltaTime / 1000f);
		index = index % textures.length;

	}

	public int getFrameRate() {
		return (int) (1 / frameTime);
	}

	public void setFrameRate(int frameRate) {
		frameTime = frameRate;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getIndex() {
		return (int) index;
	}

	public Texture2D getTexture() {
		return textures[getIndex()];
	}

	private static HashMap<String, Sprite> loadedSprites = new HashMap<>();

	public static Sprite load(String string) {
		Sprite sprite = null;
		if (!loadedSprites.containsKey(string)) {
			File file = new File("./res/textures/" + string + "/");
			if (!file.isDirectory() || !file.exists()) {
				return null;
			}
			Texture2D[] textures = new Texture2D[file.listFiles().length];
			for (File textureFile : file.listFiles()) {
				String name = textureFile.getName();
				Texture2D texture = new Texture2D("./res/textures/" + string + "/" + textureFile.getName());
				texture.noFilter();
				textures[Integer.parseInt(name.split("_")[1].replace(".png", ""))] = texture;

			}
			sprite = new Sprite(1, textures);
			loadedSprites.put(string, sprite);
			return sprite;
		} else {
			try {
				return (Sprite) loadedSprites.get(string).clone();
			} catch (CloneNotSupportedException e) {
				
				e.printStackTrace();
			}
		}
		return sprite;

	}

	public static Mesh getMesh() {
		return mesh;
	}

	public float getWidth() {
		return textures[0].getWidth();
	}
	public float getHeight() {
		return textures[0].getHeight();
	}

}
