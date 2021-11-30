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

    private ArrayList<Vector3f> positionQueue;

    public Camera(float fov, float aspectRatio, float zNear, float zFar, Vector3f position, Vector3f rotation, Vector3f scale) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.projectionMatrix = Maths.createPerspectiveMatrix(fov, aspectRatio, zNear, zFar);
        this.viewMatrix = new Matrix4f().identity();
        Maths.setViewMatrix(this);
        this.positionQueue = new ArrayList<>();
    }

    public void input() {
        if(Keyboard.isKeyPressed(GLFW_KEY_A)) {
            queuePosition(new Vector3f(0.1f, 0f, 0f));
        }
        if(Keyboard.isKeyPressed(GLFW_KEY_D)) {
            queuePosition(new Vector3f(-0.1f, 0f, 0f));
        }
        if(Keyboard.isKeyPressed(GLFW_KEY_W)) {
            queuePosition(new Vector3f(0f, 0f, 0.1f));
        }
        if(Keyboard.isKeyPressed(GLFW_KEY_S)) {
            queuePosition(new Vector3f(0f, 0f, -0.1f));
        }
        if(Keyboard.isKeyPressed(GLFW_KEY_SPACE)) {
            queuePosition(new Vector3f(0f, -0.1f, 0f));
        }
        if(Keyboard.isKeyPressed(GLFW_KEY_LEFT_CONTROL)) {
            queuePosition(new Vector3f(0f, 0.1f, 0f));
        }

    }

    public void tick() {
        for(Vector3f pos : positionQueue) {
            position.add(pos);
        }
        positionQueue.clear();
    }

    private void queuePosition(Vector3f newPosition) {
        positionQueue.add(newPosition);
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
