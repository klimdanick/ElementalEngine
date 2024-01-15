
import com.danick.e2.main.AbstractGame;
import com.danick.e2.main.GameContainer;
import com.danick.e2.renderer.E2Color;
import com.danick.e2.renderer.Graphic;

public class demo extends AbstractGame{
	
	E2Color[] primary = {new E2Color(0xAAd0b747), new E2Color(0xAA299ad0), new E2Color(0xAA05d993), new E2Color(0xAAd70e48)};
	
	
	public static void main(String[] args) {
		GameContainer gc = new GameContainer(new demo(), 300, 200, 3, "test");
		gc.start();
	}

	public void init(GameContainer gc, Graphic r) {
		r.clear();
		for (int i = 0; i < primary.length; i++) {
			int x1 = (int)(Math.random()*gc.width);
			int x2 = (int)(Math.random()*gc.width);
			int y1 = (int)(Math.random()*gc.height);
			int y2 = (int)(Math.random()*gc.height);
			r.drawRectangle(x1, y1, x2, y2, 0, primary[i]);
		}
	}
	
	public void update(GameContainer gc, long dt) {
		
	}
	
	public void render(GameContainer gc, Graphic r) {
	}
}
