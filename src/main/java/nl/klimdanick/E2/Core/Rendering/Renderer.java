package nl.klimdanick.E2.Core.Rendering;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.lwjgl.opengl.GL11;

import nl.klimdanick.E2.Core.E2;
import nl.klimdanick.E2.Utils.Debug.DebugGraph;

public class Renderer {
	public SpriteBatch sprite;
	public ShapeBatch shape;
	public ShapeBatch debugBatch;
	public Camera2D camera;
	private E2 engine;
	public ArrayList<Batch> batches;
	public float dt = 0;
	
	public Renderer(E2 engine) {
		this.engine = engine;
		camera = new Camera2D(engine.getWidth(), engine.getHeight());
		sprite = new SpriteBatch(camera);
		shape = new ShapeBatch(camera);
		debugBatch = new ShapeBatch(new Camera2D(engine.getWidth(), engine.getHeight()));
		debugBatch.priority = Integer.MAX_VALUE;
		
		shape.priority = 0;
		sprite.priority = 2;
		
		batches = new ArrayList<>();
		batches.add(sprite);
		batches.add(shape);
		batches.add(debugBatch);
	}
	
	public void render() {		
		List<Batch> sorted = new ArrayList<>(batches);
		sorted.sort(Comparator.comparingInt(b -> b.priority));
		for (Batch b : sorted) {
			b.begin();
		}
		
		if (engine.debug)
		DebugGraph.render(this);
    	
    	engine.getGame().render();
    	
    	for (Batch b : sorted) {
			b.end();
		}
	}
}
