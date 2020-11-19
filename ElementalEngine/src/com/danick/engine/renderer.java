package com.danick.engine;

import java.awt.Color;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.List;

import com.danick.engine.gfx.Image;
import com.danick.engine.gfx.WaterBody;
import com.danick.engine.gfx.font;

public class renderer {
	
	protected int pW, pH;
	private int[] p;
	private List<WaterBody> Water = new ArrayList<WaterBody>();
	private boolean bgColor = false;
	private int offX, offY;
	
	private font Font = font.STANDARD;
	
	public renderer(GameContainer gc) {
		pW = gc.getWidth();
		pH = gc.getHeight();
		p = ((DataBufferInt)gc.getWindow().getImage().getRaster().getDataBuffer()).getData();		
	}
	
	public void clear() {
		for (int x = 0; x < pW; x++) {
			for (int y = 0; y < pH; y++) {
				if (bgColor) setPixel(x, y, Color.getHSBColor(0, (float)1.0, (float)0.15));
				else setPixel(x, y, Color.getHSBColor(0, (float)0.78, (float)0.31));
				if (y % 10 == 9) bgColor = !bgColor;
			}
			if (x % 10 == 9) bgColor = !bgColor;
		} 
	}
	
	public void setPixel(double X, double Y, int value) {
		int x = (int)X;
		int y = (int)Y;
		if((x + offX < 0 || x + offX >= pW || y + offY < 0 || y + offY >= pH)) {
			return;
		}
		
		p[(x + offX) + (y + offY) * pW] = value;
	}
	
	public void setPixel(double X, double Y, int value, boolean force) {
		int x = (int)X;
		int y = (int)Y;
		if(!force && (x + offX < 0 || x + offX >= pW || y + offY < 0 || y + offY >= pH)) {
			return;
		}
		
		p[(x + offX) + (y + offY) * pW] = value;
	}
	
	public void setPixel(double X, double Y, Color value) {
		int x = (int)X;
		int y = (int)Y;
		if((x + offX < 0 || x + offX >= pW || y + offY < 0 || y + offY >= pH)) {
			return;
		}
		
		p[(x + offX) + (y + offY) * pW] = value.hashCode();
	}
	
	public void setPixel(double X, double Y, Color value, boolean force) {
		int x = (int)X;
		int y = (int)Y;
		if(!force && (x + offX < 0 || x + offX >= pW || y + offY < 0 || y + offY >= pH)) {
			return;
		}
		
		p[(x + offX) + (y + offY) * pW] = value.hashCode();
	}
	
	public void drawText(String text, double offX, double offY, int color) {
		
		Image fontImage = Font.getFontImage();
		
		text= text.toUpperCase();
		int offset = 0;
		
		for(int i = 0; i < text.length(); i++) {
			int unicode = text.codePointAt(i) - 32;
			
			for (int y = 0; y < fontImage.getH(); y++) {
				for (int x = 0; x < Font.getWidths()[unicode]; x++) {
					if (Font.getFontImage().getP()[(x + Font.getOffsets()[unicode]) + y * Font.getFontImage().getW()] == 0xffffffff) {
						setPixel(x + offset + offX, y + offY, color);
					}
				}
			}
			offset += Font.getWidths()[unicode];
		} 
	}
	
public void drawText(String text, double offX, double offY, Color color) {
		
		Image fontImage = Font.getFontImage();
		
		text= text.toUpperCase();
		int offset = 0;
		
		for(int i = 0; i < text.length(); i++) {
			int unicode = text.codePointAt(i) - 32;
			
			for (int y = 0; y < fontImage.getH(); y++) {
				for (int x = 0; x < Font.getWidths()[unicode]; x++) {
					if (Font.getFontImage().getP()[(x + Font.getOffsets()[unicode]) + y * Font.getFontImage().getW()] == 0xffffffff) {
						setPixel(x + offset + offX, y + offY, color);
					}
				}
			}
			offset += Font.getWidths()[unicode];
		} 
	}
	
	public void setFont(font Font) {
		this.Font = Font;
	}
	
