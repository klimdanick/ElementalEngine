package nl.klimdanick.E2.Core.Entity.Collisions;

public class SAT {
	
	public float[] vertices; // [x0,y0, x1,y1, ...]
	public float x, y;       // position
	
	public SAT(float[] vertices, float x , float y) {
		this.vertices = vertices;
		this.x = x;
		this.y = y;
	}
	
	public static Manifold sat(SAT a, SAT b) {

	    Result result = new Result();

	    if (!checkAxes(a, b, result)) return null;
	    if (!checkAxes(b, a, result)) return null;

	    // direction from A → B
	    float dx = b.x - a.x;
	    float dy = b.y - a.y;

	    // ensure normal points from A to B
	    if (dx * result.axisX + dy * result.axisY < 0) {
	        result.axisX *= -1;
	        result.axisY *= -1;
	    }

	    Manifold m = new Manifold();
	    m.normalX = result.axisX;
	    m.normalY = result.axisY;
	    m.depth = result.depth;

	    return m;
	}

	
	public Manifold sat(SAT b) {
		return sat(this, b);
	}
	
	private static boolean checkAxes(
	        SAT a,
	        SAT b,
	        Result result) {

	    for (int i = 0; i < a.vertices.length; i += 2) {

	        int next = (i + 2) % a.vertices.length;

	        float x1 = a.vertices[i];
	        float y1 = a.vertices[i+1];

	        float x2 = a.vertices[next];
	        float y2 = a.vertices[next+1];

	        // edge
	        float edgeX = x2 - x1;
	        float edgeY = y2 - y1;

	        // perpendicular axis
	        float axisX = -edgeY;
	        float axisY = edgeX;

	        // normalize axis (VERY IMPORTANT)
	        float len = (float)Math.sqrt(axisX*axisX + axisY*axisY);
	        axisX /= len;
	        axisY /= len;

	        float overlap = getOverlap(a, b, axisX, axisY);

	        if (overlap <= 0) return false;

	        // store smallest overlap
	        if (overlap < result.depth) {
	            result.depth = overlap;
	            result.axisX = axisX;
	            result.axisY = axisY;
	        }
	    }

	    return true;
	}
	
	private static float getOverlap(
	        SAT a,
	        SAT b,
	        float axisX,
	        float axisY) {

	    float[] projA = project(a, axisX, axisY);
	    float[] projB = project(b, axisX, axisY);

	    return Math.min(projA[1], projB[1]) -
	           Math.max(projA[0], projB[0]);
	}
	
	private static float[] project(SAT p, float axisX, float axisY) {

	    float min = Float.MAX_VALUE;
	    float max = -Float.MAX_VALUE;

	    for (int i = 0; i < p.vertices.length; i += 2) {

	        float vx = p.vertices[i] + p.x;
	        float vy = p.vertices[i+1] + p.y;

	        float dot = vx * axisX + vy * axisY;

	        min = Math.min(min, dot);
	        max = Math.max(max, dot);
	    }

	    return new float[]{min, max};
	}
	
}
