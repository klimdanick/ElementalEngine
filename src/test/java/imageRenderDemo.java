import java.io.FileNotFoundException;

import com.danick.e2.AssetLoader.AssetLoader;
import com.danick.e2.main.AbstractGame;
import com.danick.e2.main.GameContainer;
import com.danick.e2.renderer.Graphic;

public class imageRenderDemo extends AbstractGame {

	@Override
	public void init(Graphic r) {
		AssetLoader.Init();
	}

	@Override
	public void update(long dt) {
		
	}

	@Override
	public void render(Graphic r) {
		r.clear();
		Graphic g = AssetLoader.getGraphicAsset("1");
		r.drawGraphic(g, gameContainer.width/2-g.pixelWidth/2, gameContainer.height/2-g.pixelHeight/2, 0);
	}

	public static void main(String[] args) {
		GameContainer gc = new GameContainer(new imageRenderDemo(), 300, 200, 3, "image ding");
		gc.start();
	}

}
