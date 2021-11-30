package Entities.Living;

import Core.Keyboard;
import Entities.LivingEntity;
import Renderer.Camera.Camera;
import Renderer.Models.TexturedModel;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class Player extends LivingEntity {

    private Camera camera;
    private final int lerpDistance = 1;

    public Player(Camera camera, int health, int maxHealth, Vector3f position, Vector3f rotation, Vector3f scale, TexturedModel model) {
        super(health, maxHealth, position, rotation, scale, model);
        this.camera = camera;
    }

    public void tick() {
        lerpCamera();
    }

    public void input() {
        if(Keyboard.isKeyPressed(GLFW_KEY_A)) {
            getPosition().add(new Vector3f(-0.01f, 0f, 0f));
        }
        if(Keyboard.isKeyPressed(GLFW_KEY_D)) {
            getPosition().add(new Vector3f(0.01f, 0f, 0f));
        }
    }

    private void lerpCamera() {
        //System.out.println((int)camera.getPosition().x + (int)getPosition().x < -lerpDistance);
        if((int)camera.getPosition().x + (int)getPosition().x > lerpDistance && !((int)camera.getPosition().x + (int)getPosition().x < (float)lerpDistance / 2)) {
            camera.queueForce(new Vector3f(-0.01f, 0f, 0f));
        }
        else if((int)camera.getPosition().x + (int)getPosition().x < -lerpDistance && !((int)camera.getPosition().x + (int)getPosition().x > -(float)lerpDistance / 2)) {
            camera.queueForce(new Vector3f(0.01f, 0f, 0f));
        }
        //camera.getPosition().set(new Vector3f(0, camera.getPosition().y, camera.getPosition().z));
    }

}
