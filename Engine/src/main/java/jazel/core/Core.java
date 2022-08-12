package jazel.core;

public class Core {
    public static void assertion(boolean assertion, String message) {
        if (assertion) {
            Log.getCoreLogger().error(message);
            throw new RuntimeException();
        }
    }
}
