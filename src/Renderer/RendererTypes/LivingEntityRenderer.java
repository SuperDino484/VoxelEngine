package Renderer.RendererTypes;

import Entities.Entity;
import Entities.LivingEntity;
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
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class LivingEntityRenderer implements IRenderer {

    private HashMap<TexturedModel, List<LivingEntity>> livingEntities;
    private ShaderProgram livingEntityShader;
    private ShaderType vertexShader;
    private ShaderType fragmentShader;
    
    public LivingEntityRenderer(Camera camera) {
        this.livingEntities = new HashMap<>();
        livingEntityShader = ShaderProgram.createShaderCluster();
        vertexShader = ShaderType.createShaderType(GL_VERTEX_SHADER, "resources/shaders/entityShader/livingEntityShader/vertex_shader.txt");
        fragmentShader = ShaderType.createShaderType(GL_FRAGMENT_SHADER, "resources/shaders/entityShader/livingEntityShader/fragment_shader.txt");
        vertexShader.compileSource();
        fragmentShader.compileSource();
        livingEntityShader.addShaderType(vertexShader);
        livingEntityShader.addShaderType(fragmentShader);
        livingEntityShader.linkAndValidateShaders();
        livingEntityShader.bind();
        livingEntityShader.storeUniformLocation("projectionMatrix");
        livingEntityShader.storeUniformLocation("viewMatrix");
        livingEntityShader.storeUniformLocation("transformationMatrix");
        livingEntityShader.storeUniformLocation("texSampler");
        livingEntityShader.setUniform1i("texSampler", 0);
        livingEntityShader.setUniformMat4("projectionMatrix", camera.getProjectionMatrix());
    }

    public void processLivingEntity(LivingEntity livingEntity) {
        List<LivingEntity> batch = livingEntities.get(livingEntity.getTexturedModel());
        if(batch != null) {
            batch.add(livingEntity);
        } else {
            List<LivingEntity> newBatch = new ArrayList<>();
            newBatch.add(livingEntity);
            livingEntities.put(livingEntity.getTexturedModel(), newBatch);
        }
    }

    @Override
    public void render(Camera camera) {
        livingEntityShader.bind();
        Maths.setViewMatrix(camera);
        livingEntityShader.setUniformMat4("viewMatrix", camera.getViewMatrix());
        for(TexturedModel texturedModel : livingEntities.keySet()) {
            List<LivingEntity> batch = livingEntities.get(texturedModel);
            glBindVertexArray(texturedModel.getModel().getVaoID());
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, texturedModel.getModel().getIboID());
            glEnableVertexAttribArray(0);
            glEnableVertexAttribArray(1);
            texturedModel.getTexture().bind();
            for(Entity entity : batch) {
                Matrix4f transformationMatrix = Maths.getTransformationMatrix(entity.getPosition(), entity.getRotation(), entity.getScale());
                livingEntityShader.setUniformMat4("transformationMatrix", transformationMatrix);
                glDrawElements(GL_TRIANGLES, entity.getTexturedModel().getModel().getMesh().getIndices().length, GL_UNSIGNED_INT, 0);
            }
            texturedModel.getTexture().unbind();
            glDisableVertexAttribArray(0);
            glDisableVertexAttribArray(1);
            glBindVertexArray(0);
        }
        livingEntityShader.unbind();
        livingEntities.clear();
    }

    @Override
    public void cleanup() {
        livingEntityShader.cleanup();
    }
}
