import Core.Engine;
import Renderer.Window;
import org.lwjgl.Version;
import org.lwjgl.opengl.GPU_DEVICE;

public class RunApplication {

    public static void main(String[] args) {
        System.out.println("LWJGL Version: " + Version.getVersion());
        Engine engine = new Engine();
        engine.start();
        engine.cleanup();
    }

}