	public void drawImage(Image image, double offX, double offY) {
		
		if (offX + this.offX < -image.getW() || offY + this.offY < -image.getH() || offX + this.offX >= pW || offY + this.offY >= pH) return;
		
		for (int y = 0; y < image.getH(); y++) {
			for (int x = 0; x < image.getW(); x++) {
				if (!(image.getP()[x + y * image.getW()] == 0xffff00ff)) setPixel(x + offX, y + offY, image.getP()[x + y * image.getW()]);
			}
		}
	}
	
	public void drawImage(Image image, double offX, double offY, boolean flip) {
		
		if (offX + this.offX < -image.getW() || offY + this.offY < -image.getH() || offX + this.offX >= pW || offY + this.offY >= pH) return;
		
		if (flip) {
			for (int y = 0; y < image.getH(); y++) {
				for (int x = 0; x < image.getW(); x++) {
					if (!(image.getP()[x + y * image.getW()] == 0xffff00ff)) setPixel(offX + image.getW() - x, offY + y, image.getP()[x + y * image.getW()]);
				}
			}
		} else {
			for (int y = 0; y < image.getH(); y++) {
				for (int x = 0; x < image.getW(); x++) {
					if (!(image.getP()[x + y * image.getW()] == 0xffff00ff)) setPixel(x + offX, y + offY, image.getP()[x + y * image.getW()]);
				}
			}
		}
	}
	
	public void addWaterBox(int X, int Y, int width, int height, GameContainer gc) {
		Water.add(new WaterBody(X, Y, width, height, this, gc));
	}
	
	public void addWaterBox(int X, int Y, int width, int height, GameContainer gc, double wind) {
		Water.add(new WaterBody(X, Y, width, height, this, gc, wind));
	}
	
	public void drawWaterBoxes() {
		for (WaterBody b : Water) {
			b.update();
			b.drawWaterBox();
		}
	}
	
	public void drawRectangle(double x1, double y1, double x2, double y2, Color value) {
		for (double x = x1; x < x2; x++) {
			for (double y = y1; y < y2; y++) {
				setPixel(x, y, value);
			}
		}
	}
	
	public void drawRectangle(double x1, double y1, double x2, double y2, int value) {
		for (double x = x1; x < x2; x++) {
			for (double y = y1; y < y2; y++) {
				setPixel(x, y, value);
			}
		}
	}
	
	public void translate(int x, int y) {
		offX += x;
		offY += y;
	}
	
	public void drawCircle(double x, double y, double r, int value, boolean fill) {
		for (int i = 0; i < 360; i++) {
			if (fill) {
				for (int j = 0; j < r; j++) {
					double newX = (Math.sin(i) * j) + x;
					double newY = (Math.cos(i) * j) + y;
					setPixel(newX, newY, value);
				}
			} else {
				double newX = (Math.sin(i) * r) + x;
				double newY = (Math.cos(i) * r) + y;
				setPixel(newX, newY, value);
			}
		}
	}
	
	public void drawCircle(double x, double y, double r, Color value, boolean fill) {
		for (int i = 0; i < 360; i++) {
			if (fill) {
				for (int j = 0; j < r; j++) {
					double newX = (Math.sin(i) * j) + x;
					double newY = (Math.cos(i) * j) + y;
					setPixel(newX, newY, value);
				}
			} else {
				double newX = (Math.sin(i) * r) + x;
				double newY = (Math.cos(i) * r) + y;
				setPixel(newX, newY, value);
			}
		}
	}
	
	public void drawLine(double x1, double y1, double x2, double y2, Color c) {
		double a = Math.atan2(x2 - x1, y2 - y1);
		double dis = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
		double x = x1;
		double y = y1;
		for (int i = 0; i < dis; i++) {
			setPixel(x, y, c);
			x+=Math.sin(a);
			y+=Math.cos(a);
		}
	}
	
	public void drawLine(double x1, double y1, double x2, double y2, int c) {
		double a = Math.atan2(x2 - x1, y2 - y1);
		double dis = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
		double x = x1;
		double y = y1;
		for (int i = 0; i < dis; i++) {
			setPixel(x, y, c);
			x+=Math.sin(a);
			y+=Math.cos(a);
		}
	}
	
}