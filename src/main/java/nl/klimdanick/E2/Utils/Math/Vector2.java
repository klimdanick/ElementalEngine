package nl.klimdanick.E2.Utils.Math;

//Vector2.java
public final class Vector2 {
 public float x, y;

 public Vector2() { this(0,0); }
 public Vector2(float x, float y) { this.x = x; this.y = y; }

 public Vector2 set(float x, float y) { this.x = x; this.y = y; return this; }
 public Vector2 cpy() { return new Vector2(x,y); }

 public Vector2 add(Vector2 o) { x += o.x; y += o.y; return this; }
 public Vector2 sub(Vector2 o) { x -= o.x; y -= o.y; return this; }
 public Vector2 scl(float s) { x *= s; y *= s; return this; }

 public float dot(Vector2 o) { return x*o.x + y*o.y; }
 public float len2() { return x*x + y*y; }
 public float len() { return (float)Math.sqrt(len2()); }

 public Vector2 nor() {
     float l = len();
     if (l != 0f) { x /= l; y /= l; }
     return this;
 }

 public Vector2 perp() { // returns a perpendicular vector (rotated 90deg)
     return new Vector2(-y, x);
 }

 public static Vector2 sub(Vector2 a, Vector2 b) { return new Vector2(a.x - b.x, a.y - b.y); }

 public String toString() { return String.format("Vector2(%.3f, %.3f)", x, y); }
}
