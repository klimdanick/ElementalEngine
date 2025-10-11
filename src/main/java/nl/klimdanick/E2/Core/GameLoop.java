package nl.klimdanick.E2.Core;

import nl.klimdanick.E2.Core.Rendering.Renderer;
import nl.klimdanick.E2.Core.Rendering.Texture;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL30.*;

public abstract class GameLoop {
    public static Window window;
    public static Renderer renderer;
    protected Input input;
    public static SteamInterface steam;
    public Texture screenTexture;

    public void start(String title, int width, int height, int scale) {    	
        window = new Window(title, width, height, scale);
        window.create();
        screenTexture = new Texture(width, height);

        renderer = new Renderer(width, height);
        input = new Input(window);

        try {
            steam = new SteamInterface();
        } catch (Exception e) {
            e.printStackTrace();
        }
        

        init();

        loop();

        steam.shutdown();
        cleanup();
        window.destroy();
    }

    private void loop() {
        long lastTime = System.nanoTime();
        double nsPerTick = 1_000_000_000.0 / 60.0;
        double delta = 0;

        while (window.isOpen()) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;

            while (delta >= 1) {
                update();
                delta--;
            }

            renderer.clear(0f, 0f, 0f);
            screenTexture.begin();
            render();
            screenTexture.end();
            
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

            renderer.drawTexture(screenTexture, screenTexture.getWidth()/2, screenTexture.getHeight()/2, screenTexture.getWidth(), screenTexture.getHeight());
            window.update();
            input.update();
            
            if (input.isKeyPressed(GLFW_KEY_F11) || (input.isKeyPressed(GLFW_KEY_ENTER) && input.isKeyDown(GLFW_KEY_LEFT_ALT))) {
        		window.toggleFullscreen();
        	}
        }
    }

    protected abstract void init();
    protected abstract void update();
    protected abstract void render();
    protected abstract void cleanup();
}
