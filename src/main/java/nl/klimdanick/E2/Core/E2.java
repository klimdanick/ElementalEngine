package nl.klimdanick.E2.Core;

import org.lwjgl.opengl.GL11;

import nl.klimdanick.E2.Core.Input.Input;
import nl.klimdanick.E2.Core.Rendering.Framebuffer;
import nl.klimdanick.E2.Core.Rendering.Renderer;
import nl.klimdanick.E2.Core.Rendering.ScreenRenderer;
import nl.klimdanick.E2.Utils.Debug.DebugGraph;

public class E2 {

    private Window window;
    private Game game;
    private Framebuffer framebuffer;
    private ScreenRenderer screenRenderer;
    private int width, height;
    public Renderer renderer;
    public boolean debug = false;

    public E2(Game game, int width, int height) {
    	this.width = width;
    	this.height = height;
        this.game = game;
    }

    public void run() {
        window = new Window(width, height, "My Engine");
        window.create();
        System.out.println(GL11.glGetString(GL11.GL_VERSION));
        
        renderer = new Renderer(this);
        Input.init(window.getHandle());
        
        game.engine = this;
        game.renderer = renderer;
        
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        
        framebuffer = new Framebuffer(width, height); // your virtual resolution
        screenRenderer = new ScreenRenderer();

        game.init();

        float lastTime = window.getTime();

        while (!window.shouldClose()) {

            float currentTime = window.getTime();
            float dt = currentTime - lastTime;
            lastTime = currentTime;
            
            renderer.dt = dt;
            DebugGraph.addPoint("fps", (int)(1/dt));

            Input.update(width, height, window.getWidth(), window.getHeight());
            
            game.update(dt);

            // 1. Render to framebuffer (LOW RES)
            framebuffer.bind();
            window.clear();

            renderer.render();

            framebuffer.unbind(window.getWidth(), window.getHeight());
            
            // 2. Calculate letterbox viewport
            int[] vp = window.calculate(width, height);
            
            // 3. Clear full screen (black bars)
            GL11.glClearColor(0f, 0f, 0f, 1f);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
            
            // 4. Set viewport to centered area
            GL11.glViewport(vp[0], vp[1], vp[2], vp[3]);
            
            // 5. Draw framebuffer texture
            screenRenderer.render(framebuffer.getTexture());

            // 6. Reset viewport (important if reused later)
            GL11.glViewport(0, 0, window.getWidth(), window.getHeight());
            
            window.update();
        }

        game.cleanup();
        window.destroy();
    }

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public Game getGame() {
		return game;
	}
}