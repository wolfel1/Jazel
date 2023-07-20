package jazel.engine.renderer.texture;

import jazel.engine.core.Log;
import jazel.engine.renderer.renderer.RendererAPI;
import jazel.engine.renderer.utils.Utils;
import jazel.platform.opengl.texture.OpenGLTexture2D;
import lombok.Getter;

public abstract class Texture {

    @Getter
    protected int width;
    @Getter
    protected int height;

    protected int rendererID;

    protected int internalFormat;
    protected int dataFormat;

    protected String path;

    public Texture(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public Texture(String path) {
        this.path = Utils.getPath("assets/textures") + "/" + path;
    }

    public abstract void setData(int[] data);

    public abstract void bind(int slot);

    public abstract void destroy();

    public static Texture create(int width, int height) {
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

    public static Texture create(String textureFile) {
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
