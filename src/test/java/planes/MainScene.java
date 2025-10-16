package planes;

import nl.klimdanick.E2.Core.Rendering.DrawingMode;
import nl.klimdanick.E2.Core.Rendering.Renderer;
import nl.klimdanick.E2.Core.Scenes.Scene;

public class MainScene extends Scene {
	
	Plane p;
	Missile[] m = new Missile[100];
	
	public MainScene() {
		p = new Plane(1920/6, 1080/6);
		for (int i = 0; i < m.length; i++) {
			m[i] = new Missile(100+i*20, 100, 0);
			this.addObject(m[i]);
		}
		this.addObject(p);
	}

	@Override
	public void update(double dt) {
		
	}

	@Override
	public void preRender(Renderer r) {
		r.drawMode = DrawingMode.FILL;
	}

	@Override
	public void postRender(Renderer r) {

	}

	public void reset() {
		p.x = 1920/6;
		p.y = 1080/6;
		p.rotation = (float)Math.PI;
	}

}
