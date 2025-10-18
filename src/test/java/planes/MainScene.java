package planes;

import org.joml.Vector2f;

import nl.klimdanick.E2.Core.Rendering.DrawingMode;
import nl.klimdanick.E2.Core.Rendering.E2Color;
import nl.klimdanick.E2.Core.Rendering.Renderer;
import nl.klimdanick.E2.Core.Scenes.Scene;

public class MainScene extends Scene {
	
	Plane p;
	Missile[] m = new Missile[5];
	
	public MainScene() {
		p = new Plane(0, 0);
		for (int i = 0; i < m.length; i++) {
			m[i] = new Missile(-100+i*20, -100, 0);
			this.addObject(m[i]);
		}
		this.addObject(p);
	}

	@Override
	public void update(double dt) {
		
	}

	@Override
	public void preRender(Renderer r) {
		r.clear(E2Color.CURIOS_BLUE);
		r.drawMode = DrawingMode.FILL;
		camera.translation.x = p.x;
		camera.translation.y = p.y;
	}

	@Override
	public void postRender(Renderer r) {
	}

	public void reset() {
		p.x = 0;
		p.y = 0;
		p.rotation = (float)Math.PI;
	}

}
