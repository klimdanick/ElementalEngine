package com.danick.e2.main;

import com.danick.e2.renderer.renderer;

public abstract class AbstractGame {
	public abstract void init(GameContainer gc);
	
	public abstract void update(GameContainer gc, long dt);
	
	public abstract void render(GameContainer gc, renderer r);
}
