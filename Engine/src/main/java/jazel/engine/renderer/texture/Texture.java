package jazel.engine.renderer.texture;

import jazel.engine.renderer.utils.Utils;
import lombok.Getter;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public abstract class Texture {

    @Getter protected int width;
    @Getter protected int height;

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
}
