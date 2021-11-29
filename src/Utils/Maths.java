package Utils;

import Renderer.Camera.Camera;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Maths {

    public static Matrix4f createPerspectiveMatrix(float fov, float aspectRatio, float zNear, float zFar) {
        return new Matrix4f().perspective(fov, aspectRatio, zNear, zFar);
    }

    public static void setViewMatrix(Camera camera) {
        Matrix4f matrix4f = camera.getViewMatrix();
        Vector3f position = camera.getPosition();
        Vector3f rotation = camera.getRotation();
        Vector3f scale = camera.getScale();
        matrix4f.identity().translate(position).
                rotateX((float)Math.toRadians(rotation.x)).
                rotateY((float)Math.toRadians(rotation.y)).
                rotateZ((float)Math.toRadians(rotation.z)).scale(scale);
    }

}
