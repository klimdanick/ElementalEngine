package nl.klimdanick.E2.Core.Entity.Collisions;

public class Manifold {
    float normalX, normalY; // push direction
    float depth;            // how much to push
	public float contactX, contactY;
	public float pushX, pushY;
}