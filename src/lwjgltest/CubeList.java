package lwjgltest;

import static org.lwjgl.opengl.GL11.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Random;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class CubeList extends BaseRenderer {

	
	private int displayListHandle = -1;

	// angle in degrees required for rotating the cubes
	private float rotationAngle = 0;
	
	private boolean showWireframe = false;
	private boolean showSineMovement = false;


	public void setUp() {
		// Set up LWJGL and OpenGL using the
		// base class.
		super.setUp();

		// Generate one (!) display list.
		// The handle is used to identify the
		// list later.
		displayListHandle = glGenLists(1);

		// Start recording the new display list.
		glNewList(displayListHandle, GL_COMPILE);

		// Render a single cube
		renderCube(1);

		// End the recording of the current display list.
		glEndList();
	}

	public static void renderCube(float size) {
		final float HALF = size / 4;
		glBegin(GL_QUADS);

		glVertex3f(-HALF, -HALF, -HALF);
		glVertex3f(-HALF, size - HALF, -HALF);
		glVertex3f(size - HALF, size - HALF, -HALF);
		glVertex3f(size - HALF, -HALF, -HALF);

		glVertex3f(-HALF, -HALF, size - HALF);
		glVertex3f(-HALF, size - HALF, size - HALF);
		glVertex3f(size - HALF, size - HALF, size - HALF);
		glVertex3f(size - HALF, -HALF, size - HALF);

		glVertex3f(-HALF, size - HALF, -HALF);
		glVertex3f(size - HALF, size - HALF, -HALF);
		glVertex3f(size - HALF, size - HALF, size - HALF);
		glVertex3f(-HALF, size - HALF, size - HALF);

		glVertex3f(-HALF, -HALF, -HALF);
		glVertex3f(size - HALF, -HALF, -HALF);
		glVertex3f(size - HALF, -HALF, size - HALF);
		glVertex3f(-HALF, -HALF, size - HALF);

		glVertex3f(-HALF, -HALF, -HALF);
		glVertex3f(-HALF, size - HALF, -HALF);
		glVertex3f(-HALF, size - HALF, size - HALF);
		glVertex3f(-HALF, -HALF, size - HALF);

		glVertex3f(size - HALF, -HALF, -HALF);
		glVertex3f(size - HALF, size - HALF, -HALF);
		glVertex3f(size - HALF, size - HALF, size - HALF);
		glVertex3f(size - HALF, -HALF, size - HALF);

		glEnd();
	}

	@Override
	public void render() {
		// In each render pass, add one degree to rotation
		// angle.
		rotationAngle += 1;
		float mul = 1;
		float wave =0;

		float dt = 0.0f; // length of frame
		float lastTime = 0.0f; // when the last frame was
		float time = 0.0f;

		time = Sys.getTime();
		dt = (time - lastTime) / 1000.0f;
		lastTime = time;
		Random ran =  new Random();
		float r1 = ran.nextFloat();
		float waveAmp = 0.5f * r1; // adjust if needed
		float waveAngle = dt * 3.14f  ; // adjust if needed
					
					
					float distanceBetween = 0.3f;
	           
//		            /* Amplitude of the waves */
		            float amplitude = 1f;
		            int cubeNum = 10;
		            
		            while (Keyboard.next()) {
		            	   if (Keyboard.getEventKeyState()) {
		            	      if (Keyboard.getEventKey() == Keyboard.KEY_B) {
		            	    	  showWireframe= !showWireframe;
		            	      }
	            	      
		            	   }
		            	}
		            
				

		for (int j = 0; j < 10; j++) {

			for (int x = 0; x < 10; x++) {
				for (int z = 0; z < 10; z++) {
					// Save current MODELVIEW matrix to
					// stack.
					glPushMatrix();
					
					glColor3f((float) (x * 25) / 250, (float) (j * 25) / 250f,
							(float) (z * 25) / 250); // Color for your polygon border

//					glTranslatef(((float) (x * 3) / 2) ,( (float)  (j * 3) / 2) ,((float) (z * 3) / 2));
					
					
					/* Compute some values based on arbitary combinations of trig functions */
					
                  float valA = (float) (Math.sin(x / (float) cubeNum * waveAngle) * Math.cos(j / (float) cubeNum * waveAngle)) * amplitude;
                  float valB = (float) (Math.sin(j / (float) cubeNum * waveAngle) * Math.cos(z / (float) cubeNum * waveAngle)) * amplitude;
                  float valC = (float) (Math.sin(z / (float) cubeNum * waveAngle) * Math.cos(x / (float) cubeNum * waveAngle)) * amplitude;

                  glTranslatef((float) x * distanceBetween, (float) j * distanceBetween,(float) z * distanceBetween);
                  

//                glTranslatef(((float) (x * 3) / 2) ,( (float)  (j * 3) / 2) ,((float) (z * 3) / 2));

//                glColor3f(1f / cubeNum * (x + cubeNum / 2), 1f / cubeNum * (j + cubeNum / 2), 1f / cubeNum * (z + cubeNum / 2));
                 
                  glTranslatef((float)-valA,(float)- valB, (float)-valC);

//      			glTranslatef(((float) (x * 3) / 2) * mul,( (float)  (j * 3) / 2) * mul,((float) (z * 3) / 2) * mul);
                  glTranslatef( x , j , z );
				

					ByteBuffer temp = ByteBuffer.allocateDirect(16);
					temp.order(ByteOrder.nativeOrder());
					glEnable(GL_LIGHT0);
					float lightPosition[] = { 10f, 10f, 10f, 0.5f };
					glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
					glEnable(GL_BLEND);
					glEnable(GL_LINE_SMOOTH);
					
					glLight(GL11.GL_LIGHT0,	GL11.GL_POSITION,(FloatBuffer) temp.asFloatBuffer().put(lightPosition).flip());
					
		
					// glVertexPointer(2, GL_FLOAT, 0, points);
//					glEnableClientState(GL_VERTEX_ARRAY);

//					 glDrawArrays(GL_LINE_STRIP, 0, num_points);

//					glDisableClientState(GL_VERTEX_ARRAY);
				
					// rotate next cube
//					 glRotatef(x*z*10+rotationAngle, 0, 0, 1);
//					 glRotatef( j*10+rotationAngle,0,0, 0); //interesting scale effect

		
						if(showWireframe){
							glEnable(GL_POLYGON_OFFSET_FILL);
							glEnable(GL_POLYGON_SMOOTH_HINT);
							glCallList(displayListHandle);
							glColor3f(0,0,0); // Color for shape outline
							glPolygonMode(GL_FRONT_AND_BACK, GL_LINE); // enable wireframe for front and back
							glCallList(displayListHandle); // Call the same rendering routine for the previous polygon.
							glPolygonMode(GL_FRONT_AND_BACK, GL_FILL); //fill shape again
							}

							glCallList(displayListHandle); // Call the same rendering routine for the previous polygon.
		

					// Remove current MODELVIEW Matrix from
					// stack.
					glPopMatrix();
				}
			}
		}

	

	}
	

	public static void main(String[] argv) {
		CubeList example = new CubeList();
		example.setUp();
		example.gameLoop();
	}
}
