package modules.shaders;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import core.kernel.Camera;
import core.math.Vec3f;
import core.scene.Layer;
import core.shaders.Shader;
import core.utils.ResourceLoader;

public class LayerShader extends Shader{
private static LayerShader instance;
	
	public static LayerShader getInstance() {
		if(instance==null) {
			instance = new LayerShader();
		}
		return instance;
	}
	
	public LayerShader() {
		addVertexShader(ResourceLoader.loadShader("shaders/layerVertex.glsl"));
		addFragmentShader(ResourceLoader.loadShader("shaders/layerFragment.glsl"));
		compileShader();
		
		addUniform("scale");
		addUniform("viewMatrix");
		addUniform("tex");
	}
	
	public void updateUniforms(Layer layer) {
		setUniform("scale",new Vec3f(Layer.WIDTH,Layer.HEIGHT,layer.depth));
		setUniform("viewMatrix",Camera.getInstance().getViewProjectionMatrix());
		glActiveTexture(GL_TEXTURE0);
		layer.bind();
		setUniformi("tex",0);
	}

}
