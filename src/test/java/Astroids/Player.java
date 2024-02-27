package Astroids;

import java.awt.Color;
import java.awt.event.KeyEvent;

import com.danick.e2.main.GameContainer;
import com.danick.e2.objects.GameObject;
import com.danick.e2.renderer.E2Color;
import com.danick.e2.renderer.Graphic;

public class Player extends GameObject {
	
	double dir = 0;

	public Player() {
		super(Main.gc.width/2, Main.gc.height/2, 11, 11);
	}

	@Override
	public void update(GameContainer gc) {
		x+=Math.cos(dir)*0.1;
		y+=Math.sin(dir)*0.1;
		if (gc.input.isKey(KeyEvent.VK_A)) dir-=0.01;
		if (gc.input.isKey(KeyEvent.VK_D)) dir+=0.01;
	}

	@Override
	public void render(Graphic graphic) {
		graphic.clear();
		int[][] shape = new int[][] {
			{(int) (width/4), (int) (height/2)},
			{(int) (0), (int) (height-1)},
			{(int) (width-1), (int) (height/2)},
			{(int) (0), 0}};
		graphic.drawPoly(rotateShape(shape, dir), E2Color.AQUA_GREEN, 0, 0, 0);
	}
	
	public int[][] rotateShape(int[][] shape, double angle) {
		for (int i = 0; i < shape.length; i++) {
			int[] p = shape[i];
			p[0]-=width/2;
			p[1]-=height/2;
			shape[i][0] = (int) (p[0] * Math.cos(angle) - p[1] * Math.sin(angle));
			shape[i][1] = (int) (p[0] * Math.sin(angle) + p[1] * Math.cos(angle));
			shape[i][0]+=width/2;
			shape[i][1]+=height/2;
		}
		return shape;
	}

}
