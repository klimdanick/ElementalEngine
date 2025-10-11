package nl.klimdanick.E2.Core;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.glfw.GLFWVidMode;

public class Window {
	private boolean fullscreen = false;
	private int windowedX, windowedY, windowedWidth, windowedHeight;
    private long handle;
    public int width, height, scale;
    private String title;

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
        glfwSwapInterval(1);
        glfwShowWindow(handle);

        GL.createCapabilities();
        System.out.println("OpenGL: " + GL11.glGetString(GL11.GL_VERSION));
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