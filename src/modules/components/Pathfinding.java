package modules.components;

import java.awt.Point;
import java.util.List;

import core.math.Vec2f;
import core.math.Vec3f;
import core.scene.Component;
import modules.objects.Game;
import modules.pathfinding.Node;

public class Pathfinding extends Component {

	private Vec2f		target	= new Vec2f();
	private float		speed	= 0f;
	private final int	size;

	public Pathfinding() {
		this.size = 1;
	}

	public Pathfinding(float speed, int size) {
		this.size = size;
	}

	@Override public void update() {
		Vec3f pos = getWorldTransform().getTranslation();
		Game.getGraph().setStartPosition(new Point((int) (pos.getX() / Game.UNIT), (int) (pos.getY() / Game.UNIT)));
		Game.getGraph().setTargetPosition(new Point((int) (target.getX() / Game.UNIT), (int) (target.getY() / Game.UNIT)));
		List<Node> nodes = Game.getGraph().executeAStar();
		if (nodes!=null&&!nodes.isEmpty()) {
			Node node = nodes.get(0);

			getWorldTransform().setTranslation(node.getX() * Game.UNIT, node.getY() * Game.UNIT, pos.getZ());
		}
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}

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
