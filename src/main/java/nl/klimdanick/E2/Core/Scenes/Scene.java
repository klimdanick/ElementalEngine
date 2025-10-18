package nl.klimdanick.E2.Core.Scenes;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_F4;

import java.util.concurrent.CopyOnWriteArrayList;

import nl.klimdanick.E2.Core.Input;
import nl.klimdanick.E2.Core.Rendering.Camera;
import nl.klimdanick.E2.Core.Rendering.Renderer;

public abstract class Scene {
	
	public CopyOnWriteArrayList<GameObject> objs = new CopyOnWriteArrayList<>();
	public boolean drawHitboxes = false;
	public Camera camera = new Camera();
	
	public void addObject(GameObject obj) {
		objs.add(obj);
		obj.scene = this;
	}
	
	public void update_(double dt) {
		if (Input.isKeyPressed(GLFW_KEY_F4)) drawHitboxes = !drawHitboxes;
		
		for (GameObject obj : objs) {
			obj.update_(dt);
		}
		update(dt);
	}
	
	public void render_(Renderer r) {
		r.activeCam = camera;
		preRender(r);
		for (GameObject obj : objs) {
			obj.render_(r);
			if (drawHitboxes) obj.hitbox.render(r);
		}
		postRender(r);
		
	}
	
	public abstract void update(double dt);
	public abstract void preRender(Renderer r);
	public abstract void postRender(Renderer r);
}
