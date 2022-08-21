package jazel.engine.renderer.container;

import jazel.engine.core.Log;
import jazel.engine.renderer.renderer.RendererAPI;
import jazel.platform.opengl.container.OpenGLIndexBuffer;
import jazel.platform.opengl.container.OpenGLVertexBuffer;
import lombok.AllArgsConstructor;
import lombok.Getter;

public abstract class IndexBuffer {

    protected int rendererID;

    @Getter
    protected int count;

    public IndexBuffer(int count) {
        this.count = count;
    }

    public abstract void bind();

    public abstract void unbind();

    public abstract void destroy();

    public static IndexBuffer create(int[] indices, int count) {
        switch (RendererAPI.getAPI()) {
        case NONE -> {
            Log.getCoreLogger().error("RendererAPI NONE is not supported!");
            return null;
        }
        case OPENGL -> {
            return new OpenGLIndexBuffer(indices, count);
        }
        }

        Log.getCoreLogger().error("Unknown RendererAPI!");
        return null;
    }
}
