package modules.objects;

import core.math.Vec2f;
import core.texturing.Sprite;
import modules.components.Pathfinding;

public class Test extends SpriteObject {


	public Test() {
		super(Sprite.load("barrel"));
	}

	@Override public void onCreate() {
		scale();
		Pathfinding path = new Pathfinding(15, 15);
		addComponent("pathfinding", path);
		path.setTarget(new Vec2f(15,0));
		path.setSpeed(1f);
	}

}
