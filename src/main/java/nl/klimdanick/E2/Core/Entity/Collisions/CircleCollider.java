package nl.klimdanick.E2.Core.Entity.Collisions;

import org.joml.Vector2f;

public class CircleCollider implements Collider {
	float x, y, radius;
	boolean isStatic;
	
	public CircleCollider(float x, float y, float radius, boolean isStatic) {
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.isStatic = isStatic;
	}

	@Override
	public AABB getAABB() {
		return new AABB(x, y, radius*2, radius*2);
	}

	@Override
	public boolean isStatic() {
		return isStatic;
	}

	@Override
	public void onCollision(Collider other, Vector2f push, Vector2f location) {
	}

	@Override
	public SAT getSAT() {
		int res = 36;
		float delta = (float) (360/res);
		float[] verts = new float[res*2];
		for (int i = 0; i < res*2; i+=2) {
			verts[i] = (float) (Math.sin(Math.toRadians(i*delta))*radius);
			verts[i+1] = (float) (Math.cos(Math.toRadians(i*delta))*radius);
		}
		return new SAT(verts, x, y);
	}

}
