package jazel.core;

public class EntryPoint {

    public static void start(Application application) {
        Log.init();
        Log.getCoreLogger().info("Starting...");

        application.run();
    }
}
