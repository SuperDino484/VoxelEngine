package Renderer.Camera;

import Renderer.Window;
import org.joml.Matrix4f;

public class Camera {

    private final float FOV = (float)Math.toRadians(60.0f);
    private final float zNear = 0.01f;
    private final float zFar = 1000.0f;
    private Matrix4f projectionMatrix;

    private float aspectRatio;

    public Camera() {
        this.aspectRatio = (float) Window.getInstance().getWidth() / Window.getInstance().getHeight();
        projectionMatrix = new Matrix4f().perspective(FOV, aspectRatio, zNear, zFar);
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }
}
