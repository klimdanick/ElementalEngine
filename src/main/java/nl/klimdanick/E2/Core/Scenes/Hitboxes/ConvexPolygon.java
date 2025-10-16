package nl.klimdanick.E2.Core.Scenes.Hitboxes;

//ConvexPolygon.java
import java.util.ArrayList;
import java.util.List;

import nl.klimdanick.E2.Utils.Math.Vector2;

public class ConvexPolygon {
 // vertices in local space, ordered clockwise or counterclockwise (must be convex)
 private final List<Vector2> localVerts;
 // world-space cache (recomputed each transform change)
 private final List<Vector2> worldVerts = new ArrayList<>();
 public Vector2 position = new Vector2(0,0);
 public float rotationDeg = 0f; // rotation around origin of polygon (degrees)
 public float scale = 1f; // optional uniform scale

 public ConvexPolygon(List<Vector2> localVerts) {
     this.localVerts = new ArrayList<>(localVerts);
     for (int i = 0; i < localVerts.size(); i++) worldVerts.add(new Vector2());
     recomputeWorldVerts();
 }

 public void setPosition(float x, float y) { position.set(x,y); recomputeWorldVerts(); }
 public void setRotation(float deg) { rotationDeg = deg; recomputeWorldVerts(); }
 public void recomputeWorldVerts() {
     float rad = (float)Math.toRadians(rotationDeg);
     float cos = (float)Math.cos(rad), sin = (float)Math.sin(rad);
     for (int i = 0; i < localVerts.size(); i++) {
         Vector2 lv = localVerts.get(i);
         float wx = (lv.x * cos - lv.y * sin) * scale + position.x;
         float wy = (lv.x * sin + lv.y * cos) * scale + position.y;
         worldVerts.get(i).set(wx, wy);
     }
 }

 public List<Vector2> getWorldVertices() { return worldVerts; }

 // produce axes (normals) from edges: one axis per edge (perpendicular)
 public List<Vector2> getAxes() {
     List<Vector2> axes = new ArrayList<>();
     int n = worldVerts.size();
     for (int i = 0; i < n; i++) {
         Vector2 a = worldVerts.get(i);
         Vector2 b = worldVerts.get((i+1)%n);
         Vector2 edge = Vector2.sub(b, a);
         Vector2 axis = edge.perp().nor(); // perpendicular normalized
         axes.add(axis);
     }
     return axes;
 }

 public HtiboxProjection projectOntoAxis(Vector2 axis) {
     float min = axis.dot(worldVerts.get(0));
     float max = min;
     for (int i = 1; i < worldVerts.size(); i++) {
         float p = axis.dot(worldVerts.get(i));
         if (p < min) min = p;
         if (p > max) max = p;
     }
     return new HtiboxProjection(min, max);
 }
}
