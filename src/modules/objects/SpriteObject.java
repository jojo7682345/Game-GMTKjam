package modules.objects;

import core.configs.Default;
import core.configs.RenderConfig;
import core.kernel.ModelLoader;
import core.model.Mesh;
import core.renderer.Renderer;
import core.scene.GameObject;
import core.shaders.Shader;
import core.texturing.Sprite;
import core.utils.Constants;
import modules.shaders.SpriteShader;

public abstract class SpriteObject extends GameObject{

	private Sprite sprite;
	private Mesh mesh = ModelLoader.get("quad",0).getMesh();
	
	public SpriteObject() {};
	
	public SpriteObject(Sprite sprite) {
		setSprite(sprite);
		Renderer renderer = new Renderer();		//creates a new rendererComponent
		renderer.setRenderData(getMesh(), Default.getInstance(),SpriteShader.getInstance());
		addComponent(Constants.RENDERER_COMPONENT, renderer);
	}
	public SpriteObject(Sprite sprite, Shader shader) {
		setSprite(sprite);
		Renderer renderer = new Renderer();		//creates a new rendererComponent
		renderer.setRenderData(getMesh(), Default.getInstance(),shader);
		addComponent(Constants.RENDERER_COMPONENT, renderer);
	}
	public SpriteObject(Sprite sprite, RenderConfig config, Shader shader) {
		setSprite(sprite);
		Renderer renderer = new Renderer();		//creates a new rendererComponent
		renderer.setRenderData(getMesh(), config,shader);
		addComponent(Constants.RENDERER_COMPONENT, renderer);
	}
	
	public SpriteObject(Sprite sprite,Mesh mesh, RenderConfig config, Shader shader) {
		setSprite(sprite);
		Renderer renderer = new Renderer();		//creates a new rendererComponent
		renderer.setRenderData(mesh, config,shader);
		addComponent(Constants.RENDERER_COMPONENT, renderer);
	}
	
	public void setFramerate(int framerate) {
		sprite.setFrameRate(framerate);
	}
	
	public int getFramerate() {
		return sprite.getFrameRate();
	}
	
	public void update() {
		sprite.update();
		super.update();
	}
	
	public void scale() {
		getWorldTransform().setScaling(sprite.getWidth(),sprite.getHeight(),0);
	}
	
	public int getIndex() {
		return sprite.getIndex();
	}
	
	public void setIndex(int index) {
		sprite.setIndex(index);
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	public Mesh getMesh() {
		return mesh;
	}
	public void setMesh(Mesh mesh) {
		this.mesh = mesh;
	}

}
