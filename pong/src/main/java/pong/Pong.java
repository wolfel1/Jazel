package pong;

import jazel.engine.core.Application;
import jazel.engine.core.EntryPoint;
import jazel.engine.core.window.WindowProps;

public class Pong extends Application {

    public Pong() {
        super(new WindowProps("Pong", 960, 720));
        //pushLayer(new SandboxLayer());
    }

    public static void main(String[] args) {
        EntryPoint.start(new Pong());
    }
}
