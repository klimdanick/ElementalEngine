package nl.klimdanick.E2.Particles;

import org.joml.Vector2f;

public class Particle {

    public Vector2f position;
    public Vector2f velocity;

    public float rotation;
    public float angularVelocity;

    public float size;

    public float life;
    public float maxLife;

    public boolean active;
    
    public Particle(float maxLife) {
		this.position = new Vector2f();
		this.velocity = new Vector2f();
		this.rotation = 0;
		this.angularVelocity = 0;
		this.size = 1;
		this.maxLife = maxLife;
	}
    
    public Particle(Vector2f position, Vector2f velocity, float rotation, float angularVelocity, float size, float maxLife) {
		this.position = position;
		this.velocity = velocity;
		this.rotation = rotation;
		this.angularVelocity = angularVelocity;
		this.size = size;
		this.maxLife = maxLife;
	}

	public void update(double dt) {
    	if (!active) return;
    	
    	life-=dt;
    	
    	if (this.life <= 0) active = false;
    	
    	Vector2f speed = new Vector2f();
    	velocity.mul((float)dt, speed);
    	this.position.add(speed);
    }
}