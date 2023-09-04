package com.danick.e2.objects;

import java.awt.Color;

import com.danick.e2.main.GameContainer;
import com.danick.e2.renderer.Animation;
import com.danick.e2.renderer.Graphic;

public abstract class GameObject {
	public GameContainer gc;
	public double x, y, width, height;
	public HitBox HB;
	public boolean collidable = true;
	public Graphic sprite;
	public double oX = 0, oY = 0;
	
	public GameObject(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		HB = new HitBox(x, y, width, height);
		int[] p = new int[width + height * width];
		for (int i = 0; i < p.length; i++) {
			p[i] = 0xfffe00ff;
		}
		sprite = new Graphic(width, height);
	}
	
	public GameObject(int x, int y, int width, int height, boolean collidable) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		HB = new HitBox(x, y, width, height);
		this.collidable = collidable;
		int[] p = new int[width + height * width];
		for (int i = 0; i < p.length; i++) {
			p[i] = 0xfffe00ff;
		}
		sprite = new Graphic(width, height);
	}
	
	public GameObject(double[] pos, int width, int height) {
		this.x = pos[0];
		this.y = pos[1];
		this.width = width;
		this.height = height;
		HB = new HitBox(x, y, width, height);
		int[] p = new int[width + height * width];
		for (int i = 0; i < p.length; i++) {
			p[i] = 0xfffe00ff;
		}
		sprite = new Graphic(width, height);
	}

	public void move(double x, double y) {
		this.x += x;
		this.y += y;
		HB.x = this.x;
		HB.y = this.y;
	}
	
	public abstract void update(GameContainer gc); 
	
	public abstract void render(Graphic graphic);
	
	public GameObject collide() {
		for (GameObject obj : gc.getObjects()) {
			HitBox HB2 = obj.HB;
			if (obj.x + obj.width > x && obj.x < x + HB.width && obj.y + obj.height > y && obj.y < y + height && !obj.equals(this))
				return obj;
		}
		return null;
	}
	
	public GameObject collide(double offX, double offY) {
		GameObject o = null;
		for (GameObject obj : gc.getObjects()) {
			if (obj.collidable)
				if (obj.x + obj.width > x+offX && obj.x < x + width+offX && obj.y + obj.height > y+offY && obj.y < y + height + offY && !obj.equals(this))
					o = obj;
		}
		return o;
	}
}
