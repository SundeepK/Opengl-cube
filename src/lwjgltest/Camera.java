package lwjgltest;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

public class Camera {

	Vector3f position;
	float yaw = 0.0f;
	float pitch = 0.0f;
	
	public Camera(float x, float y, float z)
	{
		position = new Vector3f(x, y, z);
	}
	
	public void yaw(float amount)
	{
	    //increment the yaw by the amount param
	    yaw += amount;
	}
	 
	//increment the camera's current yaw rotation
	public void pitch(float amount)
	{
	    //increment the pitch by the amount param
	    pitch += amount;
	}
	
	public void walkForward(float distance)
	{
	    position.x -= distance * (float)Math.sin(Math.toRadians(yaw));
	    position.y += distance * (float)Math.cos(Math.toRadians(yaw));
	}
	 
	//moves the camera backward relative to its current rotation (yaw)
	public void walkBackwards(float distance)
	{
	    position.x += distance * (float)Math.sin(Math.toRadians(yaw));
	    position.y -= distance * (float)Math.cos(Math.toRadians(yaw));
	}
	 
	//strafes the camera left relitive to its current rotation (yaw)
	public void strafeLeft(float distance)
	{
	    position.x -= distance * (float)Math.sin(Math.toRadians(yaw-90));
	    position.y += distance * (float)Math.cos(Math.toRadians(yaw-90));
	}
	 
	//strafes the camera right relitive to its current rotation (yaw)
	public void strafeRight(float distance)
	{
	    position.x -= distance * (float)Math.sin(Math.toRadians(yaw+90));
	    position.y += distance * (float)Math.cos(Math.toRadians(yaw+90));
	}
	
	   public void lookThrough()
	    {
	        //roatate the pitch around the X axis
	        glRotatef(pitch, 1.0f, 0.0f, 0.0f);
	        //roatate the yaw around the Y axis
	        glRotatef(yaw, 0.0f, 0.0f, 1.0f);
	        //translate to the position vector's location
	        glTranslatef(position.x, position.y, position.z);
	    }
	
	
}
