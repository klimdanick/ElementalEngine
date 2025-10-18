package planes;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_R;

import nl.klimdanick.E2.Core.Input;
import nl.klimdanick.E2.Core.Debugging.DebugPanel;
import nl.klimdanick.E2.Core.Rendering.E2Color;
import nl.klimdanick.E2.Core.Rendering.Renderer;
import nl.klimdanick.E2.Core.Scenes.GameObject;
import nl.klimdanick.E2.Core.Scenes.Hitboxes.Collision;
import nl.klimdanick.E2.Core.Scenes.Hitboxes.Hitbox;

public class Plane extends GameObject {
	
	private static final float[][] shape = new float[][] {
		{0, 0.5f}, {-0.1f, 0.5f}, {-1f, 0.4f}, {-0.8f, 0.1f}, {-0.2f, -0.1f}, {-0.1f, -0.8f}, {-0.3f, -0.8f}, {-0.3f, -0.9f}, {-0.1f, -0.9f}, {0, -1},
		{0, -1}, {0.1f, -0.9f}, {0.3f, -0.9f}, {0.3f, -0.8f}, {0.1f, -0.8f}, {0.2f, 0f}, {0.8f, 0.1f}, {1f, 0.5f}, {0.1f, 0.5f}, {0, 0.5f}
	};
	
	private static final float[][] noseShape = new float[][] {
		{0, 0.8f}, {-0.1f, 0.7f}, {-0.1f, 0.5f},
		{0.1f, 0.5f}, {0.1f, 0.7f}, {0, 0.8f} 
	};
	
	private float[][] wingsShape = new float[][] {
		{-0.1f, 0.5f}, {-1f, 0.4f}, {-0.8f, 0.1f}, {-0.2f, 0f},
		{0.2f, 0f}, {0.8f, 0.1f}, {1f, 0.4f}, {0.1f, 0.5f}
	};
	private float[][] bodyShape = new float[][] {
		{-0.2f, 0f}, {-0.1f, -0.8f},
		{0.1f, -0.8f}, {0.2f, 0f}
	};
	private float[][] tailShape = new float[][] {
		{-0.1f, -0.8f}, {-0.3f, -0.8f}, {-0.3f, -0.9f}, {-0.1f, -0.9f}, {0, -1},
		{0, -1}, {0.1f, -0.9f}, {0.3f, -0.9f}, {0.3f, -0.8f}, {0.1f, -0.8f} 
	};

	public Plane(float x, float y) {
		super(x, y, 20, 20, (float)Math.PI);
		this.hitbox = new Hitbox(shape, w/2, this);
	}
	
	@Override
	public void render(Renderer r) {
		r.clear(new E2Color(0f, 0f, 0f, 0f));
		r.drawShape(w/2, h/2, noseShape, E2Color.DEBIAN_RED, w/2, 0);
		r.drawShape(w/2, h/2, wingsShape, E2Color.DEBIAN_RED, w/2, 0);
		r.drawShape(w/2, h/2, bodyShape, E2Color.DEBIAN_RED, w/2, 0);
		r.drawShape(w/2, h/2, tailShape, E2Color.DEBIAN_RED, w/2, 0);
	}

	@Override
	public void update(double dt) {
		DebugPanel.rows.put("rotation", (int)(rotation*360/(Math.PI*2)));
		DebugPanel.rows.put("x", (int)(x));
		DebugPanel.rows.put("y", (int)(y));
		
		if (Input.isKeyDown(GLFW_KEY_A)) rotation += 3*dt;
		if (Input.isKeyDown(GLFW_KEY_D)) rotation -= 3*dt;
		
		if (Input.isKeyDown(GLFW_KEY_R)) {
			if (scene instanceof MainScene) {
				((MainScene)scene).reset();
			}
		}
		
		if (this.scene instanceof MainScene) {
			MainScene s = (MainScene)scene;
			Collision c = hitbox.getCollision(s.m);
			if (c.collided) {
//				System.out.println(c);
//				s.reset();
				x-=c.mtv.x;
				y-=c.mtv.y;
			} else {
				double speed = 100*dt;
				x+=Math.sin(rotation)*speed;
				y+=Math.cos(rotation)*speed;
			}
		}
		
	}
}
