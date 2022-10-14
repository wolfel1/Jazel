package jazel.engine.renderer.renderer;

import jazel.engine.core.Log;
import jazel.engine.renderer.container.VertexArray;
import jazel.engine.renderer.enumeration.API;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector4f;
import jazel.platform.opengl.OpenGLRendererAPI;
import org.joml.Vector4i;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

public abstract class RendererAPI {

    private static API api = API.OPENGL;

    public abstract void init();

    public abstract void setViewport(int x, int y, int width, int height);
    public abstract Vector4i getViewport();

    public abstract void setClearColor(Vector4f color);

    public abstract void clear();

    public abstract void drawIndexed(VertexArray vertexArray, int vertexBuffer);
    public void renderImage(String name) {
        try {
            var viewport = getViewport();
            int width = viewport.z;
            int height = viewport.w;

            BufferedImage screenshot = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics graphics = screenshot.getGraphics();

            ByteBuffer buffer = ByteBuffer.allocateDirect(width * height * 4);

            readPixels(buffer, width, height);

            for (int h = 0; h < height; h++) {
                for (int w = 0; w < width; w++) {
                    graphics.setColor(new Color((buffer.get() & 0xff), (buffer.get() & 0xff),
                            (buffer.get() & 0xff)));
                    buffer.get();
                    graphics.drawRect(w, height - h, 1, 1);
                }
            }
            buffer.clear();

            var directory = Utils.getPath("output");
            File outfile = new File(directory + "/" + name +".png");
            ImageIO.write(screenshot, "png", outfile);
        } catch (IOException ex) {
            Log.getCoreLogger().error(String.valueOf(ex));
        }
    }

    protected abstract void readPixels(ByteBuffer buffer, int width, int height);

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
