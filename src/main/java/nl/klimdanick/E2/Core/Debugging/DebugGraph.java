package nl.klimdanick.E2.Core.Debugging;

import java.awt.Color;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import nl.klimdanick.E2.Core.GameLoop;
import nl.klimdanick.E2.Core.Rendering.DrawingMode;
import nl.klimdanick.E2.Core.Rendering.E2Color;
import nl.klimdanick.E2.Core.Rendering.Renderer;
import nl.klimdanick.E2.Core.Rendering.Texture;


public class DebugGraph {
	
	Texture graphTexture;
	public static HashMap<String, ConcurrentLinkedQueue<Integer>> graph = new HashMap<>();
	public static HashMap<String, E2Color> graphColor = new HashMap<>();
	
	public boolean show = false;
	
	public DebugGraph() {
		graphTexture = new Texture(200, 100);
	}
	
	public void render() {
		
		if (!show) return;
			
		Renderer r = GameLoop.renderer;
		
		r.drawMode = DrawingMode.FILL;
		graphTexture.begin();
		
		
		Object[] keys = graph.keySet().toArray();
		
		E2Color c = E2Color.CINDER_BLACK.clone();
		c.a = 0.5f;
		r.clear(c);
		
		int max = Integer.MIN_VALUE;
		for (Object key : keys) {
			Queue<Integer> points = graph.get(key);
			for (int p0 : points) {
				max = Math.max(max, p0);
			}
		}
			
		
		for (int i = 0; i <= 1000; i+=50) {
			float height = (int) (100-((double)i/max*100));
			E2Color white = E2Color.WHITE.clone();
			if (i == 0) white.a = 1f;
			else if (i%100 == 0) white.a = 0.75f;
			else white.a = 0.5f;
			r.drawLine(0, height, 200, height, white);
		}
			
		for (Object key : keys) {
			Queue<Integer> points = graph.get(key);
			Object[] p = points.toArray();
			for (int j = p.length-1; j > 0; j--) {
				int p0 = (int) p[j], p1 = (int) p[j-1];
				int x = 200-(p.length-j)*2;
				int y0 = (int) (100-((double)p0/max*100));
				int y1 = (int) (100-((double)p1/max*100));

//				g.drawLine(x, y0, x-2, y1, Integer.MIN_VALUE, graphColor.get(key));
				r.drawLine(x, y0, x-2, y1, graphColor.get(key));
			}
		}
		
		graphTexture.end();
		
		r.drawTexture(graphTexture, r.screenWidth-100, 50, 200, 100, 0);
	}
	
	public static void addPointToGraph(String gr, int p, E2Color c) {
		ConcurrentLinkedQueue<Integer> g = graph.get(gr);
		if (g == null) {
			g = new ConcurrentLinkedQueue<>();
			graph.put(gr, g);
		}
		graphColor.put(gr, c);
		g.add(p);
		while (g.size() > 100) g.poll();
	}
}
