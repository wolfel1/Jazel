package jazel.engine.renderer.container;

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

  public static VertexBuffer create(int[] indices, int count) {
    return null;
  }
}
