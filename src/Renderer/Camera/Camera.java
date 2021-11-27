package Renderer.Camera;

import org.joml.Matrix4f;

public class Camera {

    private float fov;
    private float zNear;
    private float zFar;
    private Matrix4f projectionMatrix;
    private float aspectRatio;

    public Camera(float fov, float zNear, float zFar, float aspectRatio) {
        this.aspectRatio = aspectRatio;
        this.projectionMatrix = new Matrix4f().perspective(fov, aspectRatio, zNear, zFar);
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    public float getFov() {
        return fov;
    }

    public float getzNear() {
        return zNear;
    }

    public float getzFar() {
        return zFar;
    }

    public float getAspectRatio() {
        return aspectRatio;
    }
}
