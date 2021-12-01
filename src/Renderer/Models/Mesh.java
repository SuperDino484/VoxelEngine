package Renderer.Models;

public class Mesh {

    private float[] vertices;
    private int[] indices;
    private float[] textureCoords;

    public Mesh(float[] vertices, int[] indices, float[] textureCoords) {
        this.vertices = vertices;
        this.indices = indices;
        this.textureCoords = textureCoords;
    }

    public float[] getVertices() {
        return vertices;
    }

    public int[] getIndices() {
        return indices;
    }

    public float[] getTextureCoords() {
        return textureCoords;
    }
}
