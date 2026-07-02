package nl.klimdanick.E2.Core.Entity.Collisions;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;

import nl.klimdanick.E2.Core.Rendering.Renderer;

public class CollisionSystem {

    private List<Collider> colliders = new ArrayList<>();
    public boolean debug = false;

    public List<Collider> getColliders() {
		return colliders;
	}

	public void add(Collider c) {
        colliders.add(c);
    }

    public void clear() {
        colliders.clear();
    }

    public void update() {

        int size = colliders.size();

        for (int i = 0; i < size; i++) {
            Collider a = colliders.get(i);

            for (int j = i + 1; j < size; j++) {
                Collider b = colliders.get(j);

                // skip static-static
                if (a.isStatic() && b.isStatic()) continue;

                // ---- BROAD PHASE (AABB) ----
                if (!a.getAABB().intersects(b.getAABB()))
                    continue;
                
                
                // ---- NARROW PHASE (SAT) ----
                Manifold m;
                if ((m = SAT.sat(a.getSAT(), b.getSAT())) != null) {

                    // ---- RESOLUTION ----
                    Vector2f push = resolve(a, b, m);
                    Vector2f pushB = new Vector2f();
                    push.mul(-1, pushB);
                    
                    if (a.isStatic()) push.mul(0);
                    if (b.isStatic()) pushB.mul(0);

                    a.onCollision(b, push, new Vector2f(m.contactX, m.contactY));
                    b.onCollision(a, pushB, new Vector2f(m.contactX, m.contactY));
                }
            }
        }
    }
    
    private Vector2f resolve(Collider a, Collider b, Manifold m) {

    	float pushX = m.normalX * m.depth;
        float pushY = m.normalY * m.depth;

        if (!a.isStatic() && !b.isStatic()) {
        	return new Vector2f(-pushX/2, -pushY/2); 
        } else {
        	return new Vector2f(-pushX/2, -pushY/2);
        }
    }
}