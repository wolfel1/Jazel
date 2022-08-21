package jazel.engine.renderer.renderer;

import jazel.engine.core.Core;
import jazel.engine.renderer.container.VertexArray;
import jazel.engine.renderer.container.VertexBuffer;
import org.joml.Vector4f;

public abstract class RenderCommand {

    private static RendererAPI rendererAPI;

    public static void init() {
        if (rendererAPI == null) {
            rendererAPI = RendererAPI.create();
        }
        Core.assertion(rendererAPI == null, "API is null");
        rendererAPI.init();
    }

    public static void setViewport(int x, int y, int width, int height) {
        rendererAPI.setViewport(x, y, width, height);
    }

    public static void setClearColor(Vector4f color) {
        rendererAPI.setClearColor(color);
    }

    public static void clear() {
        rendererAPI.clear();
    }

    public static void drawIndexed(VertexArray vertexArray, int indexCount) {
        rendererAPI.drawIndexed(vertexArray, indexCount);
    }
}
