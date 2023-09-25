package cam.danick.e2.IDE2;

import com.danick.e2.main.AbstractGame;
import com.danick.e2.main.GameContainer;
import com.danick.e2.main.Input;
import com.danick.e2.renderer.Window;

public class IDEGameContainer extends GameContainer{

	public IDEGameContainer(AbstractGame game, int width, int height, float scale, String title) {
		this.width = width;
		this.height = height;
		this.title = title;
		window = new PreviewWindow(this);
		input = new Input(this);
		AspectRatio = (float)height / (float)width;
		changeScene(game);
	}
	
	public void createBuffer() {
		((PreviewWindow) window).createBuffer();
	}

}
