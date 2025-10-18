package nl.klimdanick.E2.Core.Scenes;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_F4;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F5;

import java.awt.Font;
import java.util.concurrent.CopyOnWriteArrayList;

import nl.klimdanick.E2.Core.Input;
import nl.klimdanick.E2.Core.Rendering.Camera;
import nl.klimdanick.E2.Core.Rendering.E2Color;
import nl.klimdanick.E2.Core.Rendering.Renderer;

public abstract class Scene {
	
	public CopyOnWriteArrayList<GameObject> objs = new CopyOnWriteArrayList<>();
	public Camera camera = new Camera();
	
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
		r.activeCam = camera;
		preRender(r);
		for (GameObject obj : objs) {
			obj.render_(r);
		}
		postRender(r);
		
	}
	
	public void drawAxes(Renderer r) {
		float minX, minY, maxX, maxY;
		minX = camera.translation.x-r.screenWidth/2;
		maxX = camera.translation.x+r.screenWidth/2;
		
		minY = camera.translation.y-r.screenHeight/2;
		maxY = camera.translation.y+r.screenHeight/2;
		
		r.drawLine(0, minY, 0, maxY, E2Color.DEBIAN_RED);
		r.drawLine(minX, 0, maxX, 0, E2Color.AQUA_GREEN);
		
		Font font = new Font("Monospaced", Font.PLAIN, 10);
		
		for (int i = Math.floorDiv((int) minX, 50)*50; i < maxX; i+=50) {
			r.drawText(""+i, font, i, 5, E2Color.AQUA_GREEN);
		}
		
		for (int i = Math.floorDiv((int) minY, 50)*50; i < maxY; i+=50) {
			r.drawText(""+i, font, 5, i, E2Color.DEBIAN_RED);
		}
	}
	
	public void drawGrid(Renderer r) {
		float minX, minY, maxX, maxY;
		minX = camera.translation.x-r.screenWidth/2;
		maxX = camera.translation.x+r.screenWidth/2;
		minY = camera.translation.y-r.screenHeight/2;
		maxY = camera.translation.y+r.screenHeight/2;
		
		E2Color c = new E2Color(1, 1, 1, 0.5f);
		
		for (int i = Math.floorDiv((int) minX, 50)*50; i < maxX; i+=50) {
			r.drawLine(i, minY, i, maxY, c);
		}
		
		for (int i = Math.floorDiv((int) minY, 50)*50; i < maxY; i+=50) {
			r.drawLine(minX, i, maxX, i, c);
		}
	}
	
	public abstract void update(double dt);
	public abstract void preRender(Renderer r);
	public abstract void postRender(Renderer r);

}
