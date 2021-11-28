package Entities;

import Renderer.Models.Model;
import org.joml.Vector3f;

public class Entity {

    private Vector3f position;
    private Vector3f rotation;
    private Vector3f scale;
    private Model model;

    public Entity(Vector3f position, Vector3f rotation, Vector3f scale, Model model) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.model = model;
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

    public Model getModel() {
        return model;
    }
}
