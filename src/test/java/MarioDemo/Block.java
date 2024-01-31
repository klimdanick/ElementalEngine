package MarioDemo;

import java.awt.Color;

import com.danick.e2.main.GameContainer;
import com.danick.e2.objects.GameObject;
import com.danick.e2.renderer.Graphic;

public class Block extends GameObject {
	
	public Block(int x, int y) {
		super(x*10, y*10, 10, 10);
	}

	@Override
	public void update(GameContainer gc) {
		if (collide(0, 1) instanceof Player) hit();
	}

	@Override
	public void render(Graphic graphic) {
		graphic.drawRectangle(0, 0, width, height, 0, new Color(0xff7b3812));
	}

	
	public void hit() {
		gc.removeObject(this);
	}
}
