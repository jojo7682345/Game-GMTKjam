package core.kernel;

import core.math.Matrix4f;
import core.math.Quaternion;
import core.math.Vec3f;
import core.utils.Constants;

public class Camera {

	private static Camera instance = null;

	private final Vec3f yAxis = new Vec3f(0, 1, 0);

	private Vec3f		position;
	private Vec3f		previousPosition;
	private Vec3f		forward;
	private Vec3f		previousForward;
	private Vec3f		up;
	private Matrix4f	viewMatrix;
	private Matrix4f	projectionMatrix;
	private Matrix4f	viewProjectionMatrix;
	private Matrix4f	previousViewMatrix;
	private Matrix4f	previousViewProjectionMatrix;
	private boolean		cameraMoved;
	private boolean		cameraRotated;

	private float	width;
	private float	height;
	private float	fovY;

	private Quaternion[]	frustumPlanes	= new Quaternion[6];
	private Vec3f[]			frustumCorners	= new Vec3f[8];

	public static Camera getInstance() {
		if (instance == null) {
			instance = new Camera();
		}
		return instance;
	}

	protected Camera() {
		this(new Vec3f(0, 0, 0), new Vec3f(0f, 0, 1f), new Vec3f(0, 1f, 0));
		if (Window.is3D) {
			setProjection(70,Window.getInstance().viewPortWidth, Window.getInstance().viewPortHeight);
		} else {
			setProjection(Window.getInstance().viewPortWidth, Window.getInstance().viewPortHeight);
		}
		setViewMatrix(new Matrix4f().View(this.getForward(), this.getUp()).mul(new Matrix4f().Translation(this.getPosition().mul(-1))));
		previousViewMatrix = new Matrix4f().Zero();
		viewProjectionMatrix = new Matrix4f().Zero();
		previousViewProjectionMatrix = new Matrix4f().Zero();
	}

	private Camera(Vec3f position, Vec3f forward, Vec3f up) {
		setPosition(position);
		setForward(forward);
		setUp(up);
		up.normalize();
		forward.normalize();
	}

	public void update() {
		setPreviousPosition(new Vec3f(position));
		setPreviousForward(new Vec3f(forward));

		setPreviousViewMatrix(viewMatrix);
		setPreviousViewProjectionMatrix(viewProjectionMatrix);
		setViewMatrix(new Matrix4f().View(this.getForward(), this.getUp()).mul(new Matrix4f().Translation(this.getPosition().mul(-1))));
		setViewProjectionMatrix(projectionMatrix.mul(viewMatrix));
	}

	public void move(Vec3f dir, float amount) {
		Vec3f newPos = position.add(dir.mul(amount));
		setPosition(newPos);
	}

	public void rotateY(float angle) {
		Vec3f hAxis = yAxis.cross(forward).normalize();

		forward.rotate(angle, yAxis).normalize();

		up = forward.cross(hAxis).normalize();
	}

	public void rotateX(float angle) {
		Vec3f hAxis = yAxis.cross(forward).normalize();

		forward.rotate(angle, hAxis).normalize();

		up = forward.cross(hAxis).normalize();
	}

	public Vec3f getLeft() {
		Vec3f left = forward.cross(up);
		left.normalize();
		return left;
	}

	public Vec3f getRight() {
		Vec3f right = up.cross(forward);
		right.normalize();
		return right;
	}

	public Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}

	public void setProjectionMatrix(Matrix4f projectionMatrix) {
		this.projectionMatrix = projectionMatrix;
	}

	public void setProjection(float fovY, float width, float height) {
		this.fovY = fovY;
		this.width = width;
		this.height = height;

		this.projectionMatrix = new Matrix4f().PerspectiveProjection(fovY, width, height, Constants.ZNEAR, Constants.ZFAR);
	}
	
	public void windowSizeChanged(int viewPortWidth, int viewPortHeight) {
		if (Window.is3D) {
			setProjection(70,viewPortWidth, viewPortHeight);
		} else {
			setProjection(viewPortWidth, viewPortHeight);
		}
	}

	public void setProjection(float width, float height) {
		this.width = width;
		this.height = height;
		
	
		this.projectionMatrix = new Matrix4f().Orthographic2D((int)width,(int)height);
	}

	public Matrix4f getViewMatrix() {
		return viewMatrix;
	}

	public void setViewMatrix(Matrix4f viewMatrix) {
		this.viewMatrix = viewMatrix;
	}

	public Vec3f getPosition() {
		return position;
	}

	public void setPosition(Vec3f position) {
		this.position = position;
	}

	public Vec3f getForward() {
		return forward;
	}

	public void setForward(Vec3f forward) {
		this.forward = forward;
	}

	public Vec3f getUp() {
		return up;
	}

	public void setUp(Vec3f up) {
		this.up = up;
	}

	public Quaternion[] getFrustumPlanes() {
		return frustumPlanes;
	}

	public float getFovY() {
		return this.fovY;
	}

	public float getWidth() {
		return this.width;
	}

	public float getHeight() {
		return this.height;
	}

	public void setViewProjectionMatrix(Matrix4f viewProjectionMatrix) {
		this.viewProjectionMatrix = viewProjectionMatrix;
	}

	public Matrix4f getViewProjectionMatrix() {
		return viewProjectionMatrix;
	}

	public Matrix4f getPreviousViewProjectionMatrix() {
		return previousViewProjectionMatrix;
	}

	public void setPreviousViewProjectionMatrix(Matrix4f previousViewProjectionMatrix) {
		this.previousViewProjectionMatrix = previousViewProjectionMatrix;
	}

	public Matrix4f getPreviousViewMatrix() {
		return previousViewMatrix;
	}

	public void setPreviousViewMatrix(Matrix4f previousViewMatrix) {
		this.previousViewMatrix = previousViewMatrix;
	}

	public Vec3f[] getFrustumCorners() {
		return frustumCorners;
	}

	public boolean isCameraMoved() {
		return cameraMoved;
	}

	public boolean isCameraRotated() {
		return cameraRotated;
	}

	public Vec3f getPreviousPosition() {
		return previousPosition;
	}

	public void setPreviousPosition(Vec3f previousPosition) {
		this.previousPosition = previousPosition;
	}

	public Vec3f getPreviousForward() {
		return previousForward;
	}

	private void setPreviousForward(Vec3f previousForward) {
		this.previousForward = previousForward;
	}
}
