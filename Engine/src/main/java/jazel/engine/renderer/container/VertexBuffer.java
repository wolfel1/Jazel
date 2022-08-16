package jazel.engine.renderer.container;

import lombok.Getter;
import lombok.Setter;

public abstract class VertexBuffer {

  protected int rendererID;

  @Getter
  @Setter
  protected BufferLayout layout;

  public VertexBuffer(long size) {}
  public VertexBuffer(float[] vertices) {}


  public abstract void bind();
  public abstract void unbind();
  public abstract void setData(float[] data);

  public abstract void destroy();

  public static VertexBuffer create(int size) {
    return null;
  }

  public static VertexBuffer create(float[] vertices) {
    return null;
  }
}
