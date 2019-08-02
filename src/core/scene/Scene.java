package core.scene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import core.kernel.RenderingEngine;
import core.math.Transform;

public class Scene {
	private static RenderingEngine renderer;
	public static void load(String string, boolean newScene) {
		load(new File("./res/scenes/" + string + ".scene"),newScene);
	}

	public static void addObject(GameObject o) {
		o.onCreate();
		renderer.getRenderQueue().add(o);
	}
	
	public static void init(RenderingEngine engine) {
		renderer = engine;
	}
	
	public void removeObject(GameObject o) {
		renderer.removeObjectFromRenderQueue(o);
	}

	public static void load(File file, boolean newScene) {
		if(newScene) {
			renderer.clearRenderingQueue();
		}
		readFile(file);
		
	}
	
	private static void readFile(File file) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.startsWith("#")) {
					continue;
				}
				String[] data = line.split("/");
				GameObject object = (GameObject) (Class.forName(data[0]).newInstance());
				object.setWorldTransform(Transform.fromString(data[1]));
				object.setLocalTransform(Transform.fromString(data[2]));
				object.setName(data[3]);
				object.onCreate();
				renderer.getRenderQueue().add(object);
			}

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {

			e.printStackTrace();
		} catch (IllegalAccessException e) {

			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
	}

}
