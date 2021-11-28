package Core;

import Renderer.Camera.Camera;
import Renderer.RendererTypes.MasterRenderer;
import Renderer.Models.Loader;
import Renderer.Models.Model;
import Renderer.Shaders.ShaderProgram;
import Renderer.Shaders.ShaderType;
import Renderer.Display.Window;
import Utils.Maths;
import Utils.Time;
import org.joml.Vector3f;

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
        this.camera = new Camera(fov, aspectRatio, zNear, zFar, new Vector3f(0, -0.5f, -2.5f), new Vector3f(10, 0, 0), new Vector3f(1, 1, 1));
        this.masterRenderer = new MasterRenderer();
    }

    public void start() {
        running = true;
        loop();
    }

    private void tick() {
        //camera.getRotation().add(new Vector3f(10f, 0, 0));
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
                // Front
                -0.5f, -0.5f, 0.0f,
                -0.5f,  0.5f, 0.0f,
                 0.5f,  0.5f, 0.0f,
                 0.5f, -0.5f, 0.0f,
                // Top
                -0.5f,  0.5f,  0.0f,
                -0.5f,  0.5f, -1.0f,
                 0.5f,  0.5f, -1.0f,
                 0.5f,  0.5f,  0.0f,

        };

        int[] indices = {
                // Front
                0, 1, 2,
                2, 3, 0,
                // Top
                4, 5, 6,
                6, 7, 4
        };

        float[] colors = {
            // Front
            0.5f, 0.8f, 0.3f, 1.0f,
            0.5f, 0.8f, 0.3f, 1.0f,
            0.5f, 0.8f, 0.3f, 1.0f,
            0.5f, 0.8f, 0.3f, 1.0f,
            // Top
            0.2f, 0.8f, 0.3f, 1.0f,
            0.2f, 0.8f, 0.3f, 1.0f,
            0.2f, 0.8f, 0.3f, 1.0f,
            0.2f, 0.8f, 0.3f, 1.0f
        };

        Model quad = Loader.createModel(positions, 3, indices, colors);

        // Testing shader
        ShaderProgram shaderProgram = ShaderProgram.createShaderCluster();
        ShaderType vertex = ShaderType.createShaderType(GL_VERTEX_SHADER, "resources/shaders/default/vertex_shader.txt");
        ShaderType fragment = ShaderType.createShaderType(GL_FRAGMENT_SHADER, "resources/shaders/default/fragment_shader.txt");
        vertex.compileSource();
        fragment.compileSource();
        shaderProgram.addShaderType(vertex);
        shaderProgram.addShaderType(fragment);
        shaderProgram.linkAndValidateShaders();
        shaderProgram.bind();
        shaderProgram.storeUniformLocation("projectionMatrix");
        shaderProgram.storeUniformLocation("transformationMatrix");

        shaderProgram.setUniformMat4("projectionMatrix", camera.getProjectionMatrix());

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
            Maths.setTransformationMatrix(camera.getTransformationMatrix(), camera.getPosition(), camera.getRotation(), camera.getScale());
            shaderProgram.setUniformMat4("transformationMatrix", camera.getTransformationMatrix());
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, quad.getMesh().getIboID());
            glEnableVertexAttribArray(0);
            glEnableVertexAttribArray(1);
            glDrawElements(GL_TRIANGLES, quad.getMesh().getIndices().length, GL_UNSIGNED_INT, 0);
            glDisableVertexAttribArray(0);
            glDisableVertexAttribArray(1);

            // Update the window and poll events
            window.update();
        }

        shaderProgram.cleanup();
    }

}
