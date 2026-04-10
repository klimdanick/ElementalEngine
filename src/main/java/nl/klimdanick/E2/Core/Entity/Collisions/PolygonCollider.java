package nl.klimdanick.E2.Core.Entity.Collisions;

import org.joml.Vector2f;

public class PolygonCollider implements Collider {

    public boolean isStatic;
	private float[] vertices;
	private float x;
	private float y;

    public PolygonCollider(float[] vertices, float x, float y, boolean isStatic) {
        this.vertices = vertices;
        this.x = x;
        this.y = y;
        this.isStatic = isStatic;
    }

    @Override
    public AABB getAABB() {
        float minX = Float.MAX_VALUE, minY = Float.MAX_VALUE;
        float maxX = -Float.MAX_VALUE, maxY = -Float.MAX_VALUE;

        for (int i = 0; i < vertices.length; i += 2) {
            float vx = vertices[i] + x;
            float vy = vertices[i+1] + y;

            minX = Math.min(minX, vx);
            minY = Math.min(minY, vy);
            maxX = Math.max(maxX, vx);
            maxY = Math.max(maxY, vy);
        }

        return new AABB(
            (minX + maxX)/2,
            (minY + maxY)/2,
            maxX - minX,
            maxY - minY
        );
    }

    @Override
    public boolean isStatic() {
        return isStatic;
    }

	@Override
	public void onCollision(Collider other, Vector2f push) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SAT getSAT() {
		return new SAT(vertices, x, y);
	}
}