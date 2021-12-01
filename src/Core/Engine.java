package Core;

import Entities.Entity;
import Entities.Living.Player;
import Entities.LivingEntity;
import Renderer.Camera.Camera;
import Renderer.Models.TexturedModel;
import Renderer.RendererTypes.MasterRenderer;
import Renderer.Models.Loader;
import Renderer.Display.Window;
import Renderer.Textures.Texture;
import Utils.Maths;
import Utils.Time;
import org.joml.Vector3f;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.*;

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

    Player player;

    // Renderer variables
    private MasterRenderer masterRenderer;

    public Engine() {
        this.window = Window.createWindow("Voxel Engine", 1080, 720);

        this.fov = (float) Math.toRadians(90.0f);
        this.zNear = 0.01f;
        this.zFar = 1000.0f;
        this.aspectRatio = (float) window.getWidth() / window.getHeight();
        this.camera = new Camera(fov, aspectRatio, zNear, zFar, new Vector3f(-1f, -0.6f, -2.5f), new Vector3f(10, 0, 0), new Vector3f(1, 1, 1));
        this.masterRenderer = new MasterRenderer(camera);
    }

    public void start() {
        running = true;
        loop();
    }

    private void tick() {
        //camera.getRotation().add(new Vector3f(0, 2f, 1f));
        camera.tick();
        player.tick();
    }

    private void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glClearColor(0.3f, 0.5f, 0.8f, 1.0f);
        masterRenderer.render(camera);
    }

    private void input() {
//        if(Keyboard.isKeyPressed(GLFW_KEY_D)) {
//            camera.getPosition().add(new Vector3f(1f, 0f, 0f));
//        }
        //camera.input();
        player.input();
        if(Mouse.getScrollY() != 0) {
            System.out.println("SCROLL");
        }
    }

    public void cleanup() {
        Texture.cleanup();
        masterRenderer.cleanup();

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

        Texture palette = new Texture("resources/textures/ColorPalette.png", 0);
        TexturedModel grassBlock = Loader.loadModelFromFile("resources/GrassBlock.obj", palette);
        TexturedModel dirtBlock = Loader.loadModelFromFile("resources/DirtBlock.obj", palette);
        TexturedModel playerObj = Loader.loadModelFromFile("resources/Player.obj", palette);
        ArrayList<Entity> entities = new ArrayList<>();
        for(int i = 0; i < 100; i++) {
            entities.add(new Entity(new Vector3f(i, 0, 0), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1), grassBlock));
            for(int j = 1; j < 10; j++) {
                entities.add(new Entity(new Vector3f(i, -j, 0), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1), dirtBlock));
            }
        }

        player = new Player(camera, 100, 100, new Vector3f(0, 0.8f, 0), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1), playerObj);

        while(!glfwWindowShouldClose(window.getWindowHandle())) {
            // Calculate delta time and steps
            time = Time.getTimeSeconds();
            steps += Time.deltaTime = (float)(time - lastTime);
            lastTime = time;

            // Gather user input before the tick method
            input();

            // Run the tick method for the amount of time it is behind the game
            while(steps >= TPS_INTERVAL) {
                steps -= TPS_INTERVAL;
                tick();
            }

            for(Entity entity : entities) {
                masterRenderer.getEntityRenderer().processEntity(entity);
            }
            masterRenderer.getLivingEntityRenderer().processLivingEntity(player);

            // Render the game stuff
            render();

            // Update the window and poll events
            window.update();
            Mouse.endFrame();
        }

    }

}
