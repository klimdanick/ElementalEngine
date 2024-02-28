package Astroids;

import java.awt.Color;
import java.nio.ByteBuffer;

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
		if (beginPos == null) if (Main.gc != null) {
			beginPos = new int[2];
			do {
				beginPos[0] = (int) ((Math.random()*(Main.gc.width+200))-100);
				beginPos[1] = (int) ((Math.random()*(Main.gc.height+200))-100);
			} while((beginPos[0] > 0 && beginPos[0] < Main.gc.width) && 
					(beginPos[1] > 0 && beginPos[1] < Main.gc.height));
		} else beginPos = new int[]{10, 10};
		return beginPos;
	}
	
	int[] targetPos = null;
	public int[] calcTargetPos() {
		if (targetPos == null) if (Main.gc != null) {
			targetPos = new int[2];
			targetPos[0] = (int) ((Math.random()*Main.gc.width-20)+10);
			targetPos[1] = (int) ((Math.random()*Main.gc.height-20)+10);
		} else targetPos = new int[]{0, 0};
		return targetPos;
	}

	public void update(GameContainer gc) {
		x+=Math.cos(dir)*speed;
		y+=Math.sin(dir)*speed;
		if (Main.gc == null) return;
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
	
	public byte[] serialize() {
		byte[] bytes = new byte[4*radiusus.length+2*8+2*4];
		for (int i = 0; i < radiusus.length; i++) {
			byte[] intBytes = ByteBuffer.allocate(4).putInt(radiusus[i]).array();
			for (int b = 0; b < 4; b++) bytes[i*4+b] = intBytes[b];
		}
		
		byte[] dirBytes = ByteBuffer.allocate(8).putDouble(dir).array();
		for (int b = 0; b < 8; b++) bytes[radiusus.length*4+b] = dirBytes[b];
		byte[] speedBytes = ByteBuffer.allocate(8).putDouble(speed).array();
		for (int b = 0; b < 8; b++) bytes[radiusus.length*4+8+b] = speedBytes[b];
		
		byte[] xBytes = ByteBuffer.allocate(4).putInt((int)x).array();
		for (int b = 0; b < 4; b++) bytes[radiusus.length*4+8+8+b] = xBytes[b];
		byte[] yBytes = ByteBuffer.allocate(4).putInt((int)y).array();
		for (int b = 0; b < 4; b++) bytes[radiusus.length*4+8+8+4+b] = yBytes[b];
		
		return bytes;
	}
}
