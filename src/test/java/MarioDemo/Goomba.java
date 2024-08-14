package MarioDemo;

import java.awt.Color;

import com.danick.e2.main.GameContainer;
import com.danick.e2.renderer.Graphic;

public class Goomba extends Enemy {
	
	double ySpd = 0;
	double xSpd = 0.3;

	public Goomba(int x, int y) {
		super(x, y, 12, 12);
		if (Math.random() < 0.5) xSpd*=-1;
	}
	
	@Override
	public void update(GameContainer gc) {
		double yMove = 0;
		double xMove = 0;
		
		xMove+=xSpd;
		
		ySpd+=0.05;
		yMove+=ySpd;
		
		int loop = 50;
		while(collide(0, yMove) != null && !(collide(0, yMove) instanceof Item) && loop-- > 0) {
			yMove-=Math.signum(yMove)*0.1;
			ySpd = 0;
		}
		
		loop = 50;
		while((collide(xMove, 0) != null || x+xMove < 0) && loop-- > 0) {
			if (collide(0, yMove) instanceof Item) break;
			if (collide(1, 0) != null && (collide(1, 0) instanceof Player)) {gc.game.init(gc.game.r); break;}
			if (collide(-1, 0) != null && (collide(-1, 0) instanceof Player)) {gc.game.init(gc.game.r); break;}
			xMove-=Math.signum(xMove)*0.1;
			xSpd*=-1;
		}
		x+=xMove;
		y+=yMove;
		
		if (collide(0, -1) != null && collide(0, -1) instanceof Player) gc.removeObject(this);
	}

	@Override
	public void render(Graphic graphic) {
		//graphic.drawPoly(verts, new Color(0xFF994e00), 0, 0, 0);
		graphic.drawCircle(width/4+2, height/2-2, 0, width/4+1, new Color(0xFF994e00), true, 10);
		graphic.drawCircle(width/4*3-2, height/2-2, 0, width/4+1, new Color(0xFF994e00), true, 10);
		graphic.drawCircle(width/2, height/2+2, 0, 3, new Color(0xffffc78f), true, 10);
		graphic.drawRectangle(3, height-3, 6, height, 0, Color.black);
		graphic.drawRectangle(width-5, height-3, width-2, height, 0, Color.black);
		graphic.setPixel(width/2-3, height/2-3, 0, Color.white);
		graphic.setPixel(width/2-3, height/2-2, 0, Color.white);
		graphic.setPixel(width/2-2, height/2-3, 0, Color.black);
		graphic.setPixel(width/2-2, height/2-2, 0, Color.black);
		graphic.setPixel(width/2+1, height/2-3, 0, Color.white);
		graphic.setPixel(width/2+1, height/2-2, 0, Color.white);
		graphic.setPixel(width/2+2, height/2-3, 0, Color.black);
		graphic.setPixel(width/2+2, height/2-2, 0, Color.black);
	}

}
