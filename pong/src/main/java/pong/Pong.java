package pong;

import jazel.engine.core.Application;
import jazel.engine.core.EntryPoint;
import jazel.engine.core.window.WindowProps;

public class Pong extends Application {

    public static float WIDTH = 960.0f, HEIGHT = 720.0f;
    public Pong() {
        super(new WindowProps("Pong", (int) Pong.WIDTH, (int) Pong.HEIGHT));
        pushLayer(new GameLayer("Game"));
    }

    public static void main(String[] args) {
        EntryPoint.start(new Pong());
    }
}
