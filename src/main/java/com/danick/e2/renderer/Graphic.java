package com.danick.e2.renderer;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import com.danick.e2.main.GameContainer;

public class Graphic extends Thread{
	
	volatile public int pixelWidth, pixelHeight;
	volatile public int[] pBuffer;
	volatile public int[] zBuffer;
	public int transX = 0, transY = 0;
	public boolean isDrawing = false;
	public GameContainer gc;
	private Font Font = com.danick.e2.renderer.Font.STANDARD;
	
	public Color bgColor = new Color(0xFF081017);
	
	public static int transparent = 0xffff00ff;
	public int checkerPatternWidth = 10;
	public int checkerPatternHeight = 10;
	public int toUpperCase = 32;
	
	
	
	public Graphic(int width, int height) {
		pixelWidth = width;
		pixelHeight = height;
		pBuffer = new int[width*height];
		zBuffer = new int[width*height];
		for (int i = 0; i < width*height; i++) {
			pBuffer[i] = 0xffff00ff;
			zBuffer[i] = Integer.MAX_VALUE;
		}
	}
	
	public static Graphic fromImage(String path) {
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(Graphic.class.getResourceAsStream("/" + path));
		} catch (IOException e) {
			System.out.println("error: "+path);
			e.printStackTrace();
		}
		catch (Exception e)
		{
			System.out.println("error: "+path);
			e.printStackTrace();
		}
		
		Graphic graphic = new Graphic(image.getWidth(), image.getHeight());
		int alphaBitMask = 24;

	      for (int row = 0; row < graphic.pixelHeight; row++) {
	         for (int col = 0; col < graphic.pixelWidth; col++) {
	            if ((image.getRGB(col, row) >> alphaBitMask) == 0x00) graphic.pBuffer[col + row * graphic.pixelWidth] = 0xffff00ff;
	            else graphic.pBuffer[col + row * graphic.pixelWidth] = image.getRGB(col, row);
	         }
	      }
		
