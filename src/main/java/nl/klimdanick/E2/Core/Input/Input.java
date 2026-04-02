package nl.klimdanick.E2.Core.Input;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.glfw.GLFW;

public class Input {

    private static long window;

    private static boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];
    private static boolean[] keysLast = new boolean[GLFW.GLFW_KEY_LAST];

    private static boolean[] mouse = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
    private static boolean[] mouseLast = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];

    private static double mouseX, mouseY;
    
    private static Map<String, int[]> bindings = new HashMap<>();

    public static void init(long win) {
        window = win;
    }

    public static void update(int w, int h, int sw, int sh) {

        // copy current → last
        System.arraycopy(keys, 0, keysLast, 0, keys.length);
        System.arraycopy(mouse, 0, mouseLast, 0, mouse.length);

        // update keys
        for (int i = 0; i < keys.length; i++) {
            keys[i] = GLFW.glfwGetKey(window, i) == GLFW.GLFW_PRESS;
        }

        // update mouse buttons
        for (int i = 0; i < mouse.length; i++) {
            mouse[i] = GLFW.glfwGetMouseButton(window, i) == GLFW.GLFW_PRESS;
        }

        // mouse position
        double[] x = new double[1];
        double[] y = new double[1];
        GLFW.glfwGetCursorPos(window, x, y);
        
        mouseX = x[0] * (w / sw);
        mouseY = y[0] * (h / sh);
    }
    
    public static boolean isKeyDown(int key) {
        return keys[key];
    }

    public static boolean isKeyPressed(int key) {
        return keys[key] && !keysLast[key];
    }

    public static boolean isKeyReleased(int key) {
        return !keys[key] && keysLast[key];
    }
    
    public static boolean isMouseDown(int button) {
        return mouse[button];
    }

    public static boolean isMousePressed(int button) {
        return mouse[button] && !mouseLast[button];
    }
    
    public static float getMouseX() {
        return (float) mouseX;
    }

    public static float getMouseY() {
        return (float) mouseY;
    }
    
    public static void bind(String action, int... keys) {
        bindings.put(action, keys);
    }
    
    public static boolean get(String action) {
        int[] keys = bindings.get(action);
        if (keys == null) return false;

        for (int key : keys) {
            if (isKeyDown(key)) return true;
        }
        return false;
    }
    
    public static boolean pressed(String action) {
        int[] keys = bindings.get(action);
        if (keys == null) return false;
        
        for (int key : keys) {
        	if (isKeyPressed(key)) return true;
        }
        return false;
    }

    public static boolean released(String action) {
    	int[] keys = bindings.get(action);
        if (keys == null) return false;
        
        for (int key : keys) {
        	if (isKeyReleased(key)) return true;
        }
        return false;
    }
}