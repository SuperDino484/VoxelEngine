package Utils;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class Time {

    public static float deltaTime = 0.0f;
    public static double getTimeSeconds() { return glfwGetTime(); }

}
