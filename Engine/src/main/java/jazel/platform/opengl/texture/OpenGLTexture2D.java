package jazel.platform.opengl.texture;

import jazel.engine.core.Core;
import jazel.engine.renderer.texture.Texture2D;

import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL45.*;
import static org.lwjgl.stb.STBImage.*;

public class OpenGLTexture2D extends Texture2D {

    public OpenGLTexture2D(int width, int height) {
        super(width, height);

        internalFormat = GL_RGBA8;
        dataFormat = GL_RGBA;

        rendererID = glCreateTextures(GL_TEXTURE_2D);
        glTextureStorage2D(rendererID, 1, internalFormat, width, height);

        setTextureParameters();
    }



    public OpenGLTexture2D(String textureFile) {
        super(textureFile);

        var width= IntBuffer.allocate(1);
        var height= IntBuffer.allocate(1);
        var channels = IntBuffer.allocate(1);
        stbi_set_flip_vertically_on_load(true);

        var data = stbi_load(textureFile, width, height, channels, 0);
        Core.assertion(data == null, "Failed to load image!");
        this.width = width.get();
        this.height = height.get();

        switch (channels.get()) {
            case 3 -> {
                this.internalFormat = GL_RGB8;
                this.dataFormat = GL_RGB;
            }
            case 4 -> {
                this.internalFormat = GL_RGBA8;
                this.dataFormat = GL_RGBA;
            }
        }
        Core.assertion(this.internalFormat == 0 || this.dataFormat == 0, "Format not supported");

        rendererID = glCreateTextures(GL_TEXTURE_2D);
        glTextureStorage2D(rendererID, 1, this.internalFormat, this.width, this.height);

        setTextureParameters();

        assert data != null;
        setData(data.asIntBuffer().array());

        stbi_image_free(data);
    }

    private void setTextureParameters() {
        glTextureParameteri(rendererID, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTextureParameteri(rendererID, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glTextureParameteri(rendererID, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTextureParameteri(rendererID, GL_TEXTURE_WRAP_T, GL_REPEAT);
    }

    @Override
    public void setData(int[] data) {
        glTextureSubImage2D(rendererID, 0, 0, 0, this.width, this.height, dataFormat, GL_UNSIGNED_BYTE, data);
    }

    @Override
    public void bind(int slot) {
        glBindTextureUnit(slot, rendererID);
    }

    @Override
    public void destroy() {
        glDeleteTextures(rendererID);
    }
}
