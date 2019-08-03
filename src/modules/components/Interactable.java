package modules.components;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

import core.kernel.Camera;
import core.kernel.Input;
import core.kernel.Window;
import core.math.Vec2f;
import core.math.Vec3f;
import core.scene.Component;
import modules.util.Rectangle;

public class Interactable extends Component {

	private Rectangle	bounds;
	private Runnable	runnable;

	public Interactable(float top, float bottom, float left, float right, Runnable runnable) {
		this(new Rectangle(left, bottom, right, top),runnable);
	}
	
	public Interactable(Rectangle rectangle, Runnable runnable) {
		bounds = rectangle;
		this.runnable = runnable;
	}

	@Override public void update() {
		Vec3f cameraPos = Camera.getInstance().getPosition().mul(2);
		Input input = Input.getInstance();
		if (input.isButtonreleased(GLFW_MOUSE_BUTTON_LEFT)) {
			Vec3f pos = getWorldTransform().getTranslation().add(Window.getInstance().getWidth() / 2, Window.getInstance().getHeight() / 2, 0);
			Vec2f mousePos = input.getCursorPosition();
			if (mousePos.getX()+cameraPos.getX() >= pos.getX() + bounds.x && mousePos.getX()+cameraPos.getX() <= pos.getX() + bounds.y) {
				if (mousePos.getY()+cameraPos.getY() >= pos.getY() + bounds.width && mousePos.getY()+cameraPos.getY() <= pos.getY() + bounds.height) {
					runnable.run();
				}
			}

		}
	}

	@Override public void render() {

	}

}
