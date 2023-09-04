package com.danick.e2.main;

import java.util.ArrayList;

import com.danick.e2.QSM.AbstractQSM;
import com.danick.e2.objects.GameObject;
import com.danick.e2.renderer.Graphic;
import com.danick.e2.renderer.Renderer2D;

public abstract class AbstractGame {
	
	public Graphic r;
	public boolean initialized = false;
	volatile public ArrayList<GameObject> GameObjects = new ArrayList<>();
	public ArrayList<AbstractQSM> QSMs = new ArrayList<>();
	
	public abstract void init(GameContainer gc, Graphic r);
	
	public abstract void update(GameContainer gc, long dt);
	
	public abstract void render(GameContainer gc, Graphic r);
}
