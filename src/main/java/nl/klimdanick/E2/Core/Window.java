package nl.klimdanick.E2.Core;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

public class Window {

    private long handle;
    private int width, height;
    private String title;

    public Window(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
    }

    public void create() {
        if (!GLFW.glfwInit())
            throw new IllegalStateException("GLFW failed");

        handle = GLFW.glfwCreateWindow(width, height, title, 0, 0);

        GLFW.glfwMakeContextCurrent(handle);
        GLFW.glfwSwapInterval(1);

        GLFW.glfwShowWindow(handle);

        GL.createCapabilities();
    }

    public void clear() {
        GL11.glClearColor(0.1f, 0.1f, 0.15f, 1f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
    }

    public void update() {
    	int[] w = new int[1];
    	int[] h = new int[1];
    	GLFW.glfwGetWindowSize(handle, w, h);
    	this.width = w[0];
    	this.height = h[0];
        GLFW.glfwSwapBuffers(handle);
        GLFW.glfwPollEvents();
    }

    public boolean shouldClose() {
        return GLFW.glfwWindowShouldClose(handle);
    }

    public float getTime() {
        return (float) GLFW.glfwGetTime();
    }

    public void destroy() {
        GLFW.glfwDestroyWindow(handle);
        GLFW.glfwTerminate();
    }
    
    public int getWidth() {
    	return this.width;
    }
    
    public int getHeight() {
    	return this.width;
    }
    
    public long getHandle() {
    	return handle;
    }
    
    public int[] calculate(int virtualW, int virtualH) {
    	
    	int[] w = new int[1];
    	int[] h = new int[1];
    	GLFW.glfwGetWindowSize(handle, w, h);
    	this.width = w[0];
    	this.height = h[0];

        double scale = Math.min((double)this.width / virtualW, (double)this.height / virtualH);

        // prevent scale = 0
        if (scale < 1) scale = 1;

        int width = (int)(virtualW * scale);
        int height = (int)(virtualH * scale);

        int x = (this.width - width) / 2;
        int y = (this.height - height) / 2;

        return new int[]{x, y, width, height};
    }
}