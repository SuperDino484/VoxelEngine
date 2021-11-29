package Renderer.Models.Data;

public class Cube {

    private float[] vertices;
    private int[] indices;
    private float[] texCoords;

    public Cube() {
        vertices = new float[] {
                // Front face
                -0.5f, -0.5f,  0.5f,
                -0.5f,  0.5f,  0.5f,
                 0.5f,  0.5f,  0.5f,
                 0.5f, -0.5f,  0.5f,
                // Top face
                -0.5f,  0.5f,  0.5f,
                -0.5f,  0.5f, -0.5f,
                 0.5f,  0.5f, -0.5f,
                 0.5f,  0.5f,  0.5f,
                // Left face
                -0.5f, -0.5f, -0.5f,
                -0.5f,  0.5f, -0.5f,
                -0.5f,  0.5f,  0.5f,
                -0.5f, -0.5f,  0.5f,
                // Right face
                 0.5f, -0.5f, -0.5f,
                 0.5f,  0.5f, -0.5f,
                 0.5f,  0.5f,  0.5f,
                 0.5f, -0.5f,  0.5f,
                // Bottom face
                -0.5f, -0.5f,  0.5f,
                -0.5f, -0.5f, -0.5f,
                 0.5f, -0.5f, -0.5f,
                 0.5f, -0.5f,  0.5f,
                // Back face
                -0.5f, -0.5f, -0.5f,
                -0.5f,  0.5f, -0.5f,
                 0.5f,  0.5f, -0.5f,
                 0.5f, -0.5f, -0.5f,
        };

        indices = new int[]{
                // Front face
                0, 1, 2, 2, 3, 0,
                // Top face
                4, 5, 6, 6, 7, 4,
                // Left face
                8, 9, 10, 10, 11, 8,
                // Right face
                12, 13, 14, 14, 15, 12,
                // Bottom face
                16, 17, 18, 18, 19, 16,
                // Back face
                20, 21, 22, 22, 23, 20,
        };

        texCoords = new float[]{
                // Front face
                0.0f, 0.5f,
                0.0f, 1.0f,
                0.5f, 1.0f,
                0.5f, 0.5f,
                // Top face
                0.0f, 0.0f,
                0.0f, 0.5f,
                0.5f, 0.5f,
                0.5f, 0.0f,
                // Left face
                0.0f, 0.5f,
                0.0f, 1.0f,
                0.5f, 1.0f,
                0.5f, 0.5f,
                // Right face
                0.0f, 0.5f,
                0.0f, 1.0f,
                0.5f, 1.0f,
                0.5f, 0.5f,
                // Bottom face
                0.5f, 0.5f,
                0.5f, 1.0f,
                1.0f, 1.0f,
                1.0f, 0.5f,
                // Back face
                0.0f, 0.5f,
                0.0f, 1.0f,
                0.5f, 1.0f,
                0.5f, 0.5f,
        };
    }

    public float[] getVertices() {
        return vertices;
    }

    public int[] getIndices() {
        return indices;
    }

    public float[] getTexCoords() {
        return texCoords;
    }
}
