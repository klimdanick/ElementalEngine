package nl.klimdanick.E2.Core.Scenes.Hitboxes;

//Projection.java (internal helper)
public final class HtiboxProjection {
 public float min, max;
 public HtiboxProjection(float min, float max) { this.min = min; this.max = max; }
 public boolean overlaps(HtiboxProjection other) {
     return !(this.max < other.min || other.max < this.min);
 }
 public float getOverlap(HtiboxProjection other) {
     if (!overlaps(other)) return 0f;
     return Math.min(this.max, other.max) - Math.max(this.min, other.min);
 }
}
