package Renderer.RendererTypes;

import Entities.Entity;
import Renderer.Camera.Camera;
import Renderer.Models.TexturedModel;
import Renderer.Shaders.ShaderProgram;
import Renderer.Shaders.ShaderType;
import Utils.Maths;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class EntityRenderer implements IRenderer {

    private HashMap<TexturedModel, List<Entity>> entities;
    private ShaderProgram entityShader;
    private ShaderType vertexShader;
    private ShaderType fragmentShader;

    public EntityRenderer(Camera camera) {
        this.entities = new HashMap<>();
        entityShader = ShaderProgram.createShaderCluster();
        vertexShader = ShaderType.createShaderType(GL_VERTEX_SHADER, "resources/shaders/entityShader/vertex_shader.txt");
        fragmentShader = ShaderType.createShaderType(GL_FRAGMENT_SHADER, "resources/shaders/entityShader/fragment_shader.txt");
        vertexShader.compileSource();
        fragmentShader.compileSource();
        entityShader.addShaderType(vertexShader);
        entityShader.addShaderType(fragmentShader);
        entityShader.linkAndValidateShaders();
        entityShader.bind();
        entityShader.storeUniformLocation("projectionMatrix");
        entityShader.storeUniformLocation("viewMatrix");
        entityShader.storeUniformLocation("transformationMatrix");
        entityShader.storeUniformLocation("texSampler");
        entityShader.setUniform1i("texSampler", 0);
        entityShader.setUniformMat4("projectionMatrix", camera.getProjectionMatrix());
    }

    public void processEntity(Entity entity) {
        List<Entity> batch = entities.get(entity.getTexturedModel());
        if(batch != null) {
            batch.add(entity);
        } else {
            List<Entity> newBatch = new ArrayList<>();
            newBatch.add(entity);
            entities.put(entity.getTexturedModel(), newBatch);
        }
    }

    @Override
    public void render(Camera camera) {
        entityShader.bind();
        Maths.setViewMatrix(camera);
        entityShader.setUniformMat4("viewMatrix", camera.getViewMatrix());
        for(TexturedModel texturedModel : entities.keySet()) {
            List<Entity> batch = entities.get(texturedModel);
            glBindVertexArray(texturedModel.getModel().getVaoID());
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, texturedModel.getModel().getIboID());
            glEnableVertexAttribArray(0);
            glEnableVertexAttribArray(1);
            texturedModel.getTexture().bind();
            for(Entity entity : batch) {
                Matrix4f transformationMatrix = Maths.getTransformationMatrix(entity.getPosition(), entity.getRotation(), entity.getScale());
                entityShader.setUniformMat4("transformationMatrix", transformationMatrix);
                glDrawElements(GL_TRIANGLES, entity.getTexturedModel().getModel().getMesh().getIndices().length, GL_UNSIGNED_INT, 0);
            }
            texturedModel.getTexture().unbind();
            glDisableVertexAttribArray(0);
            glDisableVertexAttribArray(1);
            glBindVertexArray(0);
        }
        entityShader.unbind();
        entities.clear();
    }

    @Override
    public void cleanup() {
        entityShader.cleanup();
    }
}
