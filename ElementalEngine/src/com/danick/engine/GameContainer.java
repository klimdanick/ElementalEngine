package com.danick.engine;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.danick.engine.gfx.gfx3d.Renderer3D;
import com.danick.engine.objects.GameObject;
import com.danick.engine.objects.HitBox;

public class GameContainer implements Runnable{

	private Thread thread;
	private Window window;
	private renderer Renderer;
	private Input input;
	private AbstractGame game;
	
	private boolean draw3d = false;
	
	private boolean running = false;
	private final double UPDATE_CAP = 1.0/60.0;
	private int width = 320, height = 240;
	private float scale = 4f;
	private String title = "ElementalEngine";
	public boolean firstFrame = true;
	
	private ArrayList<GameObject> objects = new ArrayList<GameObject>();
	private ArrayList<GameObject> collidableObjects = new ArrayList<GameObject>();
	
	public GameContainer(AbstractGame game) {
		this.game = game;
	}
	
	public GameContainer(AbstractGame game, int w, int h, float s) {
		this.game = game;
		this.width = w;
		this.height = h;
		this.scale = s;
	}
	
	public GameContainer(AbstractGame game, int w, int h, float s, String title) {
		this.game = game;
		this.width = w;
		this.height = h;
		this.scale = s;
		this.title = title;
	}
	
	public GameContainer(AbstractGame game, boolean draw3d) {
		this.game = game;
		this.draw3d = draw3d;
	}
	
	/*public GameContainer(AbstractGame game, int w, int h, float s, boolean draw3d) {
		this.game = game;
		this.width = w;
		this.height = h;
		this.scale = s;
		this.draw3d = draw3d;
	}
	
	public GameContainer(AbstractGame game, int w, int h, float s, String title, boolean draw3d) {
		this.game = game;
		this.width = w;
		this.height = h;
		this.scale = s;
		this.title = title;
		this.draw3d = draw3d;
	}*/
	
	public void start( ) {
		game.init(this, Renderer);
		window = new Window(this);
		if (!draw3d) Renderer = new renderer(this);
		else Renderer = new Renderer3D(this.width, this.height, 0, 0, 0, 0, 0, 0, this);
		input = new Input(this);
		
		thread = new Thread(this);
		thread.run();
	}
	
	public void stop() {
		
	}
	
