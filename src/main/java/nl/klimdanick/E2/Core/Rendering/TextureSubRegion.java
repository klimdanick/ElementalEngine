package nl.klimdanick.E2.Core.Rendering;

public class TextureSubRegion extends Texture {
	
	float srcX, srcY, srcW, srcH;
	
	public TextureSubRegion(String path, float srcX, float srcY, float srcW, float srcH) {
		super(path);
		
		this.srcX = srcX;
		this.srcY = srcY;
		this.srcW = srcW;
		this.srcH = srcH;
		
		calcVerts();
	}
	
	protected void calcVerts() {
		float u0 = srcX / getWidth();
	    float v0 = srcY / getHeight();
	    float u1 = (srcX + srcW) / getWidth();
	    float v1 = (srcY + srcH) / getHeight();
	    
		float[] verts = new float[] {
		        -0.5f,  0.5f,  u0, v0,   // top-left
		         0.5f,  0.5f,  u1, v0,   // top-right
		         0.5f, -0.5f,  u1, v1,   // bottom-right
		        -0.5f, -0.5f,  u0, v1    // bottom-left
		};
		
		
		
		this.quad = new Quad(verts);
	}
	
	public void setSubRegionOrigin(float srcX, float srcY) {
		this.srcX = srcX;
		this.srcY = srcY;
		calcVerts();
	}

}
