package nl.klimdanick.E2.Particles.Systems;

import org.joml.Vector3f;

import nl.klimdanick.E2.Core.Rendering.Renderer;
import nl.klimdanick.E2.Particles.Particle;
import nl.klimdanick.E2.Particles.ParticleEmitter;
import nl.klimdanick.E2.Particles.ParticleSystem;
import nl.klimdanick.E2.Utils.E2Color;

public class Fire extends ParticleSystem {
	
	public float size;
	public float particleSize;
	
	public Fire(int maxParticles, Renderer r, float particleSize, float flameSize) {
		super(maxParticles, r);
		this.size = flameSize;
		this.particleSize = particleSize;
	}
	
	public void renderParticle(Particle p, float t) {
		E2Color c = new E2Color(1*t, 0.8*t*t*t, 0.2, 1);
		batch.rect(p.position.x, p.position.y, p.size, p.size, c);
	}
	
	public void emitter(int x, int y) {
		ParticleEmitter pe = new ParticleEmitter(50f*size, x, y) {
		    protected void initParticle(Particle p) {
		    	p.position = particleSpawnLocation(p.position);
		        p.velocity.x = (float)(Math.random() - 0.5) * 5f;
		        p.velocity.z = (float)(Math.random() - 0.5) * 5f;
		        p.velocity.y = (float)Math.random() * -15f;
	
		        p.maxLife = 2f*size;
		        p.life = p.maxLife;
	
		        p.size = particleSize;
		    }
		};
			
		addEmitter(pe);
	}
	
	public Vector3f particleSpawnLocation(Vector3f loc) {
		return loc;
	}
};
