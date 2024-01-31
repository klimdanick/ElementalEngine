package MarioDemo;

import java.awt.Color;
import java.awt.event.KeyEvent;

import com.danick.e2.main.GameContainer;
import com.danick.e2.objects.GameObject;
import com.danick.e2.renderer.E2Color;
import com.danick.e2.renderer.Graphic;

public class Player extends GameObject {
	
	double ySpd = 0;

	public Player(int x, int y) {
		super(x*10, y*10, 10, 10);
	}

	@Override
	public void update(GameContainer gc) {
		double yMove = 0;
		double xMove = 0;
		if (gc.input.isKey(KeyEvent.VK_D)) xMove+=0.7;
		if (gc.input.isKey(KeyEvent.VK_A)) xMove-=0.7;
		if ((gc.input.isKey(KeyEvent.VK_SPACE) || gc.input.isKey(KeyEvent.VK_W)) && collide(0, 1) != null) ySpd=-2.1;
		
		ySpd+=0.05;
		yMove+=ySpd;
		int loop = 100;
		while(collide(0, yMove) != null && loop-- > 0) {
			yMove-=Math.signum(yMove)*0.1;
			ySpd = 0;
		}
		loop = 100;
		while((collide(xMove, 0) != null || x+xMove < 0) && loop-- > 0) xMove-=Math.signum(xMove)*0.1;
		x+=xMove;
		y+=yMove;
	}

	@Override
	public void render(Graphic graphic) {
		graphic.drawRectangle(0, 0, width, height, 0, new Color(0xffab1200));
	}

}
