package nl.klimdanick.E2.Core.Rendering;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

import nl.klimdanick.E2.Core.E2;
import nl.klimdanick.E2.Utils.Debug.DebugGraph;

public class Renderer {
	public SpriteBatch sprite;
	public ShapeBatch shape;
	public ShapeBatch debugBatch;
	public Camera2D camera;
	private E2 engine;
	public ArrayList<Batch> batches;
	
	public Renderer(E2 engine) {
		this.engine = engine;
		camera = new Camera2D(engine.getWidth(), engine.getHeight());
		sprite = new SpriteBatch(camera);
		shape = new ShapeBatch(camera);
		debugBatch = new ShapeBatch(new Camera2D(engine.getWidth(), engine.getHeight()));
		debugBatch.priority = Integer.MAX_VALUE;
		
		batches = new ArrayList<>();
		batches.add(sprite);
		batches.add(shape);
		batches.add(debugBatch);
	}
	
	public void render() {
		
		Queue<Batch> batchQueue = new PriorityQueue<>((a, b) -> a.priority - b.priority);
		batchQueue.addAll(batches);
		for (Batch b : batchQueue) {
			b.begin();
		}
		
		DebugGraph.render(this);
    	
    	engine.getGame().render();
    	
    	Batch b;
    	while((b = batchQueue.poll()) != null) {
    		b.end();
    	}
	}
}
