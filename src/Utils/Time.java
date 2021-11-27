package Utils;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class Time {

    public static double deltaTime = 0.0d;
    public static double getTimeSeconds() { return glfwGetTime(); }

}
