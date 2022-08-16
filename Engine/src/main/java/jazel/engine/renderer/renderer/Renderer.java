package jazel.engine.renderer.renderer;

import jazel.engine.renderer.container.VertexArray;
import jazel.engine.renderer.container.VertexBuffer;
import jazel.engine.renderer.renderer.datastructure.QuadVertex;
import jazel.engine.renderer.renderer.datastructure.RenderData;

public class Renderer {

    public static void init() {
        RenderCommand.init();

        RenderData.vertexArray = VertexArray.create();

        RenderData.vertexBuffer = VertexBuffer.create(RenderData.MAX_VERTICES);
    }

    public static void shutdown() {
        RenderData.vertexBuffer.destroy();
    }

    public static void onWindowResize(int width, int height) {}
}
