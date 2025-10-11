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
	public static GameContainer gameContainer;
	
	public abstract void init(Graphic r);
	
	public abstract void update(long dt);
	
	public abstract void render(Graphic r);
}
