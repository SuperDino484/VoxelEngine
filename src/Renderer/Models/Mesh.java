package Renderer.Models;

public class Mesh {

    private float[] vertices;
    private int vertexSize;

    private int[] indices;
    private int iboID;

    public Mesh(float[] vertices, int vertexSize, int[] indices, int iboID) {
        this.vertices = vertices;
        this.vertexSize = vertexSize;
        this.indices = indices;
        this.iboID = iboID;
    }

    public float[] getVertices() {
        return vertices;
    }

    public int getVertexSize() {
        return vertexSize;
    }

    public int[] getIndices() {
        return indices;
    }

    public int getIboID() {
        return iboID;
    }
}
