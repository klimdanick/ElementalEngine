package nl.klimdanick.E2.Core.Scenes.Hitboxes;

import nl.klimdanick.E2.Core.Scenes.GameObject;
import nl.klimdanick.E2.Utils.Math.Vector2;

public class Collision {
    public boolean collided;
    public Vector2 mtv; // minimum translation vector to separate shapeA from shapeB (push A out)
    public GameObject obj;
    
    public Collision(boolean collided, Vector2 mtv) { this.collided = collided; this.mtv = mtv; }
    
    public String toString() {
    	return "collided: " + collided + ", mtv" + mtv.toString();
    }
}
