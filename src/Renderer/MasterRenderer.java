package Renderer;

import Renderer.Camera.Camera;

import java.util.ArrayList;

public class MasterRenderer {

    private IRenderer livingEntityRenderer;
    private IRenderer entityRenderer;
    private IRenderer terrainRenderer;

    public MasterRenderer() {
        livingEntityRenderer = new LivingEntityRenderer();
        entityRenderer = new EntityRenderer();
        terrainRenderer = new TerrainRenderer();
    }

    public void render(Camera camera) {
        livingEntityRenderer.render(camera);
        entityRenderer.render(camera);
        terrainRenderer.render(camera);
    }

    public void cleanup() {
        livingEntityRenderer.cleanup();
        entityRenderer.cleanup();
        terrainRenderer.cleanup();
    }

}
