package jazel.engine.core;

import org.apache.commons.lang3.SystemUtils;

public class Core {

    private static boolean isDebug = false;

    public static void checkEnvironment() {
        isDebug = true; // TODO: maven profiles to toggle this

        if (!SystemUtils.IS_OS_WINDOWS) {
            Log.getCoreLogger().error("Currently is only Windows supported!");
            throw new RuntimeException();
        }
    }

    public static void assertion(boolean assertion, String message) {
        if (assertion && isDebug) {
            Log.getCoreLogger().error(message);
            throw new RuntimeException();
        } else if (assertion) {
            Log.getCoreLogger().error(message);
        }
    }

}
