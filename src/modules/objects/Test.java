package modules.objects;

import core.kernel.Camera;
import core.math.Vec2f;
import core.texturing.Sprite;
import modules.components.Interactable;
import modules.components.Pathfinding;

public class Test extends SpriteObject {


	public Test() {
		super(Sprite.load("player"));
	}

	@Override public void onCreate() {
		scale();
		addComponent("interactable", new Interactable(getBounds(), () -> {
			Camera.getInstance().setPosition(Camera.getInstance().getPosition().add(32, 0, 0));
			System.out.println("pressed");
		}));
		Pathfinding path = new Pathfinding(20, 1);
		addComponent("pathfinding", path);
		path.setTarget(new Vec2f(20,30));
		path.setSpeed(1f);
	}

}
