package jazel;

import jazel.engine.core.Application;
import jazel.engine.core.EntryPoint;
import jazel.engine.core.window.WindowProps;

public class Sandbox extends Application {

    public Sandbox() {
        super(new WindowProps("Jazel", 1920, 1080));
        pushLayer(new SandboxLayer());
    }

    public static void main(String[] args) {
        EntryPoint.start(new Sandbox());
    }
}
