package nl.klimdanick.E2.Core.Scenes.Hitboxes;

import java.util.ArrayList;
import java.util.List;

import nl.klimdanick.E2.Core.Rendering.DrawingMode;
import nl.klimdanick.E2.Core.Rendering.E2Color;
import nl.klimdanick.E2.Core.Rendering.Renderer;
import nl.klimdanick.E2.Core.Rendering.Texture;
import nl.klimdanick.E2.Core.Scenes.GameObject;
import nl.klimdanick.E2.Utils.Math.Vector2;

public class Hitbox {
	ConvexPolygon poly;
	GameObject obj;
	float[][] shape;
	
	public Hitbox(float[][] shape, float scale, GameObject obj) {
		this.shape = shape;
		this.obj = obj;
		List<Vector2> list = new ArrayList<>(shape.length);
		for (float[] v : shape) {
		    list.add(new Vector2(v[0], v[1]));
		}
		poly = new ConvexPolygon(list);
		poly.setPosition(obj.x, obj.y);
		poly.scale = scale;
	}
	
	public Collision getCollision(GameObject[] objects) {
		Collision ret = new Collision(false, new Vector2(0, 0));
		for (GameObject obj : objects) {			
			Collision c = SAT.polygonPolygon(poly, obj.hitbox.poly);
			if (!c.collided) continue;
			if (!ret.collided) {
				ret = c;
				continue;
			}
			if (c.mtv.len2() < ret.mtv.len2()) {
				ret = c;
			}
		}
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
		r.drawShape(poly.position.x, poly.position.y, shape, E2Color.TURMERIC_YELLOW, poly.scale, (float) (poly.rotationDeg/360.0*Math.PI*2));
	}
}
