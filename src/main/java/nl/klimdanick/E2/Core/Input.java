package nl.klimdanick.E2.Core;

import static org.lwjgl.glfw.GLFW.*;

public class Input {
    private static long window;
    private static final boolean[] keys = new boolean[GLFW_KEY_LAST];
    private static final boolean[] keysLast = new boolean[GLFW_KEY_LAST];

    public Input(Window window) {
        Input.window = window.getHandle();
    }

    public static void update() {
        // Copy current state to last
        System.arraycopy(keys, 0, keysLast, 0, keys.length);

        // Update current state
        for (int i = 0; i < GLFW_KEY_LAST; i++) {
            keys[i] = glfwGetKey(window, i) == GLFW_PRESS;
        }
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
}