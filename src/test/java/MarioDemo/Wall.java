package MarioDemo;

import java.awt.Color;

import com.danick.e2.main.GameContainer;
import com.danick.e2.objects.GameObject;
import com.danick.e2.renderer.Graphic;

public class Wall extends GameObject {

	public Wall(int x, int y, int width, int height) {
		super(x*10, y*10, width*10, height*10);
	}

	@Override
	public void update(GameContainer gc) {
	}

	@Override
	public void render(Graphic graphic) {
		graphic.bgColor = new Color(0xff913c0e);
		graphic.background();
		//graphic.drawRectangle(0, 0, width, height, 0, );
	}

}
