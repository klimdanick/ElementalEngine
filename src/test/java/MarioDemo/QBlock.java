package MarioDemo;

import java.awt.Color;

import com.danick.e2.renderer.Graphic;

public class QBlock extends Block {

	boolean hit = false;
	
	public QBlock(int x, int y) {
		super(x, y);
	}
	
	public QBlock(int x, int y, Item HoldItem) {
		super(x, y, HoldItem);
	}
	
	public void render(Graphic graphic) {
		graphic.clear();
		graphic.drawRectangle(0, 0, width, height, 100, new Color(0xffef9644));
		if (!hit) graphic.drawText("?", width/2, height/2, 0, new Color(0xfffdd9b1));
	}

	
	public void hit() {
		hit = true;
		if (HoldItem != null) {
			gc.addObject(HoldItem);
			HoldItem.y-=3;
		}
		this.HoldItem = null;
	}
}
