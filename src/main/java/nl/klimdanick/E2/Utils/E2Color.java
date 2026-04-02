package nl.klimdanick.E2.Utils;

public class E2Color {

    public float r, g, b, a;

    public E2Color(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }
    
    public E2Color(double r, double g, double b, double a) {
        this.r = (float)r;
        this.g = (float)g;
        this.b = (float)b;
        this.a = (float)a;
    }

    // Common colors
    public static final E2Color WHITE = new E2Color(1, 1, 1, 1);
    public static final E2Color RED   = new E2Color(1, 0, 0, 1);
    public static final E2Color GREEN = new E2Color(0, 1, 0, 1);
    public static final E2Color BLUE  = new E2Color(0, 0, 1, 1);
    public static final E2Color BLACK  = new E2Color(0, 0, 0, 1);
    
    public static final E2Color CURIOS_BLUE = new E2Color(0.161, 0.604, 0.816, 1);
	public static final E2Color TURMERIC_YELLOW = new E2Color(0.816, 0.718, 0.278, 1);
	public static final E2Color AQUA_GREEN = new E2Color(0.02, 0.851, 0.576, 1);
	public static final E2Color DEBIAN_RED = new E2Color(0.843, 0.0549, 0.282, 1);
	public static final E2Color CINDER_BLACK = new E2Color(0.031, 0.062, 0.090, 1);
}
