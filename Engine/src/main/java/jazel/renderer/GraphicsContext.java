package jazel.renderer;

import jazel.core.Log;
import platform.opengl.OpenGLContext;

public interface GraphicsContext {

    void init();

    void swapBuffers();

    static GraphicsContext create(long window) {
        switch (RendererAPI.getAPI()) {
        case NONE:
            Log.getCoreLogger().error("RendererAPI NONE is not supported!");
        case OPENGL:
            return new OpenGLContext(window);
        }
        Log.getCoreLogger().error("Unknown RendererAPI!");

        return null;
    };
}