		image.flush();
		return graphic;
	}
	public static Graphic fromImage(BufferedImage image) {
		if(image == null)
			return null;

		Graphic graphic = new Graphic(image.getWidth(), image.getHeight());
		int alphaBitMask = 24;

		for (int row = 0; row < graphic.pixelHeight; row++) {
			for (int col = 0; col < graphic.pixelWidth; col++) {
				if ((image.getRGB(col, row) >> alphaBitMask) == 0x00) graphic.pBuffer[col + row * graphic.pixelWidth] = 0xffff00ff;
				else graphic.pBuffer[col + row * graphic.pixelWidth] = image.getRGB(col, row);
			}
		}

		image.flush();
		return graphic;
	}

	public void run() {}

	
	public void clear() {
		for (int i = 0; i < pixelHeight*pixelWidth; i++) {
			pBuffer[i] = this.transparent;
			zBuffer[i] = Integer.MAX_VALUE;
		}
	}
	
	public void background() {
		for (int x = 0; x < pixelWidth; x++) for (int y = 0; y < pixelHeight; y++) {
			zBuffer[x+y*pixelWidth] = Integer.MAX_VALUE;
			if (Math.abs(Math.round(y/(float)(checkerPatternHeight)))%2 == Math.abs(Math.round(x/(float)(checkerPatternWidth)))%2) setPixel(x, y, Integer.MAX_VALUE, bgColor);
			else setPixel(x, y, Integer.MAX_VALUE, bgColor.darker());
		} 
	}
	
	public void setPixel(double X, double Y, double Z, int value) {
		int x = (int) Math.round(X);
		int y = (int) Math.round(Y);
		int z = (int) Math.round(Z);
		if((x - transX < 0 || x - transX >= pixelWidth || y - transY < 0 || y - transY >= pixelHeight)) {
			return;
		}
		if ((x - transX) + (y - transY) * pixelWidth >= pBuffer.length) return;
		if ((x - transX) + (y - transY) * pixelWidth < 0) return;
		if (zBuffer[(x - transX) + (y - transY) * pixelWidth] < z) return;
		pBuffer[(x - transX) + (y - transY) * pixelWidth] = value;
		zBuffer[(x - transX) + (y - transY) * pixelWidth] = z;
	}
	
	public void setPixel(double X, double Y, double Z, Color value) {
		setPixel(X, Y, Z, value.hashCode());
	}
	
	public void setPixel(double X, double Y, Color value) {
		setPixel(X, Y, Integer.MAX_VALUE, value.hashCode());
	}
	
	public Color getPixel(double X, double Y) {
		return getPixel(X, Y, false);
	}
	
	public Color getPixel(double X, double Y, boolean noError) {
		int x = (int) Math.round(X);
		int y = (int) Math.round(Y);
		if((x + transX < 0 || x + transX >= pixelWidth || y + transY < 0 || y + transY >= pixelHeight)) {
			if (!noError) {
				System.err.print("Unvallid pixel coördinate! returning black pixel! | Invalid location: ");
				System.err.print(x +", " + y);
				System.err.println(" for canvas size: " + pixelWidth + ", " + pixelHeight);
			}
			return new Color(0);
		}
		return new Color(pBuffer[(x + transX) + (y + transY) * pixelWidth]);
	}
	
	public Color getDepth(double X, double Y) {
		return getDepth(X, Y, false);
	}
	
	public Color getDepth(double X, double Y, boolean noError) {
		int x = (int) Math.round(X);
		int y = (int) Math.round(Y);
		if((x + transX < 0 || x + transX >= pixelWidth || y + transY < 0 || y + transY >= pixelHeight)) {
			if (!noError) {
				System.err.print("Unvallid pixel coördinate! returning black pixel! | Invalid location: ");
				System.err.print(x +", " + y);
				System.err.println(" for canvas size: " + pixelWidth + ", " + pixelHeight);
			}
			return new Color(0);
		}
		return new Color(zBuffer[(x + transX) + (y + transY) * pixelWidth]);
	}
	
	public Graphic getZBuffer() {
		Graphic zBuf = new Graphic(pixelWidth, pixelHeight);
		for (int i = 0; i < zBuffer.length; i++) {
			if (zBuffer[i] < 0) zBuf.pBuffer[i] = 0xFF000000;
			else zBuf.pBuffer[i] = 0xFF000000 + zBuffer[i]+10 * 0x00010000 + zBuffer[i] * 0x00000100 + zBuffer[i] * 0x00000001;
		}
		return zBuf;
	}

	public void drawText(String text, double offX, double offY, Color color) {
		drawText(text, offX, offY, Integer.MAX_VALUE, color);
	}	
	public void drawText(String text, double offX, double offY, double zBuf, Color color) {
		
		Graphic fontImage = Font.getFontImage();
		
		text= text.toUpperCase();
		int offset = 0;
		
		for(int i = 0; i < text.length(); i++) {
			int unicode = text.codePointAt(i) - toUpperCase;
			offset += Font.getWidths()[unicode];
		}
		offX-=offset/2;
		offY-=Font.getFontImage().pixelHeight/2;
		offset = 0;
		
		for(int i = 0; i < text.length(); i++) {
			int unicode = text.codePointAt(i) - toUpperCase;
			
			for (int y = 0; y < fontImage.pixelHeight; y++) {
				for (int x = 0; x < Font.getWidths()[unicode]; x++) {
					if (Font.getFontImage().pBuffer[(x + Font.getOffsets()[unicode]) + y * Font.getFontImage().pixelWidth] == 0xffffffff) {
						setPixel(x + offset + offX, y + offY, zBuf, color);
					}
				}
			}
			offset += Font.getWidths()[unicode];
		} 
	}
	
	public void setFont(Font Font) {
		this.Font = Font;
	}

	private boolean isOutOfBounds(Graphic r, double offX, double offY){
		return (offX + this.transX < -r.pixelWidth || offY + this.transY < -r.pixelHeight || offX + this.transX >= pixelWidth || offY + this.transY >= pixelHeight);
	}
	
	public void drawGraphic(Graphic renderer, double offX, double offY, double offZ) {

		if (isOutOfBounds(renderer, offX, offY)) return;
		
		for (int y = 0; y < renderer.pixelHeight; y++) {
			for (int x = 0; x < renderer.pixelWidth; x++) {
				if (!(renderer.pBuffer[x + y * renderer.pixelWidth] == 0xffff00ff)) setPixel((x) + offX, (y) + offY, offZ, renderer.pBuffer[x + y * renderer.pixelWidth]);
			}
		}
	}


	public void drawRectangle(double x1, double y1, double x2, double y2, double z, Color value) {
		for (double x = x1; x < x2; x++) {
			for (double y = y1; y < y2; y++) {
				setPixel(x, y, z, value);
			}
		}
	}
	
	public void translate(int x, int y) {
		transX += x;
		transY += y;
	}
	
	public void drawCircle(double x, double y, double z, double radius, Color value, boolean fill, double thinkness) {
		for (int X = (int) (x - radius); X < x+radius; X++) {
			for (int Y = (int) (y - radius); Y < y+radius; Y++) {
				double dist = (X-x)*(X-x)+(Y-y)*(Y-y);
				if (dist < radius*radius && (dist > (radius-thinkness)*(radius-thinkness) || fill)) setPixel(X, Y, z, value);
			}
		}
	}
	
	public void drawLine(double x0, double y0, double x1, double y1, int z, Color c) {
		if (Math.abs(y1- y0) < Math.abs(x1-x0)) {
    		if (x0 > x1)
    			drawLineLow(x1,y1, x0, y0, z, c);
    		else
    			drawLineLow(x0,y0, x1, y1, z, c);
    	} else {
    		if (y0 > y1)
    			drawLineHigh(x1,y1, x0, y0, z, c);
    		else
    			drawLineHigh(x0,y0, x1, y1, z, c);
    	}
	}
	
	private void drawLineLow(double x0, double y0, double x1, double y1, int z, Color c) {
		int dX = (int)(x1-x0);
    	int dY = (int)(y1-y0);
    	int Yi = dY < 0 ? -1 : 1;
    	dY = Math.abs(dY); 
    	
    	int D = (2*dY)-dX;
    	int y = (int)y0;
    	
    	int ddXY = 2*(dY-dX);
    	int ddY = 2*dY;
    	
    	for (int x = (int)x0; x <= x1; x++) {
    		setPixel(x, y, z, c);
    		if (D > 0) {
    			y+=Yi;
    			D+=ddXY;
    		} else {
    			D+=ddY;
    		}
    	}
	}
	
	private void drawLineHigh(double x0, double y0, double x1, double y1, int z, Color c) {
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
			setPixel(x, y, z, c);
			if (D > 0) {
				x+=Xi;
				D+=ddXY;
			} else {
				D+=ddX;
			}
		}
	}
	
	public void drawPoly(int[][] points2, Color c, int x, int y, int z) {
		int[][] points = new int[points2.length+1][2];
		  for (int i = 0; i < points2.length; i++) {
		    points[i] = points2[i];
		  }
		  points[points2.length] = points[0];
		  
		  for (int i = 0; i < 4; i++) {
			  this.drawLine(points[i][0]+x, points[i][1]+y, points[i+1][0]+x, points[i+1][1]+y, z, c);
		  }
	}
	
	public void fillPoly(int[][] points2, Color c, int X, int Y, int Z) {
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
			  for (int l = 0; l < 2; l++) {
				  boolean drawing = false;
				  int t = 2;
				  for (int y = 1; y < h; y++) {
					  if (togglePoints[x][y]) {
						  if (t > 1) {
							  drawing = !drawing;
						  }
						  t = 0;
					  }
					  if (drawing && l == 1) setPixel(x+X-(0-minX), y+Y-(0-minY), Z, c);
					  if (togglePoints[x][y]) setPixel(x+X-(0-minX), y+Y-(0-minY), Z, c);
					  t++;
				  }
			  }
		  }
		  drawPoly(points2, c, X, Y, Z);
	}
	
	/*
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
            b[i] = (y2 - y1) / (x2 - x1) - (2 * x1 + x2) * c[i] / 6.0 - (x1 + x2) * d[i] / 2.0;
            c[i] = (x1 + x2) * d[i] / 2.0;
            d[i] = (y2 - y1) / (x2 - x1) - (y1 + y2) * c[i] / (2.0 * (x2 - x1));
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
    }*/
	
	public Graphic scale(float scale) {
		Graphic newGraphic = new Graphic((int)(pixelWidth*scale), (int)(pixelHeight*scale));
		for (int x = 0; x < newGraphic.pixelWidth; x++) for (int y = 0; y < newGraphic.pixelHeight; y++) {
			newGraphic.setPixel(x, y, getPixel((int)Math.floor(x/scale), (int)Math.floor(y/scale)));
		}
		return newGraphic;
	} 
	
	
	public Graphic flip(boolean xAxis, boolean yAxis) {
		Graphic newGraphic = new Graphic(pixelWidth, pixelHeight);
		for (int flipedX = 0; flipedX < newGraphic.pixelWidth; flipedX++) for (int flipedY = 0; flipedY < newGraphic.pixelHeight; flipedY++) {
			int x = pixelWidth-flipedX-1, y = pixelHeight-flipedY-1;
			if (!xAxis) x = flipedX;
			if (!yAxis) y = flipedY;
			newGraphic.setPixel(flipedX, flipedY, getPixel(x, y));
		}
		return newGraphic;
	} 
	
	public Graphic rotate(double angle) {
		Graphic newGraphic = new Graphic(pixelWidth, pixelHeight);
		for(double x = 0; x < pixelWidth; x++) for(double y = 0; y < pixelHeight; y++) {
			double x1 = x-pixelWidth/2.0;
			double y1 = y-pixelHeight/2.0;
			
			double X = Math.cos(angle)*x1+Math.sin(angle)*y1;
			double Y = -Math.sin(angle)*x1+Math.cos(angle)*y1;
			
			newGraphic.setPixel(Math.floor(X+pixelWidth/2.0), Math.floor(Y+pixelHeight/2.0), getPixel(x, y));
			newGraphic.setPixel(Math.floor(X+pixelWidth/2.0+1.0), Math.floor(Y+pixelHeight/2.0), getPixel(x, y));
		}
		return newGraphic;
	}
}
