package Utils;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Maths {

    public static Matrix4f createPerspectiveMatrix(float fov, float aspectRatio, float zNear, float zFar) {
        return new Matrix4f().perspective(fov, aspectRatio, zNear, zFar);
    }

    public static void setTransformationMatrix(Matrix4f matrix4f, Vector3f position, Vector3f rotation, Vector3f scale) {
        matrix4f.identity().translate(position).
                rotateX((float)Math.toRadians(rotation.x)).
                rotateY((float)Math.toRadians(rotation.y)).
                rotateZ((float)Math.toRadians(rotation.z)).scale(scale);
    }

}
