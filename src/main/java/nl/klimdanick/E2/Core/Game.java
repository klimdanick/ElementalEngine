package nl.klimdanick.E2.Core;

import nl.klimdanick.E2.Core.Rendering.Renderer;

public abstract class Game {
	
	public E2 engine;
	public Renderer renderer;

    public abstract void init();

    public abstract void update(float dt);

    public abstract void render();

    public void cleanup() {}
}
