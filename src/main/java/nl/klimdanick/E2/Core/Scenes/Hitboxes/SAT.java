package nl.klimdanick.E2.Core.Scenes.Hitboxes;

import java.util.ArrayList;
//SAT.java - collision detection + MTV
import java.util.List;

import org.joml.Vector2f;

public class SAT {

    public static class ConvexPolygon {
        public final List<Vector2f> localVerts = new ArrayList<>();
        public final List<Vector2f> worldVerts = new ArrayList<>();
        public final Vector2f position = new Vector2f();
        public float scale = 1;
        public float rotationDeg = 0f;

        public ConvexPolygon(float[][] verts) {
            for (float[] v : verts) {
                localVerts.add(new Vector2f(v[0], v[1]));
                worldVerts.add(new Vector2f(v[0], v[1]));
            }
            recomputeWorldVerts();
        }

        public void setPosition(float x, float y) {
            position.set(x, y);
            recomputeWorldVerts();
        }

        public void setRotation(float deg) {
            rotationDeg = deg;
            recomputeWorldVerts();
        }

        /** Recompute transformed vertices into world space */
        public void recomputeWorldVerts() {
            float rad = (float) Math.toRadians(rotationDeg);
            float cos = (float) Math.cos(rad);
            float sin = (float) Math.sin(rad);

            for (int i = 0; i < localVerts.size(); i++) {
                Vector2f lv = localVerts.get(i);
                // Apply scale -> rotation -> translation
                float sx = lv.x * scale;
                float sy = lv.y * scale;

                float wx = sx * cos - sy * sin + position.x;
                float wy = sx * sin + sy * cos + position.y;

                worldVerts.get(i).set(wx, wy);
            }
        }

        public List<Vector2f> getAxes() {
            List<Vector2f> axes = new ArrayList<>();
            int n = worldVerts.size();
            for (int i = 0; i < n; i++) {
                Vector2f a = worldVerts.get(i);
                Vector2f b = worldVerts.get((i + 1) % n);
                Vector2f edge = new Vector2f(b).sub(a);
                if (edge.lengthSquared() < 1e-6f) continue; // skip degenerate
                Vector2f normal = new Vector2f(-edge.y, edge.x).normalize(); // perpendicular
                axes.add(normal);
            }
            return axes;
        }

        public Projection projectOnto(Vector2f axis) {
            float min = axis.dot(worldVerts.get(0));
            float max = min;
            for (int i = 1; i < worldVerts.size(); i++) {
                float p = axis.dot(worldVerts.get(i));
                if (p < min) min = p;
                if (p > max) max = p;
            }
            return new Projection(min, max);
        }
    }

    private static class Projection {
        float min, max;
        Projection(float min, float max) { this.min = min; this.max = max; }
        boolean overlaps(Projection other) { return !(this.max < other.min || other.max < this.min); }
        float getOverlap(Projection other) {
            return Math.min(this.max, other.max) - Math.max(this.min, other.min);
        }
    }

    // --- POLYGON vs POLYGON ---
    public static Collision polygonPolygon(ConvexPolygon a, ConvexPolygon b) {
        List<Vector2f> axes = new ArrayList<>();
        axes.addAll(a.getAxes());
        axes.addAll(b.getAxes());

        float minOverlap = Float.POSITIVE_INFINITY;
        Vector2f smallestAxis = new Vector2f();

        for (Vector2f axis : axes) {
            Projection pA = a.projectOnto(axis);
            Projection pB = b.projectOnto(axis);

            if (!pA.overlaps(pB)) {
                return new Collision(false, new Vector2f()); // no collision
            }

            float overlap = pA.getOverlap(pB);
            if (overlap < minOverlap) {
                minOverlap = overlap;
                smallestAxis.set(axis);
                // Ensure MTV points from A to B
                Vector2f direction = new Vector2f(b.position).sub(a.position);
                if (direction.dot(smallestAxis) < 0) smallestAxis.negate();
            }
        }

        // Multiply by overlap to get MTV
        Vector2f mtv = new Vector2f(smallestAxis).mul(minOverlap);
        return new Collision(true, mtv);
    }

    // --- POLYGON vs CIRCLE (optional) ---
    public static Collision polygonCircle(ConvexPolygon poly, Vector2f center, float radius) {
        List<Vector2f> axes = new ArrayList<>(poly.getAxes());

        // Find the closest polygon vertex to circle center
        Vector2f closest = null;
        float minDist2 = Float.POSITIVE_INFINITY;
        for (Vector2f v : poly.worldVerts) {
            float d2 = v.distanceSquared(center);
            if (d2 < minDist2) {
                minDist2 = d2;
                closest = v;
            }
        }
        if (closest != null) {
            Vector2f axisToCenter = new Vector2f(center).sub(closest);
            if (axisToCenter.lengthSquared() > 1e-6f) axes.add(axisToCenter.normalize());
        }

        float minOverlap = Float.POSITIVE_INFINITY;
        Vector2f smallestAxis = new Vector2f();

        for (Vector2f axis : axes) {
            Projection pPoly = poly.projectOnto(axis);
            float cProj = axis.dot(center);
            Projection pCircle = new Projection(cProj - radius, cProj + radius);

            if (!pPoly.overlaps(pCircle)) return new Collision(false, new Vector2f());
            float overlap = pPoly.getOverlap(pCircle);
            if (overlap < minOverlap) {
                minOverlap = overlap;
                smallestAxis.set(axis);
                Vector2f d = new Vector2f(center).sub(poly.position);
                if (d.dot(smallestAxis) < 0) smallestAxis.negate();
            }
        }

        Vector2f mtv = new Vector2f(smallestAxis).mul(minOverlap);
        return new Collision(true, mtv);
    }
}
