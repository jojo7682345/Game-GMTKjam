package core.kernel;

import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWWindowRefreshCallbackI;
import org.lwjgl.glfw.GLFWWindowSizeCallbackI;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

/**
 * 
 * @author oreon3D GLFW Window implementation
 *
 */
public class Window {

	private static Window instance = null;

	public static boolean is3D = true;

	private long	window;
	private int		width;
	private int		height;
	public int		viewPortWidth;
	public int 	viewPortHeight;

	public static Window getInstance() {
		if (instance == null) {
			instance = new Window();
		}
		return instance;
	}

	public void init() {
	}

	public void create(int width, int height, int viewPortWidth, int viewPortHeight, boolean is3D) {
		setWidth(width);
		setHeight(height);
		this.viewPortWidth = viewPortWidth;
		this.viewPortHeight = viewPortHeight;
		glfwWindowHint(GLFW_RESIZABLE, GLFW.GLFW_TRUE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		
		window = glfwCreateWindow(width, height, "OREON ENGINE", 0, 0);
		GLFW.glfwSetWindowSizeLimits(window, 320, 180, GLFW.GLFW_DONT_CARE, GLFW.GLFW_DONT_CARE);
		if (window == 0) {
			throw new RuntimeException("Failed to create window");
		}
		Window.is3D = is3D;
		GLFW.glfwSetWindowRefreshCallback(window, new GLFWWindowRefreshCallbackI() {

			@Override public void invoke(long window) {
				glfwSwapBuffers(window);
			}
		});
		GLFW.glfwSetWindowSizeCallback(window, new GLFWWindowSizeCallbackI() {

			@Override public void invoke(long window, int width, int height) {
				
				Camera.getInstance().windowSizeChanged(viewPortWidth, viewPortHeight);
				GL11.glViewport(0, 0, width, height);
			}
		});
		glfwMakeContextCurrent(window);
		GL.createCapabilities();
		showWindow();
	}
	
	public void showWindow() {
		glfwShowWindow(window);
	}

	public void render() {
		glfwSwapBuffers(window);
	}

	public void dispose() {
		glfwDestroyWindow(window);
	}

	public boolean isCloseRequested() {
		return glfwWindowShouldClose(window);
	}

	public void setWindowSize(int x, int y) {
		GLFW.glfwSetWindowSize(window, x, y);

		setHeight(y);
		setWidth(x);
		Camera.getInstance().windowSizeChanged(viewPortWidth, viewPortHeight);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public long getWindow() {
		return window;
	}

	public void setWindow(long window) {
		this.window = window;
	}
}
