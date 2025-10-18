package planes;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F5;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;

import java.awt.Font;

import nl.klimdanick.E2.Core.EngineSettings;
import nl.klimdanick.E2.Core.GameLoop;
import nl.klimdanick.E2.Core.Input;
import nl.klimdanick.E2.Core.Rendering.DrawingMode;
import nl.klimdanick.E2.Core.Rendering.E2Color;
import nl.klimdanick.E2.Core.Rendering.Renderer;
import nl.klimdanick.E2.Core.Scenes.Scene;

public class TitleScene extends Scene {

	Plane p;
	String title = " ";
	double time = 0;
	
	int page = 0;
	int selected = 1;
	boolean changing = false;
	boolean START = false;
	
	int[] maxSelect = {2, 3}; 
	
	public TitleScene() {
		p = new Plane(0, 0);
		objs.add(p);
	}
	
	@Override
	public void update(double dt) {
		if (time < 3.75) time += dt;
		if (time >= 2.80) title = "Planes!";
		else if (time >= 2.70) title = "Planes";
		else if (time >= 2.60) title = "Plane";
		else if (time >= 2.50) title = "Plan";
		else if (time >= 2.40) title = "Pla";
		else if (time >= 2.30) title = "Pl";
		else if (time >= 2.20) title = "P";
		else title = " ";
		
		if (!changing) {
			if (Input.isKeyPressed(GLFW_KEY_D) && selected < maxSelect[page]) selected++;
			if (Input.isKeyPressed(GLFW_KEY_A) && selected > 0) selected--;
		}
		
		if (page == 0) {
			if (selected == 0) {
				if (Input.isKeyPressed(GLFW_KEY_SPACE)) {
					page++;
					selected = 0;
				}
			}
			if (selected == 1) {
				if (Input.isKeyPressed(GLFW_KEY_SPACE)) START = true;
			}
			if (selected == 2) {
				if (Input.isKeyPressed(GLFW_KEY_SPACE)) START = true;
			}
		}
		
		else if (page == 1) {
			if (selected == 0) {
				if (Input.isKeyPressed(GLFW_KEY_SPACE)) {
					page--;
					selected = 0;
				}
			}
			if (selected == 1) {
				if (Input.isKeyPressed(GLFW_KEY_SPACE)) {
					if (changing) changing = false;
					else changing = true;
				}
				if (changing) {
					if (Input.isKeyPressed(GLFW_KEY_D)) EngineSettings.maxFps+=10;
					if (Input.isKeyPressed(GLFW_KEY_A) && EngineSettings.maxFps > 10) EngineSettings.maxFps-=10;
				}
			}
			if (selected == 2) {
				if (Input.isKeyPressed(GLFW_KEY_SPACE)) {
					if (changing) changing = false;
					else changing = true;
				}
				if (changing) {
					if (Input.isKeyPressed(GLFW_KEY_D)) EngineSettings.maxTps+=10;
					if (Input.isKeyPressed(GLFW_KEY_A) && EngineSettings.maxTps > 10) EngineSettings.maxTps-=10;
				}
			}
		}
	}	

	@Override
	public void preRender(Renderer r) {
		r.clear(E2Color.CURIOS_BLUE);
		r.drawMode = DrawingMode.FILL;
		
	}

	@Override
	public void postRender(Renderer r) {
		r.drawMode = DrawingMode.OUTLINE;
		
		Font font = new Font("Monospaced", Font.BOLD, 30);
		Font font2 = new Font("Monospaced", Font.BOLD, 15);
		r.drawText(title, font, 0, -80, E2Color.DEBIAN_RED);
		
		if (page == 0) {
			font = new Font("Monospaced", Font.PLAIN, 20);
			if (selected == 0) font = new Font("Monospaced", Font.BOLD, 20);
			r.drawRect(-125, 100, 100, 50, E2Color.WHITE);
			r.drawText("Options", font, -125-1, 100-4, E2Color.WHITE);
			
			font = new Font("Monospaced", Font.PLAIN, 20);
			if (selected == 1) font = new Font("Monospaced", Font.BOLD, 20);
			r.drawRect(0, 100, 100, 50, E2Color.WHITE);
			r.drawText("Play", font, -1, 100-4, E2Color.WHITE);
			
			font = new Font("Monospaced", Font.PLAIN, 14);
			if (selected == 2) font = new Font("Monospaced", Font.BOLD, 14);
			r.drawRect(125, 100, 100, 50, E2Color.WHITE);
			r.drawText("Multiplayer", font, 125-1, 100-2, E2Color.WHITE);
		}
		
		if (page == 1) {
			font = new Font("Monospaced", Font.PLAIN, 20);
			if (selected == 0) font = new Font("Monospaced", Font.BOLD, 20);
			r.drawRect(-125, 100, 100, 50, E2Color.WHITE);
			r.drawText("Back", font, -125-1, 100-4, E2Color.WHITE);
			
			font = new Font("Monospaced", Font.PLAIN, 20);
			if (selected == 1) font = new Font("Monospaced", Font.BOLD, 20);
			r.drawRect(0, 100, 100, 50, E2Color.WHITE);
			r.drawText("maxFps", font2, -1, 100-40, E2Color.WHITE);
			r.drawText(EngineSettings.maxFps+"", font, -1, 100-4, E2Color.WHITE);
			
			font = new Font("Monospaced", Font.PLAIN, 20);
			if (selected == 2) font = new Font("Monospaced", Font.BOLD, 20);
			r.drawRect(125, 100, 100, 50, E2Color.WHITE);
			r.drawText("maxTps", font2, 125-1, 100-40, E2Color.WHITE);
			r.drawText(EngineSettings.maxTps+"", font, 125-1, 100-4, E2Color.WHITE);
		}
	}

}
