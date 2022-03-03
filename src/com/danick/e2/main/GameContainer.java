package com.danick.e2.main;

import com.danick.e2.renderer.Window;
import com.danick.e2.renderer.renderer;

public class GameContainer extends Thread{
	public renderer r;
	public boolean running;
	public int updateRate = 60;
	public int frameRate = 60;
	public AbstractGame game;
	public int width, height;
	public String title = "E2";
	public Window window;
	
	public GameContainer(AbstractGame game) {
		this.game = game;
		running = true;
		window = new Window(this);
	}
	
	public GameContainer(AbstractGame game, int width, int height) {
		this.game = game;
		running = true;
		this.width = width;
		this.height = height;
		window = new Window(this);
	}
	
	public GameContainer(AbstractGame game, int width, int height, String title) {
		this.game = game;
		running = true;
		this.width = width;
		this.height = height;
		this.title = title;
		window = new Window(this);
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void run() {
		long prevTime = System.currentTimeMillis();
		game.init(this);
		long newTime = System.currentTimeMillis();
		long dt = newTime - prevTime;
		while(running) {
			
			game.update(this, dt);
			window.update();
			
			dt = newTime - prevTime;
			prevTime = newTime;
			newTime = System.currentTimeMillis();
			long waitTime = (1000/updateRate) - dt;
			
			if (waitTime > 0) {
				try {Thread.sleep(waitTime);}
				catch (InterruptedException e) {e.printStackTrace();}
			} else if (waitTime < -5) System.err.print("\n Update loop is slow! ==> " + Math.abs(waitTime) + " milli(s) behind!\n");
		}
	}
}
