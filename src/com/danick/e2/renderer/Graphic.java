package com.danick.e2.renderer;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import javax.imageio.ImageIO;

import com.danick.e2.main.GameContainer;
import com.danick.e2.objects.GameObject;

public class Graphic extends Thread{
	/*
	public int pW, pH;
	int[] p;
	public int offX, offY;
	GameContainer gc;
	private font Font = font.STANDARD;
	*/
	
	volatile public int pW, pH;
	volatile public int[] p;
	public int offX = 0, offY = 0;
	public boolean isDrawing = false;
	public int locationX = 0, locationY = 0;
	public GameContainer gc;
	private Font Font = com.danick.e2.renderer.Font.STANDARD;
	public int strokeThickness = 1;
	
	public Color bgColor = new Color(0xFF004A7F);
	
	public static int transparent = 0xffff00ff;
	public int chackerPattrnWidth = 10;
	public int chackerPattrnHeight = 10;
	
	
	
	public Graphic(int width, int height) {
		pW = width;
		pH = height;
		p = new int[width*height];
		for (int i = 0; i < width*height; i++) p[i] = 0xffff00ff;
	}
	
	public static Graphic fromImage(String path) {
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(Graphic.class.getResourceAsStream("/" + path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Graphic i = new Graphic(image.getWidth(), image.getHeight());

	      for (int row = 0; row < i.pH; row++) {
	         for (int col = 0; col < i.pW; col++) {
	            if ((image.getRGB(col, row) >>24) == 0x00) i.p[col + row * i.pW] = 0xffff00ff;
	            else i.p[col + row * i.pW] = image.getRGB(col, row);
	         }
	      }
		
		image.flush();
		return i;
	}

	public void run() {}

	
	public void clear() {
		for (int x = offX; x < pW+offX; x++) for (int y = offY; y < pH+offY; y++) {
			setPixel(x, y, this.transparent);
		} 
	}
	
	public void background() {
		for (int x = offX; x < pW+offX; x++) for (int y = offY; y < pH+offY; y++) {
			if ((int)Math.abs(Math.round(y/(float)(chackerPattrnHeight)))%2 == (int)Math.abs(Math.round(x/(float)(chackerPattrnWidth)))%2) setPixel(x, y, bgColor);
			else setPixel(x, y, bgColor.darker());
		} 
	}
	
	public void setPixel(double X, double Y, int value) {
		int x = (int) Math.round(X);
		int y = (int) Math.round(Y);
		if((x - offX < 0 || x - offX >= pW || y - offY < 0 || y - offY >= pH)) {
			return;
		}
		if ((x - offX) + (y - offY) * pW >= p.length) return;
		if ((x - offX) + (y - offY) * pW < 0) return;
		p[(x - offX) + (y - offY) * pW] = value;
	}
	
	public void setPixel(double X, double Y, int value, boolean force) {
		int x = (int) Math.round(X);
		int y = (int) Math.round(Y);
		if(!force && (x - offX < 0 || x - offX >= pW || y - offY < 0 || y - offY >= pH)) {
			return;
		}
		if ((x - offX) + (y - offY) * pW >= p.length) return;
		if ((x - offX) + (y - offY) * pW < 0) return;
		p[(x - offX) + (y - offY) * pW] = value;
	}
	
	public void setPixel(double X, double Y, Color value) {
		int x = (int) Math.round(X);
		int y = (int) Math.round(Y);
		if((x - offX < 0 || x - offX >= pW || y - offY < 0 || y - offY >= pH)) {
			return;
		}
		if ((x - offX) + (y - offY) * pW >= p.length) return;
		if ((x - offX) + (y - offY) * pW < 0) return;
		p[(x - offX) + (y - offY) * pW] = value.hashCode();
	}
	
	public void setPixel(double X, double Y, Color value, boolean force) {
		int x = (int) Math.round(X);
		int y = (int) Math.round(Y);
		if(!force && (x - offX < 0 || x - offX >= pW || y - offY < 0 || y - offY >= pH)) {
			return;
		}
		
		p[(x - offX) + (y - offY) * pW] = value.hashCode();
	}
	
	public Color getPixel(double X, double Y) {
		return getPixel(X, Y, false);
	}
	
	public Color getPixel(double X, double Y, boolean noError) {
		int x = (int) Math.round(X);
		int y = (int) Math.round(Y);
		if((x + offX < 0 || x + offX >= pW || y + offY < 0 || y + offY >= pH)) {
			if (!noError) {
				System.err.print("Unvallid pixel coördinate! returning black pixel! | Invalid location: ");
				System.err.print(x +", " + y);
				System.err.println(" for canvas sie: " + pW + ", " + pH);
			}
			return new Color(0);
		}
		return new Color(p[(x + offX) + (y + offY) * pW]);
	}
	
	public void drawText(String text, double offX, double offY, int color) {
		
		Graphic fontImage = Font.getFontImage();
		
		text= text.toUpperCase();
		int offset = 0;
		
		for(int i = 0; i < text.length(); i++) {
			int unicode = text.codePointAt(i) - 32;
			offset += Font.getWidths()[unicode];
		}
		offX-=offset/2;
		offY-=Font.getHeight()/2;
		offset = 0;
		for(int i = 0; i < text.length(); i++) {
			int unicode = text.codePointAt(i) - 32;
			
			for (int y = 0; y < fontImage.pH; y++) {
				for (int x = 0; x < Font.getWidths()[unicode]; x++) {
					if (Font.getFontImage().p[(x + Font.getOffsets()[unicode]) + y * Font.getFontImage().pW] == 0xffffffff) {
						setPixel(x + offset + offX, y + offY, color);
					}
				}
			}
			offset += Font.getWidths()[unicode];
		} 
	}
	
public void drawText(String text, double offX, double offY, Color color) {
		
		Graphic fontImage = Font.getFontImage();
		
		text= text.toUpperCase();
		int offset = 0;
		
		for(int i = 0; i < text.length(); i++) {
			int unicode = text.codePointAt(i) - 32;
			offset += Font.getWidths()[unicode];
		}
		offX-=offset/2;
		offY-=Font.getFontImage().pH/2;
		offset = 0;
		
		for(int i = 0; i < text.length(); i++) {
			int unicode = text.codePointAt(i) - 32;
			
			for (int y = 0; y < fontImage.pH; y++) {
				for (int x = 0; x < Font.getWidths()[unicode]; x++) {
					if (Font.getFontImage().p[(x + Font.getOffsets()[unicode]) + y * Font.getFontImage().pW] == 0xffffffff) {
						setPixel(x + offset + offX, y + offY, color);
					}
				}
			}
			offset += Font.getWidths()[unicode];
		} 
	}
	
	public void setFont(Font Font) {
		this.Font = Font;
	}
	
	public void drawGraphic(Graphic r) {
		if (r == null) {
			System.err.append("The graphic is null!\n");
			return;
		}
		double offX = r.locationX;
		double offY = r.locationY;

		if (offX + this.offX < -r.pW || offY + this.offY < -r.pH || offX + this.offX >= pW || offY + this.offY >= pH) return;
		
		for (int y = 0; y < r.pH; y++) {
			for (int x = 0; x < r.pW; x++) {
				if (!(r.p[x + y * r.pW] == 0xffff00ff)) setPixel((x) + offX, (y) + offY, r.p[x + y * r.pW]);
			}
		}
	}
	
	public void drawGraphic(Graphic r, double offX, double offY) {

		if (offX + this.offX < -r.pW || offY + this.offY < -r.pH || offX + this.offX >= pW || offY + this.offY >= pH) return;
		
		for (int y = 0; y < r.pH; y++) {
			for (int x = 0; x < r.pW; x++) {
				if (!(r.p[x + y * r.pW] == 0xffff00ff)) setPixel((x) + offX, (y) + offY, r.p[x + y * r.pW]);
			}
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
	
	public void drawCircle(double x, double y, double r, Color value, boolean fill, double thinkness) {
		for (int X = (int) (x - r); X < x+r; X++) {
			for (int Y = (int) (y - r); Y < y+r; Y++) {
				double dist = (X-x)*(X-x)+(Y-y)*(Y-y);
				if (dist < r*r && (dist > (r-thinkness)*(r-thinkness) || fill)) setPixel(X, Y, value);
			}
		}
	}
	
	public void drawLine(double x0, double y0, double x1, double y1, Color c) {
		if (Math.abs(y1- y0) < Math.abs(x1-x0)) {
    		if (x0 > x1)
    			drawLineLow(x1,y1, x0, y0, c);
    		else
    			drawLineLow(x0,y0, x1, y1, c);
    	} else {
    		if (y0 > y1)
    			drawLineHigh(x1,y1, x0, y0, c);
    		else
    			drawLineHigh(x0,y0, x1, y1, c);
    	}
	}
	
	private void drawLineLow(double x0, double y0, double x1, double y1, Color c) {
		int dX = (int)(x1-x0);
    	int dY = (int)(y1-y0);
    	int Yi = dY < 0 ? -1 : 1;
    	dY = Math.abs(dY); 
    	
    	int D = (2*dY)-dX;
    	int y = (int)y0;
    	
    	int ddXY = 2*(dY-dX);
    	int ddY = 2*dY;
    	
    	for (int x = (int)x0; x <= x1; x++) {
    		setPixel(x, y, c);
    		if (D > 0) {
    			y+=Yi;
    			D+=ddXY;
    		} else {
    			D+=ddY;
    		}
    	}
	}
	
	private void drawLineHigh(double x0, double y0, double x1, double y1, Color c) {
		//System.out.println(x0 + " " + x1 + " " + (x1-x0));
		int dX = (int) (x1-x0);
		int dY = (int) (y1-y0);
		int Xi = dX < 0 ? -1 : 1;
		dX = Math.abs(dX) ;
		
		int D = (2*dX)-dY;
		int x = (int) x0;
		
		int ddXY = 2*(dX-dY);
		int ddX = 2*dX;
		
		for (int y = (int) y0; y <= y1; y++) {
			setPixel(x, y, c);
			if (D > 0) {
				x+=Xi;
				D+=ddXY;
			} else {
				D+=ddX;
			}
		}
	}
	
	public void drawPoly(int[][] points2, Color c, int X, int Y) {
		int[][] points = new int[points2.length+1][2];
		  for (int i = 0; i < points2.length; i++) {
		    points[i] = points2[i];
		  }
		  points[points2.length] = points[0];
		  
		  for (int i = 0; i < 4; i++) {
			  this.drawLine(points[i][0]+X, points[i][1]+Y, points[i+1][0]+X, points[i+1][1]+Y, c);
		  }
	}
	
	public void fillPoly(int[][] points2, Color c, int X, int Y) {
		int minX=0, minY=0, maxX=0, maxY=0;
		int[][] points = new int[points2.length+1][2];
		  for (int i = 0; i < points2.length; i++) {
		    points[i] = points2[i];
		    minX = Math.min(points[i][0], minX);
		    maxX = Math.max(points[i][0], maxX);
		    minY = Math.min(points[i][1], minY);
		    maxY = Math.max(points[i][1], maxY);
		  }
		  points[points2.length] = points[0];
		  
		  int w = maxX - minX + 1;
		  int h = maxY - minY + 1;
		  
		  boolean[][] togglePoints = new boolean[w][h];
		  for (boolean b1[] : togglePoints) for (boolean b : b1) {
			  b = false;
		  }
		  
		  for (int i = 0; i < points.length-1; i++) {
			  int x1 = points[i][0];
			  int y1 = points[i][1];
			  int x2 = points[i+1][0];
			  int y2 = points[i+1][1];
			  double a = Math.atan2(x2 - x1, y2 - y1);
				double dis = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
				double x = x1;
				double y = y1;
				for (int j = 0; j < dis; j++) {
					togglePoints[(int) Math.round(x+(0-minX))][(int) Math.round(y+(0-minY))] = true;
					x+=Math.sin(a);
					y+=Math.cos(a);
				}
		  }
		  
		  for (int x = 0; x < w-0; x++) {
			  int toggleAmmount = 0;
			  
			  for (int l = 0; l < 2; l++) {
				  boolean drawing = false;
				  int t = 2;
				  for (int y = 1; y < h; y++) {
					  if (togglePoints[x][y]) {
						  if (t > 1) {
							  drawing = !drawing;
							  if (l == 0) toggleAmmount++;
						  }
						  t = 0;
						  if (l == 1) toggleAmmount--;
					  }
					  if (drawing && l == 1) setPixel(x+X-(0-minX), y+Y-(0-minY), c);
					  if (togglePoints[x][y]) setPixel(x+X-(0-minX), y+Y-(0-minY), c);
					  t++;
				  }
			  }
		  }
		  drawPoly(points2, c, X, Y);
	}
	
	public void drawCurve(int[][] controlPoints, int numSteps, Color color) {
        // Calculate the coefficients of the cubic spline
        double[] a = new double[controlPoints.length];
        double[] b = new double[controlPoints.length];
        double[] c = new double[controlPoints.length];
        double[] d = new double[controlPoints.length];

        for (int i = 0; i < controlPoints.length - 1; i++) {
            double x1 = controlPoints[i][0];
            double y1 = controlPoints[i][1];
            double x2 = controlPoints[i + 1][0];
            double y2 = controlPoints[i + 1][1];

            a[i] = y1;
            b[i] = (y2 - y1) / (double)(x2 - x1) - (2 * x1 + x2) * c[i] / 6.0 - (x1 + x2) * d[i] / 2.0;
            c[i] = (x1 + x2) * d[i] / 2.0;
            d[i] = (y2 - y1) / (double)(x2 - x1) - (y1 + y2) * c[i] / (2.0 * (x2 - x1));
        }

        // Calculate the points on the curve
        for (int i = 0; i < controlPoints.length - 1; i++) {
            for (double j = 0; j <= numSteps; j++) {
                double t = j / (double)numSteps;
                double x = (1 - t) * controlPoints[i][0] + t * controlPoints[i + 1][0];
                double y = a[i] + b[i] * t + c[i] * t * t + d[i] * t * t * t;

                setPixel((int)x, (int)y, color);
            }
        }
    }
	
	public Graphic scale(float scale) {
		Graphic newGraphic = new Graphic((int)(pW*scale), (int)(pH*scale));
		for (int x = 0; x < newGraphic.pW; x++) for (int y = 0; y < newGraphic.pH; y++) {
			newGraphic.setPixel(x, y, getPixel((int)Math.floor(x/scale), (int)Math.floor(y/scale)));
		}
		return newGraphic;
	} 
	
	
	public Graphic flip(boolean xAxis, boolean yAxis) {
		Graphic newGraphic = new Graphic((int)(pW), (int)(pH));
		for (int x = 0; x < newGraphic.pW; x++) for (int y = 0; y < newGraphic.pH; y++) {
			int X = pW-x-1, Y = pH-y-1;
			if (!xAxis) X = x;
			if (!yAxis) Y = y;
			newGraphic.setPixel(x, y, getPixel(X, Y));
		}
		return newGraphic;
	} 
	
	public Graphic rotate(double angle) {
		Graphic newGraphic = new Graphic((int)(pW), (int)(pH));
		for(double x = 0; x < pW; x++) for(double y = 0; y < pH; y++) {
			double x1 = x-pW/2.0;
			double y1 = y-pH/2.0;
			
			double X = Math.cos(angle)*x1+Math.sin(angle)*y1;
			double Y = -Math.sin(angle)*x1+Math.cos(angle)*y1;
			
			newGraphic.setPixel(Math.floor(X+pW/2.0), Math.floor(Y+pH/2.0), getPixel(x, y));
			newGraphic.setPixel(Math.floor(X+pW/2.0+1.0), Math.floor(Y+pH/2.0), getPixel(x, y));
		}
		return newGraphic;
	}
}
