package Core;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class Keyboard {

    private static boolean[] keys = new boolean[512];

    public static boolean isKeyPressed(int key) {
        return keys[key];
    }

    public static void keyCallback(long window, int key, int scancode, int action, int mods) {
        if(action == GLFW_PRESS) {
            keys[key] = true;
        }
        else if(action == GLFW_RELEASE) {
            keys[key] = false;
        }
    }

}
