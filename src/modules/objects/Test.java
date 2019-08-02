package modules.objects;

import core.texturing.Sprite;
import modules.components.Interactable;

public class Test extends SpriteObject {

	public Test() {
		super(Sprite.load("player"));
	}

	@Override public void onCreate() {
		scale();
		addComponent("interactable", new Interactable(getBounds(), () -> {
			System.out.println("pressed");
		}));
	}

}
