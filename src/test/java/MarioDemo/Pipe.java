package MarioDemo;

import java.awt.Color;

import com.danick.e2.main.GameContainer;
import com.danick.e2.objects.GameObject;
import com.danick.e2.renderer.E2Color;
import com.danick.e2.renderer.Graphic;

public class Pipe extends GameObject {

	public Pipe(int x, int y, int height) {
		super(x*10, y*10, 20, height*10);
	}

	@Override
	public void update(GameContainer gc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(Graphic graphic) {
		graphic.drawRectangle(0, 0, width, 10, 0, new Color(0xff04a400));
		graphic.drawRectangle(2, 0, width-2, height, 0, new Color(0xff04a400));
	}

}
