package nl.klimdanick.E2.Core.Entity.Collisions;

public class AABB {
    public float x, y; // center
    public float w, h; // size

    public AABB(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
    
    public static boolean intersects(AABB a, AABB b) {
        return Math.abs(a.x - b.x) < (a.w / 2 + b.w / 2) &&
               Math.abs(a.y - b.y) < (a.h / 2 + b.h / 2);
    }
    
    public boolean intersects(AABB b) {
        return intersects(this, b);
    }
}