package Renderer.Models;

public class Mesh {

    private float[] vertices;

    private int[] indices;
    private int iboID;

    public Mesh(float[] vertices, int[] indices, int iboID) {
        this.vertices = vertices;
        this.indices = indices;
        this.iboID = iboID;
    }

    public float[] getVertices() {
        return vertices;
    }

    public int[] getIndices() {
        return indices;
    }

    public int getIboID() {
        return iboID;
    }
}
