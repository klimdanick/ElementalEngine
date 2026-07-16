package Particles;

import org.lwjgl.glfw.GLFW;

import nl.klimdanick.E2.Core.E2;
import nl.klimdanick.E2.Core.Game;
import nl.klimdanick.E2.Core.Input.Input;
import nl.klimdanick.E2.Particles.Particle;
import nl.klimdanick.E2.Particles.ParticleEmitter;
import nl.klimdanick.E2.Particles.ParticleSystem;
import nl.klimdanick.E2.Utils.E2Color;

public class particleTest extends Game {
	
	public static E2 engine;
	public ParticleSystem ps;
	
	public static void main(String[] args) {
		engine = new E2(new particleTest(), 480, 250); // 480, 250
		engine.run();
	}

	@Override
	public void init() {
		ps = new ParticleSystem(1000, renderer) {
			public void renderParticle(Particle p, float t) {
				E2Color c = new E2Color(1*t, 0.8*t, 0.2, 1);
				batch.rect(p.position.x, p.position.y, p.size, p.size, c);
			}
		};
		
		ParticleEmitter pe = new ParticleEmitter(50f, engine.getWidth()/2 + (int)(Math.random()*200-100), (int)(engine.getHeight()/2 + Math.random()*200-100)) {
		    protected void initParticle(Particle p) {
		        p.velocity.x = (float)(Math.random() - 0.5) * 5f;
		        p.velocity.z = (float)(Math.random() - 0.5) * 5f;
		        p.velocity.y = (float)Math.random() * -15f;
	
		        p.life = 2f;
		        p.maxLife = 2f;
	
		        p.size = 2f;
		    }
		};
			
		ps.addEmitter(pe);
		
		ParticleEmitter pe2 = new ParticleEmitter(50f, engine.getWidth()/2 + (int)(Math.random()*200-100), (int)(engine.getHeight()/2 + Math.random()*200-100)) {
		    protected void initParticle(Particle p) {
		        p.velocity.x = (float)(Math.random() - 0.5) * 5f;
		        p.velocity.z = (float)(Math.random() - 0.5) * 5f;
		        p.velocity.y = (float)Math.random() * -15f;
	
		        p.life = 2f;
		        p.maxLife = 2f;
	
		        p.size = 1f;
		    }
		};
			
		ps.addEmitter(pe2);
		
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
