package jazel;

import jazel.engine.core.Application;
import jazel.engine.core.EntryPoint;
import jazel.engine.core.window.WindowProps;

public class Sandbox extends Application {

    public Sandbox() {
        super(new WindowProps("Jazel", 960, 720));
        pushLayer(new SandboxLayer());
    }

    public static void main(String[] args) {
        EntryPoint.start(new Sandbox());
    }
}
