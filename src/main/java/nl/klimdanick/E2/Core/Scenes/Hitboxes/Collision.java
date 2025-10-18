package nl.klimdanick.E2.Core.Scenes.Hitboxes;

import org.joml.Vector2f;

import nl.klimdanick.E2.Core.Scenes.GameObject;

public class Collision {
    public boolean collided;
    public Vector2f mtv; // minimum translation vector to separate shapeA from shapeB (push A out)
    public GameObject obj;
    
    public Collision(boolean collided, Vector2f mtv) { this.collided = collided; this.mtv = mtv; }
    
    public String toString() {
    	return "collided: " + collided + ", mtv" + mtv.toString();
    }
}
