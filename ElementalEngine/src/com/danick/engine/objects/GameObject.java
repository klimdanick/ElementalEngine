package com.danick.engine.objects;

import java.awt.event.KeyEvent;

import com.danick.engine.GameContainer;
import com.danick.engine.gfx.Animation;
import com.danick.engine.gfx.Image;

public class GameObject {
	double x, y, width, height;
	HitBox HB;
	boolean collidable = false;
	static Animation placeholder = new Animation("placeholder.png");
	Animation sprite = placeholder;
	private Image placeholderImage = new Image("placeholder.png");
	double g = 0;
	
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
		placeholderImage.setW(width);
		placeholderImage.setH(height);
		placeholderImage.setP(p);
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
		placeholderImage.setW(width);
		placeholderImage.setH(height);
		placeholderImage.setP(p);
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
		placeholderImage.setW(width);
		placeholderImage.setH(height);
		placeholderImage.setP(p);
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
		HB.setX(x);
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
		HB.setY(y);
	}
	
	public void setPos(double[] pos) {
		this.x = pos[0];
		this.y = pos[1];
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public HitBox getHitBox() {
		return HB;
	}

	public void setHitBox(HitBox hB) {
		HB = hB;
	}
	
	public void move(double x, double y) {
		this.x += x;
		this.y += y;
		HB.setX(this.x);
		HB.setY(this.y);
	}

	public boolean isCollidable() {
		return collidable;
	}

	public void setCollidable(boolean collidable) {
		this.collidable = collidable;
	}

	public Image getSprite() {
		if (sprite != placeholder) {
			System.out.println("sprite");
			return sprite.getImage();
		}
		else {
			System.out.println("placeholder");
			return placeholderImage;
		}
	}

	public void setSprite(Animation sprite) {
		this.sprite = sprite;
	}
	
	public void updateGravity(GameContainer gc) {
		if (!gc.collide(this, 0, (int)g)) {
			this.move(0, (int)g);
			if (g < 5) {
				if (g < 0) g+=0.1;
				if (g > 0) g+=0.4;
			}
			if (g >= -0.5 && g <= 0) g = 1;
		} else {
			g = 1;
		}
	}

	public double getG() {
		return g;
	}

	public void setG(double g) {
		this.g = g;
	}
	
	public double[] getPos() {
		double[] pos = new double[2];
		pos[0] = x;
		pos[1] = y;
		return pos;
	}
}
