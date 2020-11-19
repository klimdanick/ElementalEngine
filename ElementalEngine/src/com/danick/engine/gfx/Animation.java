package com.danick.engine.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.danick.engine.*;
import com.danick.engine.gfx.*;
import com.danick.engine.objects.*;

public class Animation {
	
	private int w,h;
	private Image[] images;
	private int[] p;
	private int frameCount;
	private int frame = 0;
	boolean playing = true;
	private int t = 0;
	private int fpf = 1;
	
	public Animation(String path) {
		BufferedImage animationImage = null;
		
		try {
			animationImage = ImageIO.read(Image.class.getResourceAsStream("/" + path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		w = animationImage.getWidth();
		h = animationImage.getHeight();
		p = animationImage.getRGB(0, 0, w, h, null, 0, w);
		
		frameCount = w / h;
		
		images = new Image[frameCount];
		
		for(int i = 0; i < images.length; i++) {
			int[] pix = new int[h * h];
			for (int x = 0; x < h; x++) {
				for (int y = 0; y < h; y++) {
					pix[x + y * h] = p[(x + (h * i)) + y * w];
				}
			}
			images[i] = new Image(pix, h, h);
		}
		
		animationImage.flush();
	}
	
	public Animation(String path, int frames) {
		if (frames ==1) {
			frameCount = 1;
			images = new Image[frames];
			images[0] = new Image(path);
		}
		else {
			BufferedImage animationImage = null;
			
			try {
				animationImage = ImageIO.read(Image.class.getResourceAsStream("/" + path));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			w = animationImage.getWidth();
			h = animationImage.getHeight();
			p = animationImage.getRGB(0, 0, w, h, null, 0, w);
			
			frameCount = frames;
			
			images = new Image[frameCount];
			
			for(int i = 0; i < images.length; i++) {
				int[] pix = new int[w/frames * h];
				for (int x = 0; x < w/frames; x++) {
					for (int y = 0; y < h; y++) {
						pix[x + y * h] = p[(x + (int)(Math.floor(w/frames) * i)) + y * w];
					}
				}
				images[i] = new Image(pix, w/frames, h);
			}
			
			animationImage.flush();
		}
	}
	
	public int getFrame() {
		return frame;
	}
	
	public void setFrame(int x) {
		frame = x;
	}
	
	public int getLength() {
		return frameCount;
	}
	
	public void play() {
		playing = true;
	}
	
	public void stop() {
		playing = false;
	}
	
	public Image getImage() {
		return images[frame];
	} 
	
	public void update() {
		if (playing) {
			t++;
			if (t >= fpf) {
				t = 0;
				frame++;
				if (frame == frameCount) frame = 0;
			}
		}
	}

	public void setFpf(int fpf) {
		this.fpf = fpf;
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
		for(int i = 0; i < images.length; i++) {
			int[] pix = new int[h * h];
			for (int x = 0; x < h; x++) {
				for (int y = 0; y < h; y++) {
					pix[x + y * h] = p[(x + (h * i)) + y * w];
				}
			}
			images[i] = new Image(pix, h, h);
		}
	}
}
