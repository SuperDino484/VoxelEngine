package Renderer.Models;

import Renderer.Textures.Texture;

public class TexturedModel {

    private Model model;
    private Texture texture;
    private float[] textureCoords;

    public TexturedModel(Model model, Texture texture, float[] textureCoords) {
        this.model = model;
        this.texture = texture;
        this.textureCoords = textureCoords;
    }

    public Model getModel() {
        return model;
    }

    public Texture getTexture() {
        return texture;
    }

    public float[] getTextureCoords() {
        return textureCoords;
    }
}
