package MarioDemo;

import com.danick.e2.main.AbstractGame;
import com.danick.e2.main.GameContainer;
import com.danick.e2.objects.GameObject;
import com.danick.e2.renderer.Graphic;

public class Enemy extends GameObject {

	public Enemy(int x, int y, int width, int height) {
		super(x*10, y*10+5, width, height);
	}

	@Override
	public void update(GameContainer gc) {
		
	}

	@Override
	public void render(Graphic graphic) {
		
	}

}
