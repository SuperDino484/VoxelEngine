package Core;

import Renderer.Camera.Camera;
import Renderer.MasterRenderer;
import Renderer.Models.Loader;
import Renderer.Models.Model;
import Renderer.Shaders.ShaderProgram;
import Renderer.Shaders.ShaderType;
import Renderer.Window;
import Utils.Time;

import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

public class Engine {

    // Window variables
    private Window window;
    private boolean running = false;

    // Camera variables
    private Camera camera;
    private float fov;
    private float zNear = 0.01f;
    private float zFar = 1000.0f;
    private float aspectRatio;

    // Renderer variables
    private MasterRenderer masterRenderer;

    public Engine() {
        this.window = Window.createWindow("Voxel Engine", 1920, 1080);

        this.fov = (float) Math.toRadians(60.0f);
        this.zNear = 0.01f;
        this.zFar = 1000.0f;
        this.aspectRatio = (float) window.getWidth() / window.getHeight();
        this.camera = new Camera(fov, zNear, zFar, aspectRatio);
        this.masterRenderer = new MasterRenderer();
    }

    public void start() {
        running = true;
        loop();
    }

    private void tick() {

    }

    private void render() {
        glClear(GL_COLOR_BUFFER_BIT);
        glClearColor(0.3f, 0.5f, 0.8f, 1.0f);
    }

    private void input() {

    }

    public void cleanup() {

        // This needs to be last to protect crashes
        window.cleanup();
    }

    private void loop() {
        // Variables to help calculate tick and delta time
        double steps = 0;
        double time = Time.getTimeSeconds();
        double lastTime = time;

        // Variables to control the tick speed
        final int TPS = 60;
        final double TPS_INTERVAL = 1f / TPS;

        float[] positions = {
                -0.5f, -0.5f, -1.0f,
                -0.5f,  0.5f, -1.0f,
                 0.5f,  0.5f, -1.0f,
                 0.5f, -0.5f, -1.0f
        };

        int[] indices = {
                0, 1, 2,
                2, 3, 0
        };

        Model quad = Loader.createModel(positions, 3, indices);

        // Testing shader
        ShaderProgram shaderCluster = ShaderProgram.createShaderCluster();
        ShaderType vertex = ShaderType.createShaderType(GL_VERTEX_SHADER, "resources/shaders/default/vertex_shader.txt");
        ShaderType fragment = ShaderType.createShaderType(GL_FRAGMENT_SHADER, "resources/shaders/default/fragment_shader.txt");
        vertex.compileSource();
        fragment.compileSource();
        shaderCluster.addShaderType(vertex);
        shaderCluster.addShaderType(fragment);
        shaderCluster.linkAndValidateShaders();
        shaderCluster.bind();
        shaderCluster.storeUniformLocation("projectionMatrix");

        shaderCluster.setUniformMat4("projectionMatrix", camera.getProjectionMatrix());

        while(!glfwWindowShouldClose(window.getWindowHandle())) {
            // Calculate delta time and steps
            time = Time.getTimeSeconds();
            steps += Time.deltaTime = time - lastTime;
            lastTime = time;

            // Gather user input before the tick method
            input();

            // Run the tick method for the amount of time it is behind the game
            while(steps >= TPS_INTERVAL) {
                steps -= TPS_INTERVAL;
                tick();
            }

            // Render the game stuff
            render();
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, quad.getMesh().getIboID());
            glEnableVertexAttribArray(0);
            glDrawElements(GL_TRIANGLES, quad.getMesh().getIndices().length, GL_UNSIGNED_INT, 0);
            glDisableVertexAttribArray(0);

            // Update the window and poll events
            window.update();
        }

        shaderCluster.cleanup();
    }

}
