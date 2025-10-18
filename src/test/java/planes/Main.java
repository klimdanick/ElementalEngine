package planes;

import nl.klimdanick.E2.Core.GameLoop;
import nl.klimdanick.E2.Core.Input;
import nl.klimdanick.E2.Core.Rendering.DrawingMode;
import nl.klimdanick.E2.Core.Rendering.E2Color;
import nl.klimdanick.E2.Core.Scenes.*;

import static org.lwjgl.glfw.GLFW.*; 

public class Main extends GameLoop {

	Scene mainScene, titleScene;
	
	@Override
	protected void init() {
		mainScene = new MainScene();
		titleScene = new TitleScene();
		this.activeScene = titleScene;
		
		window.toggleFullscreen();
	}

	@Override
	protected void update(double dt) {
		if (activeScene instanceof TitleScene) {
			if (((TitleScene) activeScene).START) {
				((MainScene)mainScene).reset();
				activeScene = mainScene;
			}
		} else {
			if (Input.isKeyPressed(GLFW_KEY_ESCAPE)) {
				((TitleScene)titleScene).START = false;
				((TitleScene)titleScene).time = 1;
				((TitleScene)titleScene).title = " ";
				activeScene = titleScene;
			}
		}
	}

	@Override
	protected void render() {
	}

	@Override
	protected void cleanup() {
		
	}
	
	public static void main(String[] args) {
		new Main().start("Planes!", 1920/3, 1080/3, 1);
	}

}
