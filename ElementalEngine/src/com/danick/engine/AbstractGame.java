package com.danick.engine;

import com.danick.engine.*;
import com.danick.engine.gfx.*;
import com.danick.engine.objects.*;

public abstract class AbstractGame {
	public abstract void init(GameContainer gc, renderer r);
	public abstract void update(GameContainer gc, float dt);
	public abstract void render(GameContainer gc, renderer r);
}
