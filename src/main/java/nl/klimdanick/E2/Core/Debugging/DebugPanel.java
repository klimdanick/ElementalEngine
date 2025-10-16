package nl.klimdanick.E2.Core.Debugging;

import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import nl.klimdanick.E2.Core.GameLoop;
import nl.klimdanick.E2.Core.Rendering.DrawingMode;
import nl.klimdanick.E2.Core.Rendering.E2Color;
import nl.klimdanick.E2.Core.Rendering.Renderer;
import nl.klimdanick.E2.Core.Rendering.TextTexture;
import nl.klimdanick.E2.Core.Rendering.Texture;

public class DebugPanel {

	
//	Font font = new Font("Monospaced", Font.PLAIN, 10);
//    renderer.drawText("Hello World!", font, 320, 180, E2Color.WHITE);
	
//	TextTexture text = new TextTexture(s, font, new Color(c.r, c.g, c.b, c.a));
//	drawTexture(text, x, y, (float)text.getWidth(), (float)text.getHeight());
	
	Texture graphTexture;
	public static HashMap<String, Object> rows = new HashMap<>();
	
	public boolean show = false;
	
	public DebugPanel() {
		graphTexture = new Texture(500, 500);
	}
	
	public void render() {
		
		if (!show) return;
		
		Renderer r = GameLoop.renderer;
		r.drawMode = DrawingMode.FILL;
		
		graphTexture.begin();
		
		r.clear(new E2Color(0, 0, 0, 0));
		
		Font font = new Font("Monospaced", Font.PLAIN, 10);
		
		int i = 0;
		Object[] keys = rows.keySet().toArray();
		Arrays.sort(keys);
		for (Object rowName : keys) {
			int x = 10, y = 5+i*15;
//			int[] size = g.drawText(rowName+": "+rows.get(rowName).toString(), x, y, Integer.MIN_VALUE, Color.white, false);
//			g.drawRectangle(x, y, x+size[0]+1, y+size[1]+1, Integer.MIN_VALUE+1, E2Color.CINDER_BLACK.setAlpha(150));
			
			TextTexture text = new TextTexture(rowName+": "+rows.get(rowName).toString(), font, new Color(255, 255, 255, 255));
			E2Color c = E2Color.CINDER_BLACK.clone();
			c.a = 0.6f;
			r.drawRect(x+text.getWidth()/2, y+text.getHeight()/2, text.getWidth()+2, text.getHeight(), c);
			r.drawTexture(text, x+text.getWidth()/2, y+text.getHeight()/2-1, (float)text.getWidth(), (float)text.getHeight(), 0);
			
			i++;
		}
		
		graphTexture.end();
		
		r.drawTexture(graphTexture, 250, 250, 500, 500, 0);
	}
}
