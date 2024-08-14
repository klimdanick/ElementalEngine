package com.danick.e2.renderer;

import java.awt.image.DataBufferInt;
import java.awt.image.Raster;
import java.util.ConcurrentModificationException;

import com.danick.e2.main.GameContainer;
import com.danick.e2.objects.GameObject;

public class Renderer2D extends Graphic{
	
	public int chackerPattrnWidth = 10;
	public int chackerPattrnHeight = 10;

	public Renderer2D(GameContainer gc) {
		super(gc.width, gc.height);
		this.gc = gc;
		pBuffer = ((DataBufferInt)gc.window.image.getRaster().getDataBuffer()).getData();
		this.setName("Render Thread");
		clear();
	}

	public void run() {
		pixelWidth = gc.window.image.getWidth();
		pixelHeight = gc.window.image.getHeight();
		long prevTime = System.currentTimeMillis();
		long newTime = System.currentTimeMillis();
		long dt = newTime - prevTime;
		while(true) {
			try {	
				gc.game.render(this);
			} catch (ConcurrentModificationException e) {}
			gc.updateObjsR = false;
			try {
				
				for (GameObject obj : gc.getObjects()) {
					obj.render(obj.sprite);
					if (obj.sprite instanceof Animation) {
						((Animation) obj.sprite).update();
					}
					this.drawGraphic(obj.sprite, (int)obj.x, (int)obj.y, (int)obj.z);
				}
			} catch (ConcurrentModificationException e) {
				System.err.println(e.getClass() + "in the render tread! \n Are you using GameObjects.remove() or GameObjects.add()?\nUse GameContainer.addObject() or GameContainer.removeObject() instead!");
			}
			gc.updateObjsR = true;
			gc.window.update();
			
			dt = newTime - prevTime;
			prevTime = newTime;
			newTime = System.currentTimeMillis();
			long waitTime = (1000/gc.frameRate) - dt;
			//System.getLogger(getName()).log(Logger.Level.INFO, waitTime+" "+dt);
			if (waitTime > 0) {
				try {Thread.sleep(waitTime);}
				catch (InterruptedException e) {e.printStackTrace();}
			} else if (waitTime < -100) System.err.print("\n Render loop is slow! ==> " + Math.abs(waitTime) + " milli(s) behind!\n");
		}
	}
	
	public void clear() {
		background();
	}
	
	
}
