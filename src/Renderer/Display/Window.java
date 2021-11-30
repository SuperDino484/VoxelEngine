package Renderer.Display;

import Core.Keyboard;
import Core.Mouse;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;

public class Window {

    private String title;
    private int width;
    private int height;
    private long windowHandle;

    private static Window windowInstance;

    private Window(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        initializeWindow();
    }

    public static Window createWindow(String title, int width, int height) {
        if(windowInstance == null)
        {
            windowInstance = new Window(title, width, height);
        }
        return windowInstance;
    }

    public static Window getInstance() {
        return windowInstance;
    }

    private void initializeWindow() {
        GLFWErrorCallback.createPrint(System.err).set();

        if(!glfwInit())
            throw new IllegalStateException("Could not initialize glfw");

        windowHandle = glfwCreateWindow(width, height, title, 0, 0);
        if(windowHandle == 0)
            throw new IllegalStateException("Could not initialize window");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_SAMPLES, 16);

        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(windowHandle, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    windowHandle,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        }
        glfwSetKeyCallback(windowHandle, Keyboard::keyCallback);
        glfwSetCursorPosCallback(windowHandle, Mouse::cursorPositionCallback);
        glfwSetMouseButtonCallback(windowHandle, Mouse::mouseButtonCallback);
        glfwSetScrollCallback(windowHandle, Mouse::mouseScrollCallback);

        glfwMakeContextCurrent(windowHandle);


        glfwSwapInterval(1);

        glfwShowWindow(windowHandle);

        GL.createCapabilities();
        glEnable(GL_DEPTH_TEST);
    }

    public void update() {
        glfwSwapBuffers(windowHandle);
        glfwPollEvents();
    }

    public void cleanup() {
        glfwDestroyWindow(windowHandle);
        glfwFreeCallbacks(windowHandle);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public String getTitle() {
        return title;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public long getWindowHandle() {
        return windowHandle;
    }
}
