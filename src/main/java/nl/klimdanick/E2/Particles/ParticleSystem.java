package nl.klimdanick.E2.Particles;

import java.util.ArrayList;
import java.util.List;

import nl.klimdanick.E2.Core.Rendering.Renderer;
import nl.klimdanick.E2.Core.Rendering.ShapeBatch;
import nl.klimdanick.E2.Utils.E2Color;

public abstract class ParticleSystem {

    private Particle[] particles;
    public int cursor = 0;

    private List<ParticleEmitter> emitters = new ArrayList<>();
    
    protected ShapeBatch batch;

    public ParticleSystem(int maxParticles, Renderer r) {
        particles = new Particle[maxParticles];

        for(int i = 0; i < maxParticles; i++) {
            particles[i] = new Particle(100);
        }
        
        batch = new ShapeBatch(r.camera);
        
        r.batches.add(batch);
    }

    public Particle spawn(float x, float y) {

        Particle p = particles[cursor];

        cursor = (cursor + 1) % particles.length;

        p.active = true;
        p.position.x = x;
        p.position.y = y;

        return p;
    }

    public void update(float dt) {

        for (ParticleEmitter e : emitters)
            e.update(dt, this);

        for (Particle p : particles) {
            if (!p.active) continue;

            p.update(dt);

//            if (p.life <= 0)
//                p.active = false;
        }
    }
    
    public void renderAll() {
    	for (Particle p : particles) {
            if (!p.active) continue;
            float t = p.life/p.maxLife;
            renderParticle(p, t);
        }
    }

	public abstract void renderParticle(Particle p, float t);
	
	public void addEmitter(ParticleEmitter pe) {
		this.emitters.add(pe);
	}
}