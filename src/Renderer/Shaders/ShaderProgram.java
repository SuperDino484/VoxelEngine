package Renderer.Shaders;

import Renderer.Camera.Camera;
import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import static org.lwjgl.opengl.GL20.*;

public class ShaderProgram {

    private int programID;
    private ArrayList<ShaderType> shaders;
    private HashMap<String, Integer> uniforms;

    private ShaderProgram() {
        this.programID = glCreateProgram();
        this.shaders = new ArrayList<>();
        this.uniforms = new HashMap<>();
    }

    private int getUniformLocation(String name) {
        return uniforms.get(name);
    }

    public void storeUniformLocation(String name) {
        int uniformLocation = glGetUniformLocation(programID, name);
        if(uniformLocation < 0)
            throw new IllegalStateException("Could not find uniform: " + name);
        uniforms.put(name, uniformLocation);
    }

    public void setUniform1i(String uniformName, int value) {
        glUniform1i(getUniformLocation(uniformName), value);
    }

    public void setUniformMat4(String uniformName, Matrix4f matrix4f) {
        try(MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer fb = stack.mallocFloat(16);
            matrix4f.get(fb);
            glUniformMatrix4fv(getUniformLocation(uniformName), false, fb);
        }
    }

    public static ShaderProgram createShaderCluster() {
        return new ShaderProgram();
    }

    public void linkAndValidateShaders() {
        // Link the program and shaders and error check
        glLinkProgram(programID);
        if(glGetProgrami(programID, GL_LINK_STATUS) == GL_FALSE) {
            int size = glGetProgrami(programID, GL_INFO_LOG_LENGTH);
            System.err.println("Could not link program " + glGetProgramInfoLog(programID, size));
        }

        // Validate and error check
        glValidateProgram(programID);
        if(glGetProgrami(programID, GL_VALIDATE_STATUS) == GL_FALSE) {
            int size = glGetProgrami(programID, GL_INFO_LOG_LENGTH);
            System.err.println("Could not validate program " + glGetProgramInfoLog(programID, size));
        }

        // Detach the shaders after linked
        for(ShaderType shaderType : shaders) {
            glDetachShader(programID, shaderType.getShaderID());
        }

    }

    public void bind() {
        glUseProgram(programID);
    }

    public void unbind() {
        glUseProgram(0);
    }

    public void cleanup() {
        // unbind the shader before deleting
        unbind();
        // For every shader attached delete it
        for(ShaderType shaderType : shaders) {
            shaderType.cleanup();
        }
        // Delete the program
        glDeleteProgram(programID);
    }

    public void addShaderType(ShaderType shaderType) {
        // Attach shader to program
        glAttachShader(programID, shaderType.getShaderID());
        shaders.add(shaderType);
    }

    public int getProgramID() {
        return programID;
    }
}
