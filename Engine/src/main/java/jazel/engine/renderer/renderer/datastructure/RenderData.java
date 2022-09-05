package jazel.engine.renderer.renderer.datastructure;

import jazel.engine.renderer.container.VertexArray;
import jazel.engine.renderer.container.VertexBuffer;
import jazel.engine.renderer.shader.Shader;

public final class RenderData {
    public static final int MAX_QUADS = 10000;
    public static final int MAX_VERTICES = MAX_QUADS * 4;
    public static final int MAX_INDICES = MAX_QUADS * 6;

    public VertexArray quadVertexArray;
    public VertexBuffer quadVertexBuffer;

    public QuadVertex[] quadVertices;
    public int quadVertexIndex;
    public int quadIndexCount;

    public Shader globalShader;
}