	public void run() {
			
			try {
				Image loadingImg = (Image)ImageIO.read(Image.class.getResourceAsStream("/(0).png"));
				window.getCanvas().getGraphics().drawImage(loadingImg, 0, 0, (int)(width * scale), (int)(height * scale), null);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			int videoLength = 125;
		    BufferedImage im[] = new BufferedImage[videoLength];
		    Image im1[] = new Image[videoLength];
		for (int i = 1; i <= videoLength; i++) {
			try {
				im[i-1] = ImageIO.read(Image.class.getResourceAsStream("/(" + i + ").png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			im1[i-1] = im[i-1];
		}
		
		for (int i = 0; i < videoLength; i++) {
			window.getCanvas().getGraphics().drawImage(im1[i], 0, 0, (int)(width * scale), (int)(height * scale), null);
			
			try {
				Thread.sleep(1000/24);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		running = true;
		
		boolean render = false;
		double firstTime = 0;
		double lastTime = System.nanoTime() / 1000000000.0;
		double passedTime = 0;
		double unprocessedTime = 0;
		
		double frameTime = 0;
		int frames = 0;
		int fps = 0;
		
		while(running) {
			render = false;
			firstTime = System.nanoTime() / 1000000000.0;
			passedTime = firstTime - lastTime;
			lastTime = firstTime;
			
			unprocessedTime += passedTime;
			
			frameTime += passedTime;
			
			while (unprocessedTime >= UPDATE_CAP) {
				
				unprocessedTime -= UPDATE_CAP;
				render = true;
				
				game.update(this, (float)UPDATE_CAP);
				
				input.update();
				
				if (frameTime >= 1.0) {
					frameTime = 0;
					fps = frames;
					frames = 0;
					//System.out.println("FPS: " + fps);
				}
				
			}
			
			if (render) {
				
				//Renderer.clear();
				
				game.render(this, Renderer);
				window.update();
				frames++;
				firstFrame = false;
			} else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		dispose();
	}
	
	public void dispose() {
		
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Window getWindow() {
		return window;
	}

	public Input getInput() {
		return input;
	}

	public ArrayList<GameObject> getObjects() {
		return objects;
	}

	public void setObjects(ArrayList<GameObject> objects) {
		this.objects = objects;
	}

	public ArrayList<GameObject> collidableObjects() {
		return collidableObjects;
	}

	public void setCollidebleObjects(ArrayList<GameObject> collidableObjects) {
		this.collidableObjects = collidableObjects;
	}
	
	public void addObject(GameObject object) {
		objects.add(object);
		if (object.isCollidable()) {
			collidableObjects.add(object);
		}
	}
	
	public void removeObject(GameObject object) {
		objects.remove(object);
		if (object.isCollidable()) {
			collidableObjects.remove(object);
		}
	}
	
	public boolean collide(GameObject object) {
		for (GameObject o : collidableObjects) {
			if (o != object && o.isCollidable()) {
				HitBox oHB = o.getHitBox();
				HitBox objectHB = object.getHitBox();
				double oX1 = oHB.getX();
				double oX2 = oX1 + oHB.getWidth();
				double oY1 = oHB.getY();
				double oY2 = oY1 + oHB.getHeight();
				
				double objectX1 = objectHB.getX();
				double objectX2 = objectX1 + objectHB.getWidth();
				double objectY1 = objectHB.getY();
				double objectY2 = objectY1 + objectHB.getHeight();
				
				if (doOverlap(oX1, oY1, oX2, oY2, objectX1, objectY1, objectX2, objectY2)) return true;
			}
		}
		return false;
	} 
	
	public boolean collide(GameObject object, double offX, double offY) {
		for (GameObject o : collidableObjects) {
			if (o != object && o.isCollidable()) {
				HitBox oHB = o.getHitBox();
				HitBox objectHB = object.getHitBox();
				double oX1 = oHB.getX() - offX;
				double oX2 = oX1 + oHB.getWidth();
				double oY1 = oHB.getY() - offY;
				double oY2 = oY1 + oHB.getHeight();
				
				double objectX1 = objectHB.getX();
				double objectX2 = objectX1 + objectHB.getWidth();
				double objectY1 = objectHB.getY();
				double objectY2 = objectY1 + objectHB.getHeight();
				
				if (doOverlap(oX1, oY1, oX2, oY2, objectX1, objectY1, objectX2, objectY2)) return true;
			}
		}
		return false;
	} 
	
	public boolean collide(GameObject object, double offX, double offY, boolean z) {
		if (!z) return collide(object, offX, offY);
		for (GameObject o : collidableObjects) {
			if (o != object) {
				HitBox oHB = o.getHitBox();
				HitBox objectHB = object.getHitBox();
				double oX1 = oHB.getX() - offX;
				double oX2 = oX1 + oHB.getWidth();
				double oY1 = oHB.getY() - offY;
				double oY2 = oY1 + oHB.getHeight();
				
				double objectX1 = objectHB.getX();
				double objectX2 = objectX1 + objectHB.getWidth();
				double objectY1 = objectHB.getY();
				double objectY2 = objectY1 + objectHB.getHeight();
				
				if (doOverlap(oX1, oY1, oX2, oY2, objectX1, objectY1, objectX2, objectY2)) return true;
			}
		}
		return false;
	} 
	
	static boolean doOverlap(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) { 
        // If one rectangle is on left side of other  
        if (x1 >= x4 || x3 >= x2) { 
            return false; 
        } 
  
        // If one rectangle is above other  
        if (y1 >= y4 || y3 >= y2) { 
            return false; 
        } 
  
        return true; 
    } 
}
