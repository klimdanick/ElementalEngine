package nl.klimdanick.E2.Core.Entity;

import org.joml.Matrix2f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import nl.klimdanick.E2.Core.Entity.Collisions.AABB;
import nl.klimdanick.E2.Core.Entity.Collisions.Collider;
import nl.klimdanick.E2.Core.Entity.Collisions.SAT;

public class PhysicsEntity extends Entity implements Collider{
	protected Vector3f velocity = new Vector3f();
	protected Vector3f accumulatedForce = new Vector3f();
	public Vector3f rotation = new Vector3f();
	private Vector3f angularVelocity = new Vector3f();
	private Vector3f accumulatedTorque = new Vector3f();
	
	public float mass = 1.0f;
	public float inverseMass = 1.0f/mass; // 1/mass
	public float inertia = calcInertia(mass);
	
	protected float linearDamping = 0.965f;
	protected float angularDamping = 0.96f;
	private boolean useGravity = true;
	
	public float HBsize = 1;
	
	public void applyForce(Vector3f force) {
//		System.out.println(force);
		accumulatedForce.add(force);
	}
	
	public void applyForce(Vector3f force, Vector3f location) {

	    // normal linear force
	    applyForce(force);

	    // rotational force (torque)
	    Vector3f r = new Vector3f(location).sub(position);

	    Vector3f torque = r.cross(force, new Vector3f());

	    accumulatedTorque.add(torque.mul(-10));
	}
	
	public void setMass(float mass) {
		
		this.mass = mass;
		if (mass == 0) this.inverseMass = Float.MAX_VALUE;
		else this.inverseMass = 1.0f/mass;
		this.inertia = calcInertia(this.mass);
	}
	
	public void setInverseMass(float inverseMass) {
		this.inverseMass = inverseMass;
		if (inverseMass == 0) this.mass = Float.MAX_VALUE;
		else this.mass = 1.0f/inverseMass;
		this.inertia = calcInertia(this.mass);
	}
	
	public float calcInertia(float mass) {
		float I = 1.0f/12.0f * mass * (1*1 + 1*1);
		return I;
	}
	
	public void updatePhysics(float deltaTime) {

        // F = ma  -> a = F / m
        Vector3f acceleration = new Vector3f(accumulatedForce)
                .mul(inverseMass);

        // v = v + a * dt
        velocity.add(
                new Vector3f(acceleration)
                        .mul(deltaTime)
        );

        // p = p + v * dt
        position.add(
                new Vector3f(velocity)
                        .mul(deltaTime)
        );

        // Clear forces for next frame
        accumulatedForce.zero();

		Vector3f angularAcceleration =
		        new Vector3f(accumulatedTorque).div(inertia);

		angularVelocity.add(
		        angularAcceleration.mul(deltaTime));

		rotation.add(
		        new Vector3f(angularVelocity).mul(deltaTime));

		accumulatedTorque.zero();
		
		velocity.mul(linearDamping);
		angularVelocity.mul(angularDamping);
		
		rotation.x %= 360;
		rotation.y %= 360;
		rotation.z %= 360;
		
		if (rotation.x < 0) rotation.x = 360 - rotation.x;
		if (rotation.y < 0) rotation.y = 360 - rotation.y;
		if (rotation.z < 0) rotation.z = 360 - rotation.z;
	}
	
	@Override
	public AABB getAABB() {
		return new AABB(position.x, position.y, 5, 5);
	}

	@Override
	public void onCollision(Collider other, Vector2f push, Vector2f location) {
		float factor = 2;
//		if (other instanceof PhysicsEntity) factor *= this.mass / ((PhysicsEntity) other).mass;
		
		if (!(other instanceof PhysicsEntity)) {
			this.position.x += push.x/factor;
			this.position.y += push.y/factor;
			this.velocity.x += push.x/factor;
			this.velocity.y += push.y/factor;
		}
		
		applyForce(new Vector3f(push, 0).mul(100), new Vector3f(location, 0));
	}

	@Override
	public boolean isStatic() {
		return inverseMass == 0;
	}

	@Override
	public SAT getSAT() {
		double a = Math.toRadians(rotation.z);
		Matrix2f rot = new Matrix2f((float)Math.sin(a), (float)Math.cos(a), (float)-Math.cos(a), (float)Math.sin(a));
		
		Vector2f p0 = new Vector2f(-1, 1).mul(HBsize).mul(rot);
		Vector2f p1 = new Vector2f(-1f, -1).mul(HBsize).mul(rot);
		Vector2f p2 = new Vector2f(1f, -1).mul(HBsize).mul(rot);
		Vector2f p3 = new Vector2f(1, 1).mul(HBsize).mul(rot);
		
		float[] verts = new float[] {
				p0.x, p0.y,
				p1.x, p1.y,
				p2.x, p2.y,
				p3.x, p3.y
		};
		
		return new SAT(verts, this.position.x, this.position.y);
	}
}
