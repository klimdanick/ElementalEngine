package nl.klimdanick.E2.Core.Scenes;

import java.util.concurrent.CopyOnWriteArrayList;

import nl.klimdanick.E2.Core.Rendering.Renderer;

public abstract class Scene {
	
	public CopyOnWriteArrayList<GameObject> objs = new CopyOnWriteArrayList<>();
	
	public void addObject(GameObject obj) {
		objs.add(obj);
		obj.scene = this;
	}
	
	public void update_(double dt) {
		for (GameObject obj : objs) {
			obj.update_(dt);
		}
		update(dt);
	}
	
	public void render_(Renderer r) {
		preRender(r);
		for (GameObject obj : objs) {
			obj.render_(r);
			obj.hitbox.render(r);
		}
		postRender(r);
		
	}
	
	public abstract void update(double dt);
	public abstract void preRender(Renderer r);
	public abstract void postRender(Renderer r);
}
