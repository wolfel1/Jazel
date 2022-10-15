package jazel.engine.renderer.utils;

import jazel.engine.core.Core;
import jazel.engine.core.Log;

import java.io.File;

public final class Utils {

    public static String getPath(String folder) {
        var current = Thread.currentThread().getContextClassLoader();
        var resource = current.getResource(folder);
        if (resource == null) {
            var resourceDirectory = current.getResource(".");
            new File(resourceDirectory.getPath() + "/" + folder).mkdir();
            resource = current.getResource(folder);
        }
        Core.assertion(resource == null, "Directory does not exist!");
        return resource.getPath();
    }
}
