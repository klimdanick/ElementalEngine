
import com.danick.e2.main.AbstractGame;
import com.danick.e2.main.GameContainer;
import com.danick.e2.renderer.E2Color;
import com.danick.e2.renderer.Graphic;

public class demo extends AbstractGame{
	
	E2Color[] primary = {
			E2Color.AQUA_GREEN,
			E2Color.CURIOS_BLUE,
			E2Color.DEBIAN_RED,
			E2Color.TURMERIC_YELLOW
	};
	
	
	public static void main(String[] args) {
		GameContainer gc = new GameContainer(new demo(), 300, 200, 3, "test");
		gc.start();
	}

	public void init(GameContainer gc, Graphic r) {
		
	}
	
	public void update(GameContainer gc, long dt) {
		r.clear();
		for (int i = 0; i < primary.length; i++) {
			primary[i] = primary[i].changeAlpha(0xAA);
			int x1 = (int)(Math.random()*gc.width);
			int x2 = (int)(Math.random()*gc.width);
			int y1 = (int)(Math.random()*gc.height);
			int y2 = (int)(Math.random()*gc.height);
			r.drawRectangle(x1, y1, x2, y2, 0, primary[i]);
		}
	}
	
	public void render(GameContainer gc, Graphic r) {
	}
}
