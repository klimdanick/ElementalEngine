
import com.danick.e2.main.AbstractGame;
import com.danick.e2.main.GameContainer;
import com.danick.e2.renderer.E2Color;
import com.danick.e2.renderer.Graphic;

public class demo extends AbstractGame{
	
	E2Color[] primary = {new E2Color(0xFFd0b747), new E2Color(0xFF299ad0), new E2Color(0xFF05d993), new E2Color(0xFFd70e48)};
	
	
	public static void main(String[] args) {
		GameContainer gc = new GameContainer(new demo(), 300, 200, 3, "test");
		gc.start();
		E2Color c = new E2Color(0x00AABBCC);
		System.out.println(c.getAlpha());
		c = new E2Color(0xFFAABBCC);
		System.out.println(c.getAlpha());
		c = new E2Color(0x9FAABBCC);
		System.out.println(c.getAlpha());
	}

	public void init(GameContainer gc, Graphic r) {
		r.clear();
		for (int i = 0; i < primary.length; i++) {
			int x1 = (int)(Math.random()*gc.width);
			int x2 = (int)(Math.random()*gc.width);
			int y1 = (int)(Math.random()*gc.height);
			int y2 = (int)(Math.random()*gc.height);
			r.drawLine(x1, x2, y1, y2, 0, primary[i]);
		}
	}
	
	public void update(GameContainer gc, long dt) {
		
	}
	
	public void render(GameContainer gc, Graphic r) {
	}
}
