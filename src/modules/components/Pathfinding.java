package modules.components;

import core.math.Vec2f;
import core.scene.Component;
import modules.map.Map;

public class Pathfinding extends Component {

	private Vec2f		target	= new Vec2f();
	private float		speed	= 0f;
	private final int	size;

	public Pathfinding() {
		this(0,1);
	}

	public Pathfinding(float speed, int size) {
		this.size = size;
		Map.getInstance().addPathfinder(this);
	}

	@Override public void update() {
		

	}
	
	@Override public void render() {
	}

	public Vec2f getTarget() {
		return target;
	}

	public void setTarget(Vec2f target) {
		this.target = target;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

}
