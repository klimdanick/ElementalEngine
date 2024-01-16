import java.io.FileNotFoundException;

import com.danick.e2.AssetLoader.AssetLoader;
import com.danick.e2.main.AbstractGame;
import com.danick.e2.main.GameContainer;
import com.danick.e2.renderer.Graphic;

public class imageRenderDemo extends AbstractGame {

	@Override
	public void init(GameContainer gc, Graphic r) {
		try {
			AssetLoader.Init();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(GameContainer gc, long dt) {
		
	}

	@Override
	public void render(GameContainer gc, Graphic r) {
		r.clear();
		Graphic g = AssetLoader.getGraphicAsset("1");
		r.drawGraphic(g, gc.width/2-g.pixelWidth/2, gc.height/2-g.pixelHeight/2, 0);
	}

	public static void main(String[] args) {
		GameContainer gc = new GameContainer(new imageRenderDemo(), 300, 200, 3, "image ding");
		gc.start();
	}

}
