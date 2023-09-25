package com.danick.e2.IDE;

import java.awt.Color;
import java.awt.event.KeyEvent;

import com.danick.e2.main.AbstractGame;
import com.danick.e2.main.GameContainer;
import com.danick.e2.renderer.Graphic;
import com.danick.e2.renderer.Renderer2D;

public class GamePreview extends AbstractGame {
	
	@Override
	public void update(GameContainer gc, long dt) {
	}

	@Override
	public void init(GameContainer gc, Graphic r) {
		// TODO Auto-generated method stub
		r.bgColor = Color.DARK_GRAY;
		
	}
	
	double x = 0;

	@Override
	public void render(GameContainer gc, Graphic r) {
		// TODO Auto-generated method stub
		r.clear();
		r.drawCircle(30+Math.sin(x), 30, 20, Color.white, false, 3);
		r.drawCircle(30, 80+Math.sin(x), 20, Color.white, false, 3);
		x+=0.1;
	}

}
