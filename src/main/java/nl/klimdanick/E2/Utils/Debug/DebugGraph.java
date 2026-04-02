package nl.klimdanick.E2.Utils.Debug;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import nl.klimdanick.E2.Core.Rendering.Renderer;
import nl.klimdanick.E2.Utils.E2Color;

public class DebugGraph {
	private static HashMap<String, ArrayList<Integer>> graphs = new HashMap<>();
	private static final int MAX_SIZE = 100;
	
	
	public static void addPoint(String id, int value) {
		ArrayList<Integer> graph = graphs.get(id);
		if (graph == null) {
			graph = new ArrayList<>();
			graph.add(value);
			graphs.put(id, graph);
			return;
		}
		
		graph.add(value);
		
		while(graph.size() > MAX_SIZE) graph.removeFirst();
	}
	
	public static void render(Renderer r) {
		r.debugBatch.rect(0, 0, 100, 100, new E2Color(0.1, 0.1, 0.1, 0.2));
		Set<String> graphNames = graphs.keySet();
		for (String name : graphNames) {	
			ArrayList<Integer> points = graphs.get(name);
			Integer pPrev = null;
			int x = 0;
			for (Integer p : points) {
				if (pPrev == null) pPrev = p;
				r.debugBatch.line(x-1, 100-pPrev, x, 100-p, E2Color.DEBIAN_RED);
				pPrev = p;
				x++;
			}			
		}
	}
}
