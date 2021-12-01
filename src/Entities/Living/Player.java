package Entities.Living;

import Core.Keyboard;
import Entities.LivingEntity;
import Renderer.Camera.Camera;
import Renderer.Models.TexturedModel;
import Utils.Time;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class Player extends LivingEntity {

    private Camera camera;
    private final float cameraLerpSpeed = 0.05f;
    private boolean faceLeft = true;
    //private final int lerpDistance = 1;

    public Player(Camera camera, int health, int maxHealth, Vector3f position, Vector3f rotation, Vector3f scale, TexturedModel model) {
        super(health, maxHealth, position, rotation, scale, model);
        this.camera = camera;
    }

    public void tick() {
        lerpCamera();
        if(faceLeft) {
            Vector3f newRot = new Vector3f(0, 0, 0);
            getRotation().lerp(newRot, 0.2f);
        } else {
            Vector3f newRot = new Vector3f(0, 180, 0);
            getRotation().lerp(newRot, 0.2f);
        }
    }

    public void input() {
        if(Keyboard.isKeyPressed(GLFW_KEY_A)) {
            faceLeft = true;
            getPosition().add(new Vector3f(-5f * Time.deltaTime, 0f, 0f));
        }
        if(Keyboard.isKeyPressed(GLFW_KEY_D)) {
            faceLeft = false;
            getPosition().add(new Vector3f(5f * Time.deltaTime, 0f, 0f));
        }
    }

    private void lerpCamera() {
        Vector3f posX = new Vector3f(-getPosition().x, camera.getPosition().y, camera.getPosition().z);
        camera.getPosition().lerp(posX, cameraLerpSpeed);
        //System.out.println((int)camera.getPosition().x + (int)getPosition().x < -lerpDistance);
//        if((int)camera.getPosition().x + (int)getPosition().x > lerpDistance && !((int)camera.getPosition().x + (int)getPosition().x < (float)lerpDistance / 2)) {
//            camera.queueForce(new Vector3f(-0.01f, 0f, 0f));
//            return;
//        }
//        else if((int)camera.getPosition().x + (int)getPosition().x < -lerpDistance && !((int)camera.getPosition().x + (int)getPosition().x > -(float)lerpDistance / 2)) {
//            camera.queueForce(new Vector3f(0.01f, 0f, 0f));
//            return;
//        }
        //camera.getPosition().set(new Vector3f(0, camera.getPosition().y, camera.getPosition().z));
    }

}
