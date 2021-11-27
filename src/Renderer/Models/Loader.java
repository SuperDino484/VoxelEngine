package Renderer.Models;

import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryUtil.memFree;

public class Loader {

    private static ArrayList<Integer> vaos = new ArrayList<>();
    private static ArrayList<Integer> vbos = new ArrayList<>();

    public static Model createModel(float[] vertices, int vertexSize, int[] indices) {
        // Create and bind the vertex array
        int vaoID = glGenVertexArrays();
        vaos.add(vaoID);
        glBindVertexArray(vaoID);
        storeFloatDataInAttribute(0, vertices, vertexSize);
        int iboID = storeIntDataInIndices(indices);
        return new Model(new Mesh(vertices, vertexSize, indices, iboID), vaoID);
    }

    private static void storeFloatDataInAttribute(int slot, float[] vertices, int vertexSize) {
        // Create and bind the vertex buffer object
        int vboID = glGenBuffers();
        vbos.add(vboID);
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        try (MemoryStack stack = MemoryStack.stackPush()) {
            // Get the float buffer from the heap
            FloatBuffer buffer = stack.mallocFloat(vertices.length);
            // Input data into the float buffer
            buffer.put(vertices).flip();
            // Input data into the vbo
            glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
            // Store data in the attribute list
            glVertexAttribPointer(slot, vertexSize, GL_FLOAT, false, 0, 0);
            // Unbind the vbo
            glBindBuffer(GL_ARRAY_BUFFER, 0);
        }
    }

    private static int storeIntDataInIndices(int[] indices) {
        // Create and bind the index buffer object
        int iboID = glGenBuffers();
        vbos.add(iboID);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboID);
        try (MemoryStack stack = MemoryStack.stackPush()) {
            // Get the input buffer from the heap
            IntBuffer buffer = stack.mallocInt(indices.length);
            // Input data into int buffer
            buffer.put(indices).flip();
            // Store data as index buffer
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);
            // Free the memory
            // Unbind the vbo
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        }

        return iboID;
    }

    public static void cleanup() {
        for(int vbo : vbos) {
            glDeleteBuffers(vbo);
        }
        for(int vao : vaos) {
            glDeleteVertexArrays(vao);
        }
    }
}
