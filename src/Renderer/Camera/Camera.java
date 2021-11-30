package Renderer.Camera;

import Core.Keyboard;
import Utils.Maths;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;

public class Camera {

    private Matrix4f projectionMatrix;
    private Matrix4f viewMatrix;
    private Vector3f position;
    private Vector3f rotation;
    private Vector3f scale;

    private Vector3f velocity;
    private Vector3f acceleration;

    private ArrayList<Vector3f> forceQueue;

    public Camera(float fov, float aspectRatio, float zNear, float zFar, Vector3f position, Vector3f rotation, Vector3f scale) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.velocity = new Vector3f(0, 0, 0);
        this.acceleration = new Vector3f(0, 0, 0);
        this.projectionMatrix = Maths.createPerspectiveMatrix(fov, aspectRatio, zNear, zFar);
        this.viewMatrix = new Matrix4f().identity();
        Maths.setViewMatrix(this);
        this.forceQueue = new ArrayList<>();
    }

    public void tick() {
        //for(Vector3f pos : forceQueue) {
            //acceleration.add(pos);
        //}
        velocity.add(acceleration);
        velocity.mul(0.95f);
        position.add(velocity);
        acceleration.mul(0);
        forceQueue.clear();
    }

    public void queueForce(Vector3f newPosition) {
        //forceQueue.add(newPosition);
        acceleration.add(newPosition);
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    public Matrix4f getViewMatrix() {
        return viewMatrix;
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
