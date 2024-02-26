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
	double dir;
	double speed = Math.random()*0.5+0.1;

	public Astroid(int size) {
		super(0, 0, size*3, size*3, false);
		this.x = calcBeginPos()[0];
		this.y = calcBeginPos()[1];
		seed=(int) (Math.random()*1000);
		for (double a = 0; a < 20; a++) {
			radiusus[(int)a]= (int) (size + Noise.noise(a/10, seed)*size/2);
		}
		shape=mutatedCircleToPoly(radiusus);
		int dx = calcTargetPos()[0]-calcBeginPos()[0];
		int dy = calcTargetPos()[1]-calcBeginPos()[1];
		dir = Math.atan2(dy, dx);
	}
	
	
	int[] beginPos = null;
	public int[] calcBeginPos() {
		if (beginPos == null) {
			beginPos = new int[2];
			do {
				beginPos[0] = (int) ((Math.random()*(Main.gc.width+200))-100);
				beginPos[1] = (int) ((Math.random()*(Main.gc.height+200))-100);
			} while((beginPos[0] > 0 && beginPos[0] < Main.gc.width) && 
					(beginPos[1] > 0 && beginPos[1] < Main.gc.height));
		}
		return beginPos;
	}
	
	int[] targetPos = null;
	public int[] calcTargetPos() {
		if (targetPos == null) {
			targetPos = new int[2];
			targetPos[0] = (int) ((Math.random()*Main.gc.width-20)+10);
			targetPos[1] = (int) ((Math.random()*Main.gc.height-20)+10);
		}
		return targetPos;
	}

	public void update(GameContainer gc) {
		x+=Math.cos(dir)*speed;
		y+=Math.sin(dir)*speed;
		if (x < -100 || x > Main.gc.width+100 ||
			y < -100 || y > Main.gc.height+100) {
			Main.gc.removeObject(this);
		}
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
