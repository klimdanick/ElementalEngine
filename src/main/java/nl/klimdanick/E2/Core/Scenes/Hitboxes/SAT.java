package nl.klimdanick.E2.Core.Scenes.Hitboxes;

//SAT.java - collision detection + MTV
import java.util.List;

import nl.klimdanick.E2.Utils.Math.Vector2;

public final class SAT {
 private static final float EPS = 1e-6f;
 // polygon vs polygon
 public static Collision polygonPolygon(ConvexPolygon a, ConvexPolygon b) {
     List<Vector2> axesA = a.getAxes();
     List<Vector2> axesB = b.getAxes();

     float minOverlap = Float.POSITIVE_INFINITY;
     Vector2 smallestAxis = new Vector2(0,0);
     // test all axes from both polygons
     for (Vector2 axis : concat(axesA, axesB)) {
         HtiboxProjection pa = a.projectOntoAxis(axis);
         HtiboxProjection pb = b.projectOntoAxis(axis);

         if (!pa.overlaps(pb)) {
             return new Collision(false, new Vector2(0,0)); // separating axis found
         } else {
             float overlap = pa.getOverlap(pb);
             if (overlap < minOverlap) {
                 minOverlap = overlap;
                 smallestAxis = axis.cpy();
                 // ensure axis points from A to B (MTV direction)
                 Vector2 d = Vector2.sub(b.position, a.position);
                 if (d.dot(smallestAxis) < 0) smallestAxis.scl(-1f);
             }
         }
     }
     // If we get here, collision happened. MTV = smallestAxis * minOverlap
     Vector2 mtv = smallestAxis.scl(minOverlap + EPS); // small epsilon to avoid touching issues
     return new Collision(true, mtv);
 }

 // polygon vs circle
 public static Collision polygonCircle(ConvexPolygon poly, Vector2 circleCenter, float radius) {
     List<Vector2> axes = poly.getAxes();
     float minOverlap = Float.POSITIVE_INFINITY;
     Vector2 smallestAxis = new Vector2(0,0);

     // include axis from polygon edges
     for (Vector2 axis : axes) {
         HtiboxProjection pPoly = poly.projectOntoAxis(axis);
         // project circle onto axis: center projection +/- radius
         float cProj = axis.dot(circleCenter);
         HtiboxProjection pCircle = new HtiboxProjection(cProj - radius, cProj + radius);
         if (!pPoly.overlaps(pCircle)) return new Collision(false, new Vector2(0,0));
         float overlap = pPoly.getOverlap(pCircle);
         if (overlap < minOverlap) {
             minOverlap = overlap;
             smallestAxis = axis.cpy();
             Vector2 d = Vector2.sub(circleCenter, poly.position);
             if (d.dot(smallestAxis) < 0) smallestAxis.scl(-1f);
         }
     }

     // Also test axis from the nearest polygon vertex to circle center
     // find closest vertex
     List<Vector2> verts = poly.getWorldVertices();
     float bestDist2 = Float.POSITIVE_INFINITY;
     Vector2 closest = null;
     for (Vector2 v : verts) {
         float dx = circleCenter.x - v.x;
         float dy = circleCenter.y - v.y;
         float d2 = dx*dx + dy*dy;
         if (d2 < bestDist2) { bestDist2 = d2; closest = v; }
     }
     if (closest != null) {
         Vector2 axis = Vector2.sub(circleCenter, closest);
         if (axis.len2() == 0) {
             // circle center exactly on vertex: choose any axis (use first edge normal)
             axis = poly.getAxes().get(0).cpy();
         } else axis.nor();
         HtiboxProjection pPoly = poly.projectOntoAxis(axis);
         float cProj = axis.dot(circleCenter);
         HtiboxProjection pCircle = new HtiboxProjection(cProj - radius, cProj + radius);
         if (!pPoly.overlaps(pCircle)) return new Collision(false, new Vector2(0,0));
         float overlap = pPoly.getOverlap(pCircle);
         if (overlap < minOverlap) {
             minOverlap = overlap;
             smallestAxis = axis.cpy();
             Vector2 d = Vector2.sub(circleCenter, poly.position);
             if (d.dot(smallestAxis) < 0) smallestAxis.scl(-1f);
         }
     }

     Vector2 mtv = smallestAxis.scl(minOverlap + EPS);
     return new Collision(true, mtv);
 }

 // helper: concat two lists (cheap)
 private static <T> List<T> concat(List<T> a, List<T> b) {
     java.util.ArrayList<T> res = new java.util.ArrayList<>(a.size() + b.size());
     res.addAll(a); res.addAll(b); return res;
 }
}
