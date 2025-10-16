package nl.klimdanick.E2.Core;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F11;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F3;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_ALT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_CONTROL;
import static org.lwjgl.glfw.GLFW.glfwGetFramebufferSize;
import static org.lwjgl.opengl.GL11.glViewport;

import nl.klimdanick.E2.Core.Debugging.DebugGraph;
import nl.klimdanick.E2.Core.Debugging.DebugPanel;
import nl.klimdanick.E2.Core.Rendering.DrawingMode;
import nl.klimdanick.E2.Core.Rendering.E2Color;
import nl.klimdanick.E2.Core.Rendering.Renderer;
import nl.klimdanick.E2.Core.Rendering.Texture;

public abstract class GameLoop {
    public static Window window;
    public static Renderer renderer;
    protected Input input;
    public static SteamInterface steam;
    public Texture screenTexture;
    public DebugGraph debugGraph;
    public DebugPanel debugPanel;

    public void start(String title, int width, int height, int scale) {    	
        window = new Window(title, width, height, scale);
        window.create();
        
        debugGraph = new DebugGraph();
        debugPanel = new DebugPanel();
        
        screenTexture = new Texture(width, height);

        renderer = new Renderer(width, height);
        input = new Input(window);

//        try {
//            steam = new SteamInterface();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        
        init();
        loop();

//        steam.shutdown();
        cleanup();
        window.destroy();
    }
    
    private Thread updateThread = new Thread() {
    	public void run() {
    		long lastTime = System.nanoTime();
    		long nsPerTick = (int) (1_000_000_000.0 / EngineSettings.maxTps);
    		
    		while (window.isOpen()) {
    			nsPerTick = (int) (1_000_000_000.0 / EngineSettings.maxTps);
    			long now = System.nanoTime();
                long dt = (now - lastTime);
                double tps = 1_000_000_000.0 / dt;
//                System.out.println("tps: " + tps);
                DebugPanel.rows.put("tps", (int)tps);
                DebugGraph.addPointToGraph("tps", (int)tps, E2Color.CURIOS_BLUE);
                lastTime = now;
                
    			update(dt/1_000_000_000.0);
    			Input.update();
    			
    			if (Input.isKeyPressed(GLFW_KEY_F11) || (Input.isKeyPressed(GLFW_KEY_ENTER) && input.isKeyDown(GLFW_KEY_LEFT_ALT))) {
            		window.toggleFullscreen();
            	}
    			
    			
    			if (debugPanel != null) if (Input.isKeyPressed(GLFW_KEY_F3)) {
    				debugPanel.show = !debugPanel.show;
    				if (debugGraph != null) {
	    				if (!debugPanel.show) debugGraph.show = false;
	    				else if (Input.isKeyDown(GLFW_KEY_LEFT_CONTROL)) debugGraph.show = true;
    				}
    			}
    			
    			now = System.nanoTime();
                dt = (now - lastTime);
                if (nsPerTick > dt) {
                	try {
                		int sleepTimeMS = (int)((nsPerTick - dt)/1_000_000.0);
                		int sleepTimeNS = (int)((nsPerTick - dt)%1_000_000.0);
						Thread.sleep(sleepTimeMS, sleepTimeNS);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
                }
    		}
    	}
    };
    
    long fpsTime0 = System.nanoTime();
    int frames = 0;

    private void loop() {
        long lastTime = System.nanoTime();
        long nsPerTick = (int) (1_000_000_000.0 / EngineSettings.maxFps);

        updateThread.start();
        
        while (window.isOpen()) {
        	nsPerTick = (int) (1_000_000_000.0 / EngineSettings.maxFps);
        	frames++;
            long now = System.nanoTime();
            long dt = (now - lastTime);
            double fps = 1_000_000_000.0 / dt;
//            System.out.println("fps: " + fps);
            if (now - fpsTime0 >= 1_000_000_000.0) {
            	DebugPanel.rows.put("fps", (int)frames);
            	fpsTime0 = now;
            	frames=0;
            }
            DebugGraph.addPointToGraph("fps", (int)fps, E2Color.DEBIAN_RED);
            lastTime = now;
            
            renderer.drawMode = DrawingMode.FILL;
            renderer.clear(E2Color.CINDER_BLACK);
            screenTexture.begin();
            render();
            if (debugGraph != null) debugGraph.render();
            if (debugPanel != null) debugPanel.render();
            screenTexture.end();
            
            resizeCanvas();

            renderer.drawTexture(screenTexture, screenTexture.getWidth()/2, screenTexture.getHeight()/2, screenTexture.getWidth(), screenTexture.getHeight(), 0);
            window.update();
            
            now = System.nanoTime();
            dt = (now - lastTime);
            if (nsPerTick > dt) {
            	try {
            		int sleepTimeMS = (int)((nsPerTick - dt)/1_000_000.0);
					Thread.sleep(sleepTimeMS);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            }
        }
    }
    
    private void resizeCanvas() {
    	int[] width = new int[1];
        int[] height = new int[1];
        glfwGetFramebufferSize(window.getHandle(), width, height);
        int windowWidth = width[0];
        int windowHeight = height[0];
        
        
        float scaleX = windowWidth / (float) screenTexture.getWidth();
        float scaleY = windowHeight / (float) screenTexture.getHeight();
        float scale = Math.min(scaleX, scaleY); // maintain aspect ratio
        
        int drawWidth = (int)(screenTexture.getWidth() * scale);
        int drawHeight = (int)(screenTexture.getHeight() * scale);
        int offsetX = (windowWidth - drawWidth) / 2;
        int offsetY = (windowHeight - drawHeight) / 2;
        glViewport(offsetX, offsetY, drawWidth, drawHeight);
    }

    protected abstract void init();
    protected abstract void update(double dt);
    protected abstract void render();
    protected abstract void cleanup();
}
