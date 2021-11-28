package Renderer.Camera;

import Utils.Maths;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {

    private Matrix4f projectionMatrix;
    private Matrix4f transformationMatrix;
    private Vector3f position;
    private Vector3f rotation;
    private Vector3f scale;

    public Camera(float fov, float aspectRatio, float zNear, float zFar, Vector3f position, Vector3f rotation, Vector3f scale) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.projectionMatrix = Maths.createPerspectiveMatrix(fov, aspectRatio, zNear, zFar);
        this.transformationMatrix = new Matrix4f().identity();
        Maths.setTransformationMatrix(this.transformationMatrix, position, rotation, scale);
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    public Matrix4f getTransformationMatrix() {
        return transformationMatrix;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public Vector3f getScale() {
        return scale;
    }
}
