package Entities;

import Renderer.Models.TexturedModel;
import org.joml.Vector3f;

public class LivingEntity extends Entity {

    private int health;
    private int maxHealth;

    public LivingEntity(int health, int maxHealth, Vector3f position, Vector3f rotation, Vector3f scale, TexturedModel model) {
        super(position, rotation, scale, model);
        this.health = health;
        this.maxHealth = maxHealth;
    }
}
