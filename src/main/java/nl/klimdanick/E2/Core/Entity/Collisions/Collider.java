package nl.klimdanick.E2.Core.Entity.Collisions;

import org.joml.Vector2f;

public interface Collider {
	public AABB getAABB();
	public SAT getSAT();
	void onCollision(Collider other, Vector2f push, Vector2f location);
	boolean isStatic();
}
