
import com.danick.e2.main.AbstractGame;
import com.danick.e2.main.GameContainer;
import com.danick.e2.renderer.E2Color;
import com.danick.e2.renderer.Graphic;

public class demo extends AbstractGame{
	
	Graphic g;
	
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

	public void init(Graphic r) {
		g = Graphic.fromImage("bed_end.png");
	}
	
	public void update(long dt) {
	}
	int x = 0;
	public void render(Graphic r) {
		x++;
		this.gameContainer.frameRate = 30;
		r.clear();
		for (int i = 0; i < primary.length; i++) {
			primary[i] = primary[i].setAlpha(0xAA);
			int x1 = (int)(Math.random()*gameContainer.width);
			int x2 = (int)(Math.random()*gameContainer.width);
			int y1 = (int)(Math.random()*gameContainer.height);
			int y2 = (int)(Math.random()*gameContainer.height);
			//r.drawRectangle(x1, y1, x2, y2, 0, primary[i]);
		}
		g = g.setAlpha(x%254+1);
		r.drawGraphic(g, 0.5*gameContainer.width, 0.5*gameContainer.height, 0);
	}
}
