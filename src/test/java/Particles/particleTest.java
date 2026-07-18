package Particles;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import nl.klimdanick.E2.Core.E2;
import nl.klimdanick.E2.Core.Game;
import nl.klimdanick.E2.Core.Input.Input;
import nl.klimdanick.E2.Particles.Systems.Fire;

public class particleTest extends Game {
	
	public static E2 engine;
	public Fire ps;
	
	public static void main(String[] args) {
		engine = new E2(new particleTest(), 480, 250); // 480, 250
		engine.run();
	}

	@Override
	public void init() {
		
		ps = new Fire(5000, renderer, 2, 3) {
			public Vector3f particleSpawnLocation(Vector3f loc) {
				float angle = (float) (Math.random()*360);
				loc.x += Math.sin(angle)*30;
				loc.y += Math.cos(angle)*30;
				return loc;
			}
		};
		ps.emitter(240, 125);
		
		Input.bind("spawn", GLFW.GLFW_KEY_SPACE);
	}

	@Override
	public void update(float dt) {
		ps.update(dt);
	}

	@Override
	public void render() {
		ps.renderAll();
	}
}
