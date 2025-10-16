package planes;

import nl.klimdanick.E2.Core.GameLoop;
import nl.klimdanick.E2.Core.Input;
import nl.klimdanick.E2.Core.Rendering.DrawingMode;
import nl.klimdanick.E2.Core.Rendering.E2Color;
import nl.klimdanick.E2.Core.Scenes.*;

import static org.lwjgl.glfw.GLFW.*; 

public class Main extends GameLoop {

	Scene mainScene, currentScene, titleScene;
	
	@Override
	protected void init() {
		mainScene = new MainScene();
		titleScene = new TitleScene();
		currentScene = titleScene;
		
		window.toggleFullscreen();
	}

	@Override
	protected void update(double dt) {
		currentScene.update_(dt);
		if (currentScene instanceof TitleScene) {
			if (((TitleScene) currentScene).START) {
				((MainScene)mainScene).reset();
				currentScene = mainScene;
			}
		} else {
			if (Input.isKeyPressed(GLFW_KEY_ESCAPE)) {
				((TitleScene)titleScene).START = false;
				((TitleScene)titleScene).time = 1;
				((TitleScene)titleScene).title = " ";
				currentScene = titleScene;
			}
		}
	}

	@Override
	protected void render() {
		renderer.clear(E2Color.CURIOS_BLUE);
		currentScene.render_(renderer);
	}

	@Override
	protected void cleanup() {
		
	}
	
	public static void main(String[] args) {
		new Main().start("Planes!", 1920/3, 1080/3, 1);
	}

}
