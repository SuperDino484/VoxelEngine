package Renderer.Shaders;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static org.lwjgl.opengl.GL20.*;

public class ShaderType {

    private int shaderID;
    private int type;
    private String filePath;

    private ShaderType(int type, String filePath) {
        this.type = type;
        this.filePath = filePath;
    }

    public static ShaderType createShaderType(int type, String filePath) {
        return new ShaderType(type, filePath);
    }

    public void compileSource() {
        // Take the shader and read it and compile it
        StringBuilder source = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            String line;
            while((line = bufferedReader.readLine()) != null) {
                source.append(line).append("\n");
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        shaderID = glCreateShader(type);
        glShaderSource(shaderID, source);
        glCompileShader(shaderID);

        // Error check the shader
        if(glGetShaderi(shaderID, GL_COMPILE_STATUS) == GL_FALSE) {
            int size = glGetShaderi(shaderID, GL_INFO_LOG_LENGTH);
            System.err.println("Could not compile shader (" + filePath + ") " + glGetShaderInfoLog(shaderID, size));
        }
    }

    public void cleanup() {
        glDeleteShader(shaderID);
    }

    public int getShaderID() {
        return shaderID;
    }
}
