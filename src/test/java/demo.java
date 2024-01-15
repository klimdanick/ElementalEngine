

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.danick.e2.main.AbstractGame;
import com.danick.e2.main.GameContainer;
import com.danick.e2.math.OrthographicProjection;
import com.danick.e2.renderer.Graphic;
import com.danick.e2.renderer.Graphic3D.Model3D;
import com.danick.e2.renderer.Renderer3D;

public class demo extends AbstractGame{
	
	Color[] primary = {new Color(0xFFd0b747), new Color(0xFF299ad0), new Color(0xFF05d993), new Color(0xFFd70e48)};
	
	
	public static void main(String[] args) {
		GameContainer gc = new GameContainer(new demo(), 300, 200, 3, "test");
		gc.start();
		Color c = new Color(0x00AABBCC);
		System.out.println(c.getAlpha());
		c = new Color(0xFFAABBCC);
		System.out.println(c.getAlpha());
		c = new Color(0x9FAABBCC);
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
