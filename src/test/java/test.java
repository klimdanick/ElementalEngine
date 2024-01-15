

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import com.danick.e2.AssetLoader.AssetLoader;
import com.danick.e2.main.AbstractGame;
import com.danick.e2.main.GameContainer;
import com.danick.e2.math.OrthographicProjection;
import com.danick.e2.renderer.Graphic;
import com.danick.e2.renderer.Graphic3D.Model3D;
import com.danick.e2.renderer.Renderer3D;

public class test extends AbstractGame{
	GameContainer gc;
	double x, y;
	public static void main(String[] args) throws Exception {
		AssetLoader.Init();
		GameContainer gc = new GameContainer(new test(), 600, 400, 1, "test");
		gc.start();
	}

	public void init(GameContainer gc, Graphic r) {
		r.bgColor = Color.red.darker();
		this.gc = gc;
	}
	
	public void update(GameContainer gc, long dt) {
		if (gc.input.isKey(KeyEvent.VK_W)) y+=0.1;
		if (gc.input.isKey(KeyEvent.VK_S)) y-=0.1;
		if (gc.input.isKey(KeyEvent.VK_A)) x+=0.1;
		if (gc.input.isKey(KeyEvent.VK_D)) x-=0.1;
	}
	
	public void render(GameContainer gc, Graphic r) {
		r.clear();
		double z = Math.sin((double)x/2) + Math.sin((double)y/2)+1;
		int[] uv = project(x, y, z);

		Graphic[] tiles = new Graphic[] {AssetLoader.getGraphicAsset("1")};
		Graphic[] tiles2 = new Graphic[] {AssetLoader.getGraphicAsset("2")};

		for (int i = -50; i < 40; i++) for (int j = 40; j >= -50; j--) {
			z = Math.sin((double)i/2) + Math.sin((double)j/2);
			uv = project(i, j, z);
			r.drawGraphic(tiles[0], uv[0]-tiles[0].pixelWidth/2, uv[1]-tiles[0].pixelHeight/2, j-i-z);
		}
		r.drawCircle(uv[0], uv[1], y-x-z, 20, Color.red, true, 1);
		r.drawGraphic(r.getZBuffer(), 0, 0, Integer.MIN_VALUE);
	}

	public int[] project(double x, double y, double z) {
		int u, v;
		u = (int) Math.round(8 * x + 8 * y);
		v = (int) Math.round(4 * x - 4 * y - 8 * z);
		u += gc.width / 2;
		v += gc.height-100;
		return new int[] { u, v };
	}
}
