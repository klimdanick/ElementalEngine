package nl.klimdanick.E2.Core;

import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwGetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetWindowMonitor;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.opengl.GL30.*;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import nl.klimdanick.E2.Core.Debugging.DebugPanel;

public class Window {
	private boolean fullscreen = false;
	private int windowedX, windowedY, windowedWidth, windowedHeight;
    private long handle;
    public int width, height, scale;
    private String title;
    public boolean VSync;

    public Window(String title, int width, int height, int scale) {
        this.title = title;
        this.width = width;
        this.scale = scale;
        this.height = height;
    }

    public void create() {
        if (!glfwInit())
            throw new IllegalStateException("Failed to init GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        handle = glfwCreateWindow(width*scale, height*scale, title, NULL, NULL);
        if (handle == NULL)
            throw new RuntimeException("Failed to create window");

        glfwMakeContextCurrent(handle);
        glfwSwapInterval(0);
        VSync = false;
        glfwShowWindow(handle);

        GL.createCapabilities();
        DebugPanel.rows.put("Opengl", glGetString(GL_VERSION));
        DebugPanel.rows.put("Renderer", glGetString(GL_RENDERER));
    }
    
    public void toggleVSync() {
    	if (VSync) glfwSwapInterval(0);
    	else glfwSwapInterval(1);
    	VSync = !VSync;
    }

    public void update() {
        glfwSwapBuffers(handle);
        glfwPollEvents();
    }

    public boolean isOpen() {
        return !glfwWindowShouldClose(handle);
    }

    public void close() {
        glfwSetWindowShouldClose(handle, true);
    }

    public void destroy() {
        glfwDestroyWindow(handle);
        glfwTerminate();
    }

    public long getHandle() {
        return handle;
    }
    
    public void toggleFullscreen() {
        fullscreen = !fullscreen;

        long monitor = glfwGetPrimaryMonitor();
        GLFWVidMode vidMode = glfwGetVideoMode(monitor);

        if (fullscreen) {
            // Save current window position and size
            int[] x = new int[1], y = new int[1];
            glfwGetWindowPos(handle, x, y);
            windowedX = x[0];
            windowedY = y[0];

            int[] w = new int[1], h = new int[1];
            glfwGetWindowSize(handle, w, h);
            windowedWidth = w[0];
            windowedHeight = h[0];

            // Go fullscreen
            glfwSetWindowMonitor(handle, monitor, 0, 0,
                vidMode.width(), vidMode.height(),
                vidMode.refreshRate());
        } else {
            // Go back to windowed mode
            glfwSetWindowMonitor(handle, 0,
                windowedX, windowedY,
                windowedWidth, windowedHeight,
                0);
        }
    }
}