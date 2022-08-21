package jazel.platform.opengl.container;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glBufferSubData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL45.glCreateBuffers;

import jazel.engine.renderer.container.VertexBuffer;

import java.nio.FloatBuffer;

public class OpenGLVertexBuffer extends VertexBuffer {

  public OpenGLVertexBuffer(long size) {
    super(size);

    rendererID = glCreateBuffers();
    glBindBuffer(GL_ARRAY_BUFFER, rendererID);
  }

  public OpenGLVertexBuffer(float[] vertices) {
    super(vertices);

    rendererID = glCreateBuffers();
    glBindBuffer(GL_ARRAY_BUFFER, rendererID);
    glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
  }

  @Override
  public void bind() {
    glBindBuffer(GL_ARRAY_BUFFER, rendererID);
  }

  @Override
  public void unbind() {
  glBindBuffer(GL_ARRAY_BUFFER, 0);
  }

  @Override
  public void setData(FloatBuffer data) {
    glBindBuffer(GL_ARRAY_BUFFER, rendererID);
    glBufferData(GL_ARRAY_BUFFER, data.array(), GL_DYNAMIC_DRAW);
  }

  @Override
  public void destroy() {
    glDeleteBuffers(rendererID);
  }
}
