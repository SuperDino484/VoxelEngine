package Renderer.RendererTypes;

import Renderer.Camera.Camera;

public interface IRenderer {

    void render(Camera camera);

    void cleanup();

}
