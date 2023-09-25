package cam.danick.e2.IDE2;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import com.danick.e2.main.GameContainer;
import com.danick.e2.renderer.Window;

public class PreviewWindow extends Window{
	public PreviewWindow(GameContainer gc) {
		this.gc = gc;
		image = new BufferedImage(gc.width, gc.height, BufferedImage.TYPE_INT_ARGB);
		canvas = new Canvas();
		Dimension s = new Dimension((int)(gc.width), (int)(gc.height));
		canvas.setPreferredSize(s);
		canvas.setMinimumSize(s);
		canvas.setSize(s);
	}
	
	public void createBuffer() {
		canvas.createBufferStrategy(2);
		bs = canvas.getBufferStrategy();
		g = bs.getDrawGraphics();
	}
	
	public void update() {
		
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
}
