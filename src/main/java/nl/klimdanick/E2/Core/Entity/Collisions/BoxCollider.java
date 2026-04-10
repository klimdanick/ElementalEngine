package nl.klimdanick.E2.Core.Entity.Collisions;

import org.joml.Vector2f;

public class BoxCollider implements Collider {
	
	private boolean isStatic;
	private float x, y, w, h;
	
	public BoxCollider(float x, float y, float w, float h, boolean isStatic) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.isStatic = isStatic;
	}

	@Override
	public AABB getAABB() {
		return new AABB(x, y, w, h);
	}

	@Override
	public void onCollision(Collider other, Vector2f push) {
		
	}

	@Override
	public boolean isStatic() {
		return isStatic;
	}

	@Override
	public SAT getSAT() {
		return new SAT(new float[] {-w/2, -h/2, w/2, -h/2, w/2, h/2, -w/2, h/2}, x, y);
	}

}
