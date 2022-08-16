package jazel.engine.renderer.container;

import java.util.List;

public abstract class VertexArray {

  public abstract void bind();
  public abstract void unbind();
  public abstract void addVertexBuffer(VertexBuffer vertexBuffer);
  public abstract void setIndexBuffer(IndexBuffer indexBuffer);
  public abstract List<VertexBuffer> getVertexBuffers();
  public abstract IndexBuffer getIndexBuffer();

  public static VertexArray create() {
    return null;
  }
}
