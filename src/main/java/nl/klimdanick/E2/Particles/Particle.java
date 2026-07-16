package nl.klimdanick.E2.Particles;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class Particle {

    public Vector3f position;
    public Vector3f velocity;

    public float rotation;
    public float angularVelocity;

    public float size;

    public float life;
    public float maxLife;

    public boolean active;
    
    public Particle(float maxLife) {
		this.position = new Vector3f();
		this.velocity = new Vector3f();
		this.rotation = 0;
		this.angularVelocity = 0;
		this.size = 1;
		this.maxLife = maxLife;
	}
    
    public Particle(Vector3f position, Vector3f velocity, float rotation, float angularVelocity, float size, float maxLife) {
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
    	
    	Vector3f speed = new Vector3f();
    	velocity.mul((float)dt, speed);
    	this.position.add(speed);
    }
}