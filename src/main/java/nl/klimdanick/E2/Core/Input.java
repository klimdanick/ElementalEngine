package nl.klimdanick.E2.Core;

import static org.lwjgl.glfw.GLFW.*;

public class Input {
    private final long window;
    private final boolean[] keys = new boolean[GLFW_KEY_LAST];
    private final boolean[] keysLast = new boolean[GLFW_KEY_LAST];

    public Input(Window window) {
        this.window = window.getHandle();
    }

    public void update() {
        // Copy current state to last
        System.arraycopy(keys, 0, keysLast, 0, keys.length);

        // Update current state
        for (int i = 0; i < GLFW_KEY_LAST; i++) {
            keys[i] = glfwGetKey(window, i) == GLFW_PRESS;
        }
    }

    public boolean isKeyDown(int key) {
        return keys[key];
    }

    public boolean isKeyPressed(int key) {
        return keys[key] && !keysLast[key];
    }

    public boolean isKeyReleased(int key) {
        return !keys[key] && keysLast[key];
    }
}