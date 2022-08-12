import jazel.core.Application;
import jazel.core.EntryPoint;

public class Sandbox extends Application {

    public Sandbox() {
        pushLayer(new SandboxLayer());
    }

    public static void main(String[] args) {
        EntryPoint.start(new Sandbox());
    }
}
