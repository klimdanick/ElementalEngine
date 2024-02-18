package Astroids;

import java.awt.Color;

import com.danick.e2.main.GameContainer;
import com.danick.e2.objects.GameObject;
import com.danick.e2.renderer.E2Color;
import com.danick.e2.renderer.Graphic;
import com.danick.e2.math.Noise;

public class Astroid extends GameObject {
	
	int[] radiusus = new int[20];
	int[][] shape;
	int seed;
	double dir = Math.random()*Math.PI*2;
	double speed = Math.random()*0.5+0.1;

	public Astroid(int x, int y, int size) {
		super(x, y, size*3, size*3, false);
		seed=(int) (Math.random()*1000);
		for (double a = 0; a < 20; a++) {
			radiusus[(int)a]= (int) (size + Noise.noise(a/10, seed)*size/2);
		}
		shape=mutatedCircleToPoly(radiusus);
	}

	public void update(GameContainer gc) {
		x+=Math.sin(dir)*speed;
		y+=Math.cos(dir)*speed;
	}

	public void render(Graphic g) {
		g.drawPoly(shape, Color.white, (int)width/2, (int)height/2, 0);
		g.bgColor = E2Color.TURMERIC_YELLOW;
	}
	
	public int[][] mutatedCircleToPoly(int[] radiusus) {
		int[][] points = new int[radiusus.length+1][2];
		for (int a = 0; a < radiusus.length; a++) {
			points[a][0] = (int) (Math.sin(Math.toRadians(a*18))*radiusus[a]);
			points[a][1] = (int) (Math.cos(Math.toRadians(a*18))*radiusus[a]);
		}
		points[points.length-1]=points[0];
		return points;
	}
}
