package core.renderer;

import core.model.Model;

public class ModelRenderer extends Renderer{

	private Model model;

	public ModelRenderer(Model model) {
		this.model = model;
	}

	public Model getModel() {
		return model;
	}

}
