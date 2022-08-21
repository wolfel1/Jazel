package jazel.engine.renderer.container;

import jazel.engine.core.Core;
import jazel.engine.core.Log;
import jazel.engine.renderer.renderer.Renderer;
import jazel.engine.renderer.renderer.RendererAPI;
import jazel.platform.opengl.container.OpenGLVertexBuffer;
import lombok.Getter;
import lombok.Setter;

import java.nio.FloatBuffer;

public abstract class VertexBuffer {

  protected int rendererID;

  @Getter
  @Setter
  protected BufferLayout layout;

  public VertexBuffer(long size) {}
  public VertexBuffer(float[] vertices) {}


  public abstract void bind();
  public abstract void unbind();
  public abstract void setData(FloatBuffer data);

  public abstract void destroy();

  public static VertexBuffer create(long size) {
    switch (RendererAPI.getAPI()) {
      case NONE -> { Log.getCoreLogger().error("RendererAPI NONE is not supported!"); return null; }
      case OPENGL -> {
        return new OpenGLVertexBuffer(size);
      }
    }

    Log.getCoreLogger().error("Unknown RendererAPI!");
    return null;
  }

  public static VertexBuffer create(float[] vertices) {
    switch (RendererAPI.getAPI()) {
      case NONE -> { Log.getCoreLogger().error("RendererAPI NONE is not supported!"); return null; }
      case OPENGL -> {
        return new OpenGLVertexBuffer(vertices);
      }
    }

    Log.getCoreLogger().error("Unknown RendererAPI!");
    return null;
  }
}
