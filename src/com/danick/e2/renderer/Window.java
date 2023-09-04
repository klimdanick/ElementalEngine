package com.danick.e2.renderer;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import com.danick.e2.main.GameContainer;

public class Window {
	public JFrame frame;
	public BufferedImage image;
	public Canvas canvas;
	public Graphics g;
	public BufferStrategy bs;
	
	public Window(GameContainer gc) {
		image = new BufferedImage(gc.width, gc.height, BufferedImage.TYPE_INT_ARGB);
		canvas = new Canvas();
		Dimension s = new Dimension((int)(gc.height), (int)(gc.height));
		canvas.setPreferredSize(s);
		canvas.setMinimumSize(s);
		
		frame = new JFrame(gc.title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(canvas, BorderLayout.CENTER);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(true);
		frame.setVisible(true);
		
		canvas.createBufferStrategy(2);
		bs = canvas.getBufferStrategy();
		g = bs.getDrawGraphics();
	}
	
	public void update() {
		g.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight(), null);
		g.setColor(Color.black);
		g.drawRoundRect(0, 0, 100, 100, 0, 0);
		bs.show();
	}

	public BufferedImage getImage() {
		return image;
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public JFrame getFrame() {
		return frame;
	}
}
