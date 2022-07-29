package jazel.core;

public class Application {

    private static Application instance;

    private volatile boolean running;

    public Application() {
        assert instance != null : "Application already exists";
        instance = this;

        running = true;
    }

    public void run() {
        while (running) {

        }
    }

    public void onEvent() {}

    public static Application getInstance() {
        return instance;
    }
}
