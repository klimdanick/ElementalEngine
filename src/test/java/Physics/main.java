package Physics;

import org.joml.Matrix3f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import nl.klimdanick.E2.Core.E2;
import nl.klimdanick.E2.Core.Game;
import nl.klimdanick.E2.Core.Entity.PhysicsEntity;
import nl.klimdanick.E2.Core.Entity.Collisions.Collider;
import nl.klimdanick.E2.Core.Entity.Collisions.CollisionSystem;
import nl.klimdanick.E2.Core.Entity.Collisions.SAT;
import nl.klimdanick.E2.Core.Input.Input;
import nl.klimdanick.E2.Utils.E2Color;

public class main extends Game {
	
	public static E2 engine;
	public static CollisionSystem cs;
	public PhysicsEntity ship, ship2;
	
	Vector3f Rudderlocation1 = new Vector3f();
	Vector3f Rudderlocation2 = new Vector3f();

	@Override
	public void init() {
		cs = new CollisionSystem();
		
		ship = new PhysicsEntity();
		ship.setMass(0.5f);
		
		ship2 = new PhysicsEntity();
		ship2.setMass(0.5f);
//		ship2.setInverseMass(0);
		
		cs.add(ship);
		cs.add(ship2);
		
		Input.bind("up", GLFW.GLFW_KEY_W);
        Input.bind("left", GLFW.GLFW_KEY_A);
        Input.bind("down", GLFW.GLFW_KEY_S);
        Input.bind("right", GLFW.GLFW_KEY_D);
	}

	@Override
	public void update(float dt) {
		
		float force = 50;
		
		Rudderlocation1 = new Vector3f(1f, 0f, 0f);
		Rudderlocation2 = new Vector3f(-1f, 0f, 0f);
		
		float theta = (float) Math.toRadians(ship.rotation.z);
		Matrix3f RotationMatrix = new Matrix3f((float)Math.cos(theta), (float)-Math.sin(theta), 0f, (float)Math.sin(theta), (float)Math.cos(theta), 0f, 0f, 0f, 1f);
				
		Rudderlocation1 = Rudderlocation1.mul(RotationMatrix).add(ship.position);
		Rudderlocation2 = Rudderlocation2.mul(RotationMatrix).add(ship.position);
		
		if (Input.get("up")) ship.applyForce(new Vector3f(0f, 0.25f, 0f).mul(RotationMatrix).mul(force));
		if (Input.get("down")) ship.applyForce(new Vector3f(0f, -0.25f, 0f).mul(RotationMatrix).mul(force));
		if (Input.get("left")) {
			ship.applyForce(new Vector3f(0f, -0.125f, 0f).mul(RotationMatrix).mul(force), Rudderlocation1);
			ship.applyForce(new Vector3f(0f, 0.125f, 0f).mul(RotationMatrix).mul(force), Rudderlocation2);
		}
		if (Input.get("right")) {
			ship.applyForce(new Vector3f(0f, 0.125f, 0f).mul(RotationMatrix).mul(force), Rudderlocation1);
			ship.applyForce(new Vector3f(0f, -0.125f, 0f).mul(RotationMatrix).mul(force), Rudderlocation2);
		}
		
		ship.updatePhysics(dt);
		ship2.updatePhysics(dt);
        
        cs.update();
	}

	@Override
	public void render() {
		drawHitbox(ship);
		drawHitbox(ship2);
//		renderer.shape.circle(Rudderlocation1.x*10 + 240, Rudderlocation1.y*10 + 125, 10, E2Color.TURMERIC_YELLOW);
//		renderer.shape.circle(Rudderlocation2.x*10 + 240, Rudderlocation2.y*10 + 125, 10, E2Color.TURMERIC_YELLOW);
	}
	
	public static void main(String[] args) {
		engine = new E2(new main(), 480, 250); // 480, 250
		engine.run();
	}
	
	private void drawHitbox(Collider c) {
		SAT hb = c.getSAT();
		
		for (int i = 0; i < hb.vertices.length-2; i+=2) {
			float x1 = hb.vertices[i] + hb.x;
			float y1 = hb.vertices[i+1] + hb.y;
			
			float x2 = hb.vertices[i+2] + hb.x;
			float y2 = hb.vertices[i+3] + hb.y;
			
			renderer.shape.line(x1*10 + 240, y1*10 + 125, x2*10 + 240, y2*10 + 125, E2Color.DEBIAN_RED);
		}
		
		float x1 = hb.vertices[hb.vertices.length-2] + hb.x;
		float y1 = hb.vertices[hb.vertices.length-1] + hb.y;
		
		float x2 = hb.vertices[0] + hb.x;
		float y2 = hb.vertices[1] + hb.y;
		
		renderer.shape.line(x1*10 + 240, y1*10 + 125, x2*10 + 240, y2*10 + 125, E2Color.DEBIAN_RED);
	}
}
