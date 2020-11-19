package com.danick.engine.gfx;

import java.awt.image.BufferedImage;

import com.danick.engine.*;
import com.danick.engine.gfx.*;
import com.danick.engine.objects.*;

import java.io.IOException;

import javax.imageio.ImageIO;

public class Image {
	private int w,h;
	private int[] p;
	
	public Image(String path) {
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(Image.class.getResourceAsStream("/" + path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		w = image.getWidth();
		h = image.getHeight();
		// x + y * image.getW()
	      p = new int[w + h * w];

	      for (int row = 0; row < h; row++) {
	         for (int col = 0; col < w; col++) {
	            if ((image.getRGB(col, row) >>24) == 0x00) p[col + row * w] = 0xffff00ff;
	            else p[col + row * w] = image.getRGB(col, row);
	         }
	      }
		
		image.flush();
	}
	
	public Image(int[] p, int w, int h) {
		this.w = w;
		this.h = h;
		this.p = p;
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public int[] getP() {
		return p;
	}

	public void setP(int[] p) {
		this.p = p;
	}
}
