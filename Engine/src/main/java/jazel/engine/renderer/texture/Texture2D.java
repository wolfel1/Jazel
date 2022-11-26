package jazel.engine.renderer.texture;

import jazel.engine.core.Log;
import jazel.engine.renderer.renderer.RendererAPI;
import jazel.platform.opengl.shader.OpenGLShader;
import jazel.platform.opengl.texture.OpenGLTexture2D;

public abstract class Texture2D extends Texture {

    public Texture2D(int width, int height) {
        super(width, height);
    }

    public Texture2D(String path) {
        super(path);
    }

    public static Texture2D create(int width, int height) {
        switch (RendererAPI.getAPI()) {
        case NONE -> {
            Log.getCoreLogger().error("RendererAPI NONE is not supported!");
            return null;
        }
        case OPENGL -> {
            return new OpenGLTexture2D(width, height);
        }
    }

        Log.getCoreLogger().error("Unknown RendererAPI!");
        return null;
    }

    public static Texture2D create(String textureFile) {
        switch (RendererAPI.getAPI()) {
            case NONE -> {
                Log.getCoreLogger().error("RendererAPI NONE is not supported!");
                return null;
            }
            case OPENGL -> {
                return new OpenGLTexture2D(textureFile);
            }
        }

        Log.getCoreLogger().error("Unknown RendererAPI!");
        return null;
    }
}
