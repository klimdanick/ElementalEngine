package nl.klimdanick.E2.Core.Rendering;

import org.joml.Matrix3f;
import org.joml.Vector2f;

import nl.klimdanick.E2.Core.GameLoop;

public class Camera {
	public Matrix3f projection;
	
    public Vector2f translation;
    
    public Vector2f scale;
    
    public float rotation;
    
    public Camera() {
    	translation = new Vector2f(	0,
									0 );
    	
    	scale = new Vector2f( 1,
				  			  1 );
    	
    	projection = new Matrix3f(	1, 0, 0,
									0, 1, 0,
									0, 0, 1 );
    	
    	rotation = 0;
    }
    
    public static Camera defaultCam = new Camera(); 
}
