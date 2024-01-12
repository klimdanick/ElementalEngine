package com.danick.e2.renderer;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Animation extends com.danick.e2.renderer.Graphic {
	
	public com.danick.e2.renderer.Graphic[] frames;
	public int frameCount;
	public int frame = 0;
	private boolean playing = true;
	private int t = 0;
	public int interval = 1;
	
	public Animation(int frameCount, int width, int height) {
		super(width, height);
		this.frameCount = frameCount;
		frames = new Graphic[frameCount];
		for (int i = 0; i < frameCount; i++) frames[i] = new Graphic(width, height);
	}
	
	public Animation(String path) {
		super(1, 1);
		BufferedImage animationImage = null;
		
		try {
			animationImage = ImageIO.read(Image.class.getResourceAsStream("/" + path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		pixelWidth = animationImage.getWidth();
		pixelHeight = animationImage.getHeight();
		pBuffer = animationImage.getRGB(0, 0, pixelWidth, pixelHeight, null, 0, pixelWidth);
		
		frameCount = pixelWidth / pixelHeight;
		
		frames = new Graphic[frameCount];
		
		for(int i = 0; i < frames.length; i++) {
			int[] pix = new int[pixelHeight * pixelHeight];
			for (int x = 0; x < pixelHeight; x++) {
				for (int y = 0; y < pixelHeight; y++) {
					pix[x + y * pixelHeight] = pBuffer[(x + (pixelHeight * i)) + y * pixelWidth];
				}
			}
			frames[i] = new Graphic(pixelHeight, pixelHeight);
			frames[i].pBuffer = pix;
		}
		
		animationImage.flush();
	}
	
	public Animation(String path, int frameCount) {
		super(1, 1);
		BufferedImage animationImage = null;
		
		try {
			animationImage = ImageIO.read(Image.class.getResourceAsStream("/" + path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		pixelWidth = animationImage.getWidth();
		pixelHeight = animationImage.getHeight();
		pBuffer = animationImage.getRGB(0, 0, pixelWidth, pixelHeight, null, 0, pixelWidth);
		
		frames = new Graphic[frameCount];
		
		for(int i = 0; i < frames.length; i++) {
			int[] pix = new int[pixelWidth/frameCount * pixelHeight];
			for (int x = 0; x < pixelWidth/frameCount; x++) {
				for (int y = 0; y < pixelHeight; y++) {
					pix[x + y * pixelHeight] = pBuffer[(x + (int)(Math.floor(pixelWidth/frameCount) * i)) + y * pixelWidth];
				}
			}
			frames[i] = new Graphic(pixelWidth/frameCount, pixelHeight);
			frames[i].pBuffer = pix;
		}
		
		animationImage.flush();
	}

	public void togglePlay() {
		playing = !playing;
	}
	
	public void update() {
		if (playing) {
			t++;
			if (t >= interval) {
				t = 0;
				frame++;
				//System.out.println(frame + " " + frameCount);
				if (frame >= frameCount) frame = 0;
				pBuffer = frames[frame].pBuffer;
			}
		}
	}
	
	public Animation scale(float scale) {
		Animation newAni = new Animation(frameCount, (int)(pixelWidth*scale), (int)(pixelHeight*scale));
		for (int i = 0; i < frameCount; i++) {
			newAni.frames[i] = frames[i].scale(scale);
		}
		newAni.interval = interval;
		newAni.frame = frame-1;
		newAni.playing = playing;
		newAni.update();
		return newAni;
		
	}
	
	public Animation flip(boolean xAxis, boolean yAxis) {
		
		Animation newAni = new Animation(frameCount, (int)(pixelWidth), (int)(pixelHeight));
		
		for (int i = 0; i < frameCount; i++) {
			newAni.frames[i] = frames[i].flip(xAxis, yAxis);
		}
		
		newAni.interval = interval;
		newAni.frame = frame-1;
		newAni.playing = playing;
		newAni.update();
		
		return newAni;
		
	}
}
