package MarioDemo;

import java.awt.Color;

import com.danick.e2.main.GameContainer;
import com.danick.e2.renderer.Graphic;

public class Mushroom extends Item {
	
	double dir;
	double ySpd = 0;

	public Mushroom(int x, int y) {
		super(x, y, 12, 12);
		dir = (int) (Math.round(Math.random())*2-1);
	}

	@Override
	public void update(GameContainer gc) {
		double yMove = 0;
		double xMove = dir/2;
		
		ySpd+=0.05;
		yMove+=ySpd;
		int loop = 100;
		while(collide(0, yMove) != null && loop-- > 0) {
			yMove-=Math.signum(yMove)*0.1;
			ySpd = 0;
		}
		
		loop = 100;
		double dir2 = dir;
		while((collide(xMove, 0) != null || x+xMove < 0) && loop-- > 0) {
			xMove-=Math.signum(xMove)*0.1;
			dir2 = dir * -1;
		}
		dir = dir2;
		
		x+=xMove;
		y+=yMove;
	}

	@Override
	public void render(Graphic graphic) {
		graphic.drawCircle(width/2, height/2-1, 0, width/2, new Color(0xffab1200), true, 10);
		graphic.drawCircle(width/2, height/2+2, 0, width/2-2, Color.white, true, 10);
	}
	
	@Override
	public void pickUp() {
		gc.removeObject(this);
	}
}
