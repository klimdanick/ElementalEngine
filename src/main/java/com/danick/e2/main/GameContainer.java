package com.danick.e2.main;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import javax.swing.JFrame;

import com.danick.e2.QSM.AbstractQSM;
import com.danick.e2.objects.GameObject;
import com.danick.e2.renderer.Window;
import com.danick.e2.renderer.Graphic;
import com.danick.e2.renderer.Renderer2D;

public class GameContainer extends Thread{
	
	public static String version = "3.0.5 | ";
	
	private boolean running;
	public int updateRate = 60;
	public int frameRate = 60;
	public AbstractGame game;
	public AbstractGame nextgame = null;
	public int width = 1920, height = 1080;
	public String title = "E2";
	public Window window;
	public float AspectRatio;
	public Input input;
	private ArrayList<GameObject> TempGameObjects = new ArrayList<>();
	private ArrayList<GameObject> remGameObjects = new ArrayList<>();
	public boolean updateObjsR = true;
	
	public GameContainer(AbstractGame game, int width, int height, float scale, String title, String args[]) {
		for (String arg : args) System.out.println(arg);
		height-=40;
		this.width = width;
		this.height = height;
		this.title = title;
		window = new Window(this);
		input = new Input(this);
		AspectRatio = (float)height / (float)width;
		changeScene(game);
		//System.out.println(AspectRatio);
		window.frame.setVisible(true);
		window.scale(scale);
	}
	
	public GameContainer(AbstractGame game, int width, int height, float scale, String title) {
		this.width = width;
		this.height = height;
		this.title = title;
		window = new Window(this);
		input = new Input(this);
		AspectRatio = (float)height / (float)width;
		changeScene(game);
		//System.out.println(AspectRatio);
		window.frame.setVisible(true);
		window.scale(scale);
	}
	
	public GameContainer() {
	}
	
	public void changeScene(AbstractGame scene) {
		this.nextgame = scene;
		if (!running) {
			game = nextgame;
		}
		input = new Input(this);
		if (!nextgame.initialized) {
			nextgame.r = new Renderer2D(this);
			nextgame.init(this, nextgame.r);
			nextgame.initialized = true;
		}
		
		
	}
	
	public void changeRenderer(Graphic r) {
		this.game.r = r;
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void run() {
		this.setName("Update Thread");
		long prevTime = System.currentTimeMillis();
		//game.r = new Renderer2D(this);
		running = true;
		game.r.start();
		long newTime = System.currentTimeMillis();
		long dt = newTime - prevTime;
		while(running) {
			
			for (AbstractQSM qsm : game.QSMs) qsm.update();
			try {
				game.update(this, dt);
			} catch (ConcurrentModificationException e) {
				System.err.print(updateObjsR);
			}
			
			
			if (updateObjsR) {
				game.GameObjects.addAll(TempGameObjects);
				game.GameObjects.removeAll(remGameObjects);
				TempGameObjects.clear();
				remGameObjects.clear();
			}
			try {
				for (GameObject obj : game.GameObjects) obj.update(this);
			} catch (ConcurrentModificationException e) {
				System.err.println(e.getClass() + "in the update tread! \n Are you using GameObjects.remove() or GameObjects.add()?\nUse GameContainer.addObject() or GameContainer.removeObject() instead!");
			}
			
			if (nextgame!=null) {
				game = nextgame;
				nextgame=null;
			}
			input.update();
			
			dt = newTime - prevTime;
			prevTime = newTime;
			newTime = System.currentTimeMillis();
			long waitTime = (1000/updateRate) - dt;
			
			if (waitTime > 0) {
				try {Thread.sleep(waitTime);}
				catch (InterruptedException e) {e.printStackTrace();}
			} else if (waitTime < -100) System.err.print("\n Update loop is slow! ==> " + Math.abs(waitTime) + " milli(s) behind!\n");
		}
	}
	
	public void stopWindow() {
		running = false;
	}
	
	public void end() {
		System.exit(0);
	}
	
	public void addObject(GameObject obj) {
		obj.gc=this;
		if (running) TempGameObjects.add(obj);
		else game.GameObjects.add(obj);
	}
	
	public void removeObject(GameObject obj) {
		if (running) remGameObjects.add(obj);
		else game.GameObjects.remove(obj);
	}
	
	public ArrayList<GameObject> getObjects() {
		ArrayList<GameObject> clone = new ArrayList<>(game.GameObjects);
		return clone;
	}
}
