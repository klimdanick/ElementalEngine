package com.danick.engine.gfx;

import java.awt.Color;
import java.awt.event.MouseEvent;

import com.danick.engine.*;
import com.danick.engine.gfx.*;
import com.danick.engine.objects.*;

public class WaterBody {
	
	private Color color;
	private Color color2;
	private int X, Y, width, height;
	private double offset = Math.random();
	private double offsetSpeed = Math.random() * 2 - 1;
	
	
	private int columns = 10;
	private double columnWidth;
	private double[] columnHeight = new double[columns];
	private double[] Speed = new double[columns];
	private double[] targetHeight = new double[columns];
	private final double Dampening = 0.05;
	private final double Tension = 0.025;
	private final double Spread = 0.1;
	private double[] leftDelta = new double[columns];
	private double[] rightDelta = new double[columns];
	private int passes = 1;
	
	private renderer r;
	private GameContainer gc;
	
	
	
	public WaterBody(int X, int Y, int width, int height, renderer r, GameContainer gc, double wind) {
		color = new Color(0, 0.48f, 0.65f, 0.5f);
		color2 = new Color(0, 0.33f, 0.5f, 0.8f);
		this.X = X;
		this.Y = Y;
		this.width = width;
		this.height = height;
		this.r = r;
		this.gc = gc;
		this.columnWidth = width / columns;
		this.offsetSpeed = wind;
		
		for (int i = 0; i < columns; i++) {
			columnHeight[i] = height;
			targetHeight[i] = height;
			Speed[i] = 0;
			
			leftDelta[i] = 0;
			rightDelta[i] = 0;
		}
	}
	
	public WaterBody(int X, int Y, int width, int height, renderer r, GameContainer gc) {
		color = new Color(0, 0.48f, 0.65f, 0.5f);
		color2 = new Color(0, 0.33f, 0.5f, 0.8f);
		this.X = X;
		this.Y = Y;
		this.width = width;
		this.height = height;
		this.r = r;
		this.gc = gc;
		this.columnWidth = width / columns;
		
		for (int i = 0; i < columns; i++) {
			columnHeight[i] = height;
			targetHeight[i] = height;
			Speed[i] = 0;
			
			leftDelta[i] = 0;
			rightDelta[i] = 0;
		}
	}
	
	public void drawWaterBox() {
		for (int x = X; x < X + width; x++) {
			float f = (float)Math.sin(((float)x / 3 + offset) / 7) * 4;
			float g = (float)Math.cos(((float)-x/4) / 3)*2;
			for (int y = (int)(f + g) + Y; y < Y + height; y++) {
				r.setPixel(x, y, color);
				if (x == X || x == X + width - 1 || y == Y + height - 1 || y == (int)(f + g) + Y) r.setPixel(x, y, color2);
			}
		}
		
			
		/*for (int i = 0; i < columns; i++) {
				double x1 = X + (i * columnWidth);
				double y1 = Y + targetHeight[i] - columnHeight [i];
				double x2 = x1 + columnWidth;
				double y2 = Y + targetHeight[i];
				double right_y1 = y1;
				
				if (i < columns - 1) right_y1 = Y + targetHeight[i+1] - columnHeight[i+1];
				
				//r.drawRectangle((int)x1, (int)y1, (int)x2, (int)y2, Color.getHSBColor(color[0], color[1], color[2]));
				for (int x = (int)x1; x < x2; x++) {
					for (int y = (int)y1; y < y2; y++) {
						r.setPixel(x, y, Color.CYAN);
					}
				}
			}*/
		}
	
	public void update() {
		offset+=offsetSpeed;
		/*for (int i = 0; i < columns; i++) {
			double Displacement = (targetHeight[i] - columnHeight[i]);
			//if ((Speed[i] <= 0.02 || Speed[i] >= -0.02) && (columnHeight[i] <= targetHeight[i]+0.02 || columnHeight[i] >= targetHeight[i]-0.02)) columnHeight[i] = targetHeight[i];
			Speed[i] += (Tension * Displacement) - (Dampening * Speed[i]);
			//System.out.println(Speed[i]);
			columnHeight[i] += Speed[i];
			
			double x1 = X + (i * columnWidth);
			double y1 = Y + targetHeight[i] - columnHeight [i];
			double x2 = x1 + columnWidth;
			double y2 = Y + targetHeight[i];
			double right_y1 = y1;
			
			if (i < columns - 1) right_y1 = Y + targetHeight[i+1] - columnHeight[i+1];
			
			if (gc.getInput().isButton(MouseEvent.BUTTON1)) {
				if (gc.getInput().isMouseInBox(x1, y1, x2, y2 + 100)) {
					Speed[i]-=10;
					System.out.println(true);
					System.out.println(i);
				} //else System.out.println(false);
			}
		}
		
		
		for (int j = 0; j < passes; j++) {
			for (int i = 0; i < columns; i++) {
				if (i > 0) {
					leftDelta[i] = Spread * (columnHeight[i] - columnHeight[i - 1]);
					Speed[i - 1] += leftDelta[i];
				}
				if (i < columns - 1) {
					rightDelta[i] = Spread * (columnHeight[i] - columnHeight[i + 1]);
					Speed[i + 1] = rightDelta[i];
				}
			}
			
			for (int i = 0; i < columns; i++) {
				if (i > 0) {
					columnHeight[i - 1] += leftDelta[i];
				}
				if (i < columns - 1) {
					columnHeight[i + 1] += rightDelta[i];
				}
			}
		}*/
		
	}
}
