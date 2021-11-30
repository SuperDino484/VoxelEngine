package Entities;

import Renderer.Models.TexturedModel;
import org.joml.Vector3f;

public class Entity {

    private Vector3f position;
    private Vector3f rotation;
    private Vector3f scale;
    private TexturedModel texturedModel;

    public Entity(Vector3f position, Vector3f rotation, Vector3f scale, TexturedModel model) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.texturedModel = model;
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

    public TexturedModel getTexturedModel() {
        return texturedModel;
    }
}
