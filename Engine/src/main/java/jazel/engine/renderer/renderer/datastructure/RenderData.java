package jazel.engine.renderer.renderer.datastructure;

import jazel.engine.renderer.container.VertexArray;
import jazel.engine.renderer.container.VertexBuffer;

public final class RenderData {
    public static final int MAX_QUADS = 10000;
    public static final int MAX_VERTICES = MAX_QUADS * 4;
    public static final int MAX_INDICES = MAX_QUADS * 6;

    public static VertexArray vertexArray;
    public static VertexBuffer vertexBuffer;
}
