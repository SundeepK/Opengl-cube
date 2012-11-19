package lwjgltest;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_COLOR_MATERIAL;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_LIGHT0;
import static org.lwjgl.opengl.GL11.GL_SMOOTH;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLight;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glShadeModel;
import static org.lwjgl.opengl.GL11.glViewport;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public abstract class BaseRenderer {

	/** width of window */
	private int viewportWidth = 800;

	/** height of window */
	private int viewportHeight = 600;

	/**
	 * Rudimentary OpenGL setup with LWJGL. Use perspective projection with a
	 * field of view of 45 degrees.
	 */
	public void setUp() {
		// set up LWJGL display
		try {
			Display.setDisplayMode(new DisplayMode(viewportWidth,
					viewportHeight));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}

		// init viewport and projection matrix
		glViewport(0, 0, viewportWidth, viewportHeight);
		glMatrixMode(GL11.GL_PROJECTION);
		glLoadIdentity();
		GLU.gluPerspective(45.0f, (float) viewportWidth
				/ (float) viewportHeight, 1.0f, 200.0f);
		GLU.gluLookAt(-20, 50, 40, 0, 0, 0, 0, 0, 1);

		glMatrixMode(GL11.GL_MODELVIEW);

		glEnable(GL_DEPTH_TEST);
		glShadeModel(GL_SMOOTH);
		glClearColor(0f, 0f, 0f, 0f);
		float lightAmbient[] = { 0.05f, 0.05f, 0.05f, 1.0f };
		float lightDiffuse[] = { 1.5f, 1.5f, 1.5f, 1.0f };
		float LightSpecular[] = { 1.0f, 1.0f, 1.0f, 1.0f };

		ByteBuffer temp = ByteBuffer.allocateDirect(16);
		temp.order(ByteOrder.nativeOrder());
		glEnable(GL_LIGHT0);
		glLight(GL11.GL_LIGHT0, GL11.GL_AMBIENT, (FloatBuffer) temp
				.asFloatBuffer().put(lightAmbient).flip());
		glLight(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, (FloatBuffer) temp
				.asFloatBuffer().put(lightDiffuse).flip());
		glEnable(GL11.GL_LIGHTING);
		glEnable(GL_COLOR_MATERIAL);
		// glEnable(GL_CULL_FACE);

	}

	/**
	 * Game loop is basically an infinite loop that runs until the window is
	 * closed. It calls the abstract render() method which is to be implemented
	 * by the specific example class.
	 */
	public void gameLoop()

	{

		int dx = 0;
		int dy = 0;
		float dt = 0.0f; // length of frame
		float lastTime = 0.0f; // when the last frame was
		float time = 0.0f;

		float mouseSensitivity = 0.05f;
		float movementSpeed = 0.0001f; // move 10 units per second

		Camera camera = new Camera(0, 0, 0);
		time = Sys.getTime();
		dt = (time - lastTime) / 1000.0f;
		lastTime = time;

		while (!Display.isCloseRequested()) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT );
			
			// glLoadIdentity();

			render();

			dx = Mouse.getDX();
			// distance in mouse movement from the last getDY() call.
			dy = Mouse.getDY();

			// controll camera yaw from x movement fromt the mouse
			camera.yaw(dx * mouseSensitivity);
			// controll camera pitch from y movement fromt the mouse
			camera.pitch(dy * mouseSensitivity);

			if (Keyboard.isKeyDown(Keyboard.KEY_W))// move forward
			{
				camera.walkForward(movementSpeed * dt);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_S))// move backwards
			{
				camera.walkBackwards(movementSpeed * dt);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_A))// strafe left
			{
				camera.strafeLeft(movementSpeed * dt);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_D))// strafe right
			{
				camera.strafeRight(movementSpeed * dt);
			}

			glLoadIdentity();
			camera.lookThrough();

			Display.update();
			Display.sync(60);
		}

		Display.destroy();
	}

	/**
	 * Abstract render method which is supposed to be implemented by the
	 * extending tutorial or example class.
	 */
	public abstract void render();

	public int getViewportWidth() {
		return viewportWidth;
	}

	public void setViewportWidth(int viewportWidth) {
		this.viewportWidth = viewportWidth;
	}

	public int getViewportHeight() {
		return viewportHeight;
	}

	public void setViewportHeight(int viewportHeight) {
		this.viewportHeight = viewportHeight;
	}

}
