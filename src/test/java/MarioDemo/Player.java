package MarioDemo;

import java.awt.Color;
import java.awt.event.KeyEvent;

import com.danick.e2.main.GameContainer;
import com.danick.e2.objects.GameObject;
import com.danick.e2.renderer.E2Color;
import com.danick.e2.renderer.Graphic;

public class Player extends GameObject {
	
	double ySpd = 0;

	public Player(double x, double y, int height) {
		super((int)(x*10), (int)(y*10), 10, height*10);
	}

	@Override
	public void update(GameContainer gc) {
		double yMove = 0;
		double xMove = 0;
		if (gc.input.isKey(KeyEvent.VK_D)) xMove+=0.7;
		if (gc.input.isKey(KeyEvent.VK_A)) xMove-=0.7;
		if ((gc.input.isKeyDown(KeyEvent.VK_SPACE) || gc.input.isKeyDown(KeyEvent.VK_W)) && collide(0, 1) != null) ySpd=-2.1;
		
		ySpd+=0.05;
		yMove+=ySpd;
		
		if (collide(0, yMove) instanceof Item) ((Item)collide(0, yMove)).pickUp();
		
		int loop = 100;
		while(collide(0, yMove) != null && !(collide(0, yMove) instanceof Item) && loop-- > 0) {
			yMove-=Math.signum(yMove)*0.1;
			ySpd = 0;
		}
		
		if (collide(xMove, 0) instanceof Item) ((Item)collide(xMove, 0)).pickUp();
		
		loop = 100;
		while((collide(xMove, 0) != null || x+xMove < 0) && loop-- > 0) {
			if ((collide(0, xMove) instanceof Item)) break;
			if ((collide(0, xMove) instanceof Enemy)) break;
			xMove-=Math.signum(xMove)*0.1;
		}
		x+=xMove;
		y+=yMove;
		
		for (int i = -1; i <= 1; i++)
			for (int j = -1; j <= 1; j++)
				if (collide(i, j) instanceof Item) ((Item)collide(i, j)).pickUp();
	}

	@Override
	public void render(Graphic graphic) {
		graphic.clear();
		graphic.drawRectangle(0, 0, width, height, 0, new E2Color(0xffab1200));
	}

}
