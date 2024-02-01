package MarioDemo;

import java.awt.Color;

import com.danick.e2.renderer.Graphic;

public class OneUp extends Mushroom {

	public OneUp(int x, int y) {
		super(x, y);
	}
	
	@Override
	public void render(Graphic graphic) {
		graphic.drawCircle(width/2, height/2-1, 0, width/2, new Color(0xff0d9300), true, 10);
		graphic.drawCircle(width/2, height/2+2, 0, width/2-2, Color.white, true, 10);
	}

}
