package nl.klimdanick.E2.Particles;

public abstract class ParticleEmitter {

    public float x, y;

    public float emissionRate;
    private float accumulator;
    
    public ParticleEmitter(float emisionRate, float x, float y) {
    	this.emissionRate = emisionRate;
    	this.x = x;
    	this.y = y;
    }

    public void update(float dt, ParticleSystem system) {

        accumulator += emissionRate * dt;

        while(accumulator >= 1f) {
            accumulator--;

            Particle p = system.spawn(x, y);
            initParticle(p);
        }
    }

    protected abstract void initParticle(Particle p);
}