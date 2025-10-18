package nl.klimdanick.E2.Core.Scenes.Hitboxes;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;

import nl.klimdanick.E2.Core.Rendering.DrawingMode;
import nl.klimdanick.E2.Core.Rendering.E2Color;
import nl.klimdanick.E2.Core.Rendering.Renderer;
import nl.klimdanick.E2.Core.Scenes.GameObject;
import nl.klimdanick.E2.Core.Scenes.Hitboxes.SAT.ConvexPolygon;

public class Hitbox {
	ConvexPolygon poly;
	GameObject obj;
	float[][] shape;
	Collision lastCollision;
	
	public Hitbox(float[][] shape, float scale, GameObject obj) {
		this.shape = shape;
		this.obj = obj;
		poly = new ConvexPolygon(shape);
		poly.setPosition(obj.x, obj.y);
		poly.scale = scale;
	}
	
	public Collision getCollision(GameObject[] objects) {
		Collision ret = new Collision(false, new Vector2f(0, 0));
		for (GameObject obj : objects) {			
			Collision c = SAT.polygonPolygon(poly, obj.hitbox.poly);
			if (!c.collided) continue;
			if (!ret.collided) {
				ret = c;
				continue;
			}
			if (c.mtv.length() < ret.mtv.length()) {
				ret = c;
			}
		}
		lastCollision = ret;
		return ret;
	}
	
	public void update() {
		poly.setPosition(obj.x, obj.y);
		setRotation(obj.rotation);
	}
	
	public void setRotation(float angle) {
		poly.setRotation(360-(float) (angle*360/(Math.PI*2)));
	}
	
	public void render(Renderer r) {
		r.drawMode = DrawingMode.OUTLINE;
		E2Color c = E2Color.TURMERIC_YELLOW.clone();
		if (lastCollision != null && lastCollision.collided) {
//			c.a = 0.5f;
			r.drawMode = DrawingMode.FILL;
		}
		r.drawShape(poly.position.x, poly.position.y, shape, c, poly.scale, (float) (poly.rotationDeg/360.0*Math.PI*2));
	}
}
