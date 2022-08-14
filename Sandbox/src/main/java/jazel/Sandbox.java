package jazel;

import jazel.engine.core.Application;
import jazel.engine.core.EntryPoint;

public class Sandbox extends Application {

    public Sandbox() {
        pushLayer(new SandboxLayer());
    }

    public static void main(String[] args) {
        EntryPoint.start(new Sandbox());
    }
}
