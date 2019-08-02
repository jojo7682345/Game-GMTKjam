package modules.shaders;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import core.scene.GameObject;
import core.shaders.Shader;
import core.utils.ResourceLoader;
import modules.objects.SpriteObject;

public class SpriteShader extends Shader {

	private static SpriteShader instance;
	
	public static SpriteShader getInstance() {
		if(instance==null) {
			instance = new SpriteShader();
		}
		return instance;
	}
	
	public SpriteShader() {
		addVertexShader(ResourceLoader.loadShader("shaders/textureVertex.glsl"));
		addFragmentShader(ResourceLoader.loadShader("shaders/textureFragment.glsl"));
		compileShader();
		
		addUniform("modelViewProjectionMatrix");
		addUniform("tex");
	}
	
	@Override public void updateUniforms(GameObject object) {
		SpriteObject t = (SpriteObject) object;
		setUniform("modelViewProjectionMatrix",t.getWorldTransform().getModelViewProjectionMatrix());
		glActiveTexture(GL_TEXTURE0);
		t.getSprite().getTexture().bind();
		setUniformi("tex",0);
	}
}
