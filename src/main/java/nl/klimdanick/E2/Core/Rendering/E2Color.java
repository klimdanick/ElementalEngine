package nl.klimdanick.E2.Core.Rendering;

public class E2Color {
    public float r, g, b, a;

    public E2Color(float r, float g, float b) {
        this(r, g, b, 1.0f);
    }

    public E2Color(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }
    
    public E2Color clone() {
    	return new E2Color(r, g, b, a);
    }

    public static final E2Color WHITE = new E2Color(1, 1, 1);
    public static final E2Color BLACK = new E2Color(0, 0, 0);
    public static final E2Color RED   = new E2Color(1, 0, 0);
    public static final E2Color GREEN = new E2Color(0, 1, 0);
    public static final E2Color BLUE  = new E2Color(0, 0, 1);
    
    public static final E2Color CINDER_BLACK  		= new E2Color(0.0314f, 0.0627f, 0.0902f);
    public static final E2Color CURIOS_BLUE  		= new E2Color(0.1608f, 0.6039f, 0.8157f);
    public static final E2Color TURMERIC_YELLOW 	= new E2Color(0.8157f, 0.7176f, 0.2784f);
    public static final E2Color AQUA_GREEN  		= new E2Color(0.0196f, 0.8510f, 0.5765f);
    public static final E2Color DEBIAN_RED  		= new E2Color(0.8431f, 0.0549f, 0.2824f);
    public static final E2Color STRAWBERRY_MAGENTA  = new E2Color(1.0000f, 0.2863f, 0.6235f);
}