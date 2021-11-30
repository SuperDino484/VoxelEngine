package Core;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class Mouse {

    private static double scrollX = 0d;
    private static double scrollY = 0d;
    private static double xPos = 0d;
    private static double yPos = 0d;
    private static double lastX = 0d;
    private static double lastY = 0d;
    private static boolean[] mouseButton = new boolean[3];
    private static boolean isDragging = false;


    public static void cursorPositionCallback(long window, double xpos, double ypos) {
        lastX = xPos;
        lastY = yPos;
        xPos = xpos;
        yPos = ypos;
        isDragging = mouseButton[0] || mouseButton[1] || mouseButton[2];
    }

    public static void mouseButtonCallback(long window, int button, int action, int mods) {
        if(action == GLFW_PRESS) {
            if(!(button < mouseButton.length)) return;
            mouseButton[button] = true;
        } else if(action == GLFW_RELEASE) {
            if(!(button < mouseButton.length)) return;
            mouseButton[button] = false;
            isDragging = false;
        }
    }

    public static void mouseScrollCallback(long window, double xoffset, double yoffset) {
        scrollX = xoffset;
        scrollY = yoffset;
    }

    public static void endFrame() {
        scrollX = 0d;
        scrollY = 0d;
        lastX = xPos;
        lastY = yPos;
    }

    public static float getXPos() {
        return (float)xPos;
    }

    public static float getYPos() {
        return (float)yPos;
    }

    public static float getDx() {
        return (float)(lastX - xPos);
    }

    public static float getDy() {
        return (float)(lastY - yPos);
    }

    public static float getScrollX() {
        return (float)scrollX;
    }

    public static float getScrollY() {
        return (float)scrollY;
    }

    public static boolean isDragging() {
        return isDragging;
    }

    public static boolean isMousePressed(int button) {
        if(!(button < mouseButton.length)) return false;
        return mouseButton[button];
    }

}
