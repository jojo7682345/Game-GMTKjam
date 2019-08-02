package core.renderer;

import core.buffers.MeshVBO;
import core.buffers.VBO;
import core.configs.RenderConfig;
import core.model.Mesh;
import core.scene.Component;
import core.shaders.Shader;

public class Renderer extends Component{
	
	private VBO vbo;
	private RenderInfo renderInfo;
	
	public Renderer(){}
	
	public void render(){
		try {
		renderInfo.getConfig().enable();
		renderInfo.getShader().bind();			
		renderInfo.getShader().updateUniforms(getParent());
		getVbo().draw();
		renderInfo.getConfig().disable();
		}catch(NullPointerException e) {
			e.printStackTrace();
			System.out.println(this.getParent().getClass());
		}
	};

	public VBO getVbo() {
		return vbo;
	}

	public void setVbo(VBO vbo) {
		this.vbo = vbo;
	}

	public RenderInfo getRenderInfo() {
		return renderInfo;
	}

	public void setRenderInfo(RenderInfo renderinfo) {
		this.renderInfo = renderinfo;
	}
	
	@Override
	public void destroy() {
		vbo.delete();
	}
	
	public void setRenderData(Mesh mesh, RenderConfig config, Shader shader) {
		setRenderInfo(new RenderInfo(config,shader));
		MeshVBO vbo = new MeshVBO();
		vbo.allocate(mesh);
		setVbo(vbo);
	}

	@Override public void update() {
	}
}
