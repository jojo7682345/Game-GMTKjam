package core.kernel;

import java.util.ArrayList;
import java.util.List;

import core.configs.Default;
import core.scene.GameObject;
import core.scene.Layer;
import modules.map.Map;

/**
 * 
 * @author oreon3D
 * The RenderingEngine manages the render calls of all 3D entities
 * with shadow rendering and post processing effects
 *
 */
public class RenderingEngine {
	
	private Window window;
	
	private List<GameObject> renderQueue = new ArrayList<>();
	 
	public RenderingEngine()
	{
		window = Window.getInstance();
	}
	
	public void init()
	{
		window.init();
	}

	public void render()
	{	
		Camera.getInstance().update();
		
		Default.clearScreen();
		Map.getInstance().render();
		for(GameObject object : renderQueue) {
			object.render();
		}
		Layer.renderAll();
		// draw into OpenGL window
		window.render();
	}
	
	public void update(){
		Map.getInstance().update();
		for(GameObject object: renderQueue) {
			object.update();
		}
	}
	
	public void shutdown(){
		for(GameObject object : renderQueue) {
			object.destroy();
		}
		renderQueue.clear();
	}

	public void removeObjectFromRenderQueue(GameObject object) {
		renderQueue.remove(object);
		object.destroy();
	}

	public void clearRenderingQueue() {
		for(GameObject object : renderQueue) {
			object.destroy();
		}
		renderQueue.clear();
	}

	public List<GameObject> getRenderQueue() {
		return renderQueue;
	}
}
