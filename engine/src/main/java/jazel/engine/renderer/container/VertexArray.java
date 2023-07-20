package jazel.engine.renderer.container;

import java.util.List;

import jazel.engine.core.Log;
import jazel.engine.renderer.renderer.RendererAPI;
import jazel.platform.opengl.container.OpenGLVertexArray;
import jazel.platform.opengl.container.OpenGLVertexBuffer;
import lombok.Getter;
import lombok.Setter;

public abstract class VertexArray {

    protected int rendererID;
    protected int vertexBufferIndex = 0;
    @Getter
    protected List<VertexBuffer> vertexBuffers;
    @Getter
    protected IndexBuffer indexBuffer;

    public abstract void bind();

    public abstract void unbind();

    public abstract void addVertexBuffer(VertexBuffer vertexBuffer);

    public abstract void setIndexBuffer(IndexBuffer indexBuffer);

    public abstract void destroy();

    public static VertexArray create() {
        switch (RendererAPI.getAPI()) {
        case NONE -> {
            Log.getCoreLogger().error("RendererAPI NONE is not supported!");
            return null;
        }
        case OPENGL -> {
            return new OpenGLVertexArray();
        }
        }

        Log.getCoreLogger().error("Unknown RendererAPI!");
        return null;
    }

}
