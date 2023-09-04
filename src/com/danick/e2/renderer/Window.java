package com.danick.e2.renderer;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.danick.e2.IDE.IDEGameContainer;
import com.danick.e2.main.GameContainer;

public class Window {
	public JFrame frame;
	public BufferedImage image;
	public Canvas canvas;
	public Graphics g;
	public BufferStrategy bs;
	protected GameContainer gc;
	
	public Window(GameContainer gc) {
		this.gc = gc;
		image = new BufferedImage(gc.width, gc.height, BufferedImage.TYPE_INT_ARGB);
		canvas = new Canvas();
		Dimension s = new Dimension((int)(gc.width), (int)(gc.height));
		canvas.setPreferredSize(s);
		canvas.setMinimumSize(s);
		canvas.setSize(s);
		
		frame = new JFrame(gc.title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(canvas, BorderLayout.CENTER);
		
		
		
		//frame.setLocationRelativeTo(null);
		frame.setResizable(true);
		frame.setSize(s);
		frame.pack();
		
		canvas.createBufferStrategy(2);
		bs = canvas.getBufferStrategy();
		g = bs.getDrawGraphics();
	}
	
	public Window() {}
	
	int oldW, oldH;
	
	public void update() {
		
		if (frame.getWidth()==oldW && frame.getHeight()==oldH && (frame.getWidth() != (int) (frame.getHeight()/gc.AspectRatio)))
			frame.setSize((int) (frame.getHeight()/gc.AspectRatio), frame.getHeight());
		
		
		oldW = frame.getWidth();
		oldH = frame.getHeight();
		
		canvas.createBufferStrategy(1);
		bs = canvas.getBufferStrategy();
		do {
			do {
				g = bs.getDrawGraphics();
				g.drawImage(image, 0, 0, canvas.getWidth(), (int) (canvas.getWidth()*gc.AspectRatio), null);
				g.dispose();
			} while(bs.contentsRestored());
		} while(bs.contentsLost());
		bs.show();
		canvas.paint(g);
	}
	
	public void setFullScreen(boolean fs) {
		System.err.print("\n Function Not In Use! ==> " + "window.setFullScreen is not \"yet\" in use, we are working on implementing this, usage for E2 development reasons only!");
		return;
		/*
		if (fs) frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		else frame.setExtendedState(JFrame.NORMAL);
		frame.setUndecorated(true);
		frame.setVisible(true);
		*/
	}
	
	public void toggleBorder() {
		frame.dispose();
		frame.setUndecorated(!frame.isUndecorated());
		frame.setVisible(true);
	}
	
	public void scale(float scale) {
		frame.setSize((int)(frame.getWidth() * scale), (int)(frame.getHeight() * scale));
	}
	
	
}
