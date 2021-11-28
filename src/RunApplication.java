import Core.Engine;
import org.lwjgl.Version;

public class RunApplication {

    public static void main(String[] args) {
        System.out.println("LWJGL Version: " + Version.getVersion());
        Engine engine = new Engine();
        engine.start();
        engine.cleanup();
    }

}
