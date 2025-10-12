package nl.klimdanick.E2.Core.Rendering;

public class Animation extends TextureSubRegion {

	int frame = 0;
	int n, m;
	int t = 0, interval;
	
	public Animation(String path, float srcW, float srcH, int n, int m, int interval) {
		super(path, 0, 0, srcW, srcH);
		this.interval = interval;
		this.n = n;
		this.m = m;
	}
	
	public void play() {
		t++;
		if (t < interval) return;
		t = 0;
		frame++;
		if (frame >= n*m) frame = 0;
		srcX = srcW* (frame%n);
		srcY = srcH* Math.floorDiv(frame, n);
		
		calcVerts();
	}

}
