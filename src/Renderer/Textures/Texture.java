package Renderer.Textures;

import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.stb.STBImage.*;

public class Texture {

    private static ArrayList<Integer> textureIDS = new ArrayList<>();

    private int textureID;
    private String path;
    private int textureSlot;

    public Texture(String path, int textureSlot) {
        this.path = path;
        this.textureSlot = textureSlot;
        // Create the texture
        this.textureID = glGenTextures();
        textureIDS.add(textureID);
        try(MemoryStack stack = MemoryStack.stackPush()) {
            stbi_set_flip_vertically_on_load(true);
            // Get texture data
            IntBuffer width = stack.mallocInt(1);
            IntBuffer height = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);
            ByteBuffer image = stbi_load(path, width, height, channels, 4);

            // Bind texture
            glBindTexture(GL_TEXTURE_2D, textureID);
            // Tell how to unpack rgba
            glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
            // Setup filtering data (GL_NEAREST will make it pixelated)
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
            // Upload texture data
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width.get(0), height.get(0),
                    0, GL_RGBA, GL_UNSIGNED_BYTE, image);
            glGenerateMipmap(GL_TEXTURE_2D);


            assert image != null;
            stbi_image_free(image);
        }
    }

    public void bind() {
        glActiveTexture(GL_TEXTURE0 + textureSlot);
        glBindTexture(GL_TEXTURE_2D, textureID);
    }

    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public static void cleanup() {
        for(int textureID : textureIDS) {
            glDeleteTextures(textureID);
        }
    }

    public int getTextureID() {
        return textureID;
    }

    public String getPath() {
        return path;
    }
}
