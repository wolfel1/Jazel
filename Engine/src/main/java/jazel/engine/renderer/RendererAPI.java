package jazel.engine.renderer;

import jazel.engine.core.Log;
import jazel.engine.renderer.enumeration.API;
import org.joml.Vector4f;
import jazel.platform.opengl.OpenGLRendererAPI;

public abstract class RendererAPI {

    private static API api = API.OPENGL;

    public abstract void init();
    public abstract void setViewport(int x, int y, int width, int height);
    public abstract void setClearColor(Vector4f color);
    public abstract void clear();
    public abstract void drawIndexed();

    public static API getAPI() {
        return api;
    }

    public static void setApi(API api) {
        RendererAPI.api = api;
    }

    public static RendererAPI create() {
        switch (api) {
        case NONE:
            Log.getCoreLogger().error("RendererAPI NONE is not supported!");
        case OPENGL:
            return new OpenGLRendererAPI();
        }
        Log.getCoreLogger().error("Unknown RendererAPI!");

        return null;
    }
}
