package jazel.platform.opengl.container;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL45.glCreateVertexArrays;

import jazel.engine.core.Core;
import jazel.engine.core.Log;
import jazel.engine.renderer.container.IndexBuffer;
import jazel.engine.renderer.container.VertexArray;
import jazel.engine.renderer.container.VertexBuffer;
import jazel.platform.opengl.shader.OpenGLShaderUtils;
import org.lwjgl.system.MemoryUtil;

import java.util.ArrayList;

public class OpenGLVertexArray extends VertexArray {

  public OpenGLVertexArray() {
    rendererID = glCreateVertexArrays();
    vertexBuffers = new ArrayList<>();
  }
  @Override
  public void bind() {
    glBindVertexArray(rendererID);
  }

  @Override
  public void unbind() {
    glBindVertexArray(0);
  }

  @Override
  public void addVertexBuffer(VertexBuffer vertexBuffer) {
    Core.assertion(vertexBuffer.getLayout().getElements().size() == 0, "Vertex Buffer has no layout");

    glBindVertexArray(rendererID);
    vertexBuffer.bind();

    var layout = vertexBuffer.getLayout();
    for (var element : layout.getElements()) {
      switch (element.getShaderDataType()) {
        case FLOAT, FLOAT2, FLOAT3, FLOAT4, INT, INT2, INT3, INT4, BOOL -> {
          glEnableVertexAttribArray(vertexBufferIndex);
          glVertexAttribPointer(vertexBufferIndex,
                  element.getComponentCount(),
                  OpenGLShaderUtils.shaderDataTypeToOpenGLBaseType(element.getShaderDataType()),
                  element.isNormalized(),
                  layout.getStride(),
                  element.getOffset());
          vertexBufferIndex++;
        }
        case MAT3, MAT4 -> {
          var count = element.getComponentCount();
          for (int i = 0; i < count; i++) {
            glEnableVertexAttribArray(vertexBufferIndex);
            glVertexAttribPointer(vertexBufferIndex,
                    element.getComponentCount(),
                    OpenGLShaderUtils.shaderDataTypeToOpenGLBaseType(element.getShaderDataType()),
                    element.isNormalized(),
                    layout.getStride(),
                    element.getOffset());

            vertexBufferIndex++;
          }
        }
        default -> Log.getCoreLogger().error("Unknown ShaderDataType!");
      }
      vertexBuffers.add(vertexBuffer);
    }
  }

  @Override
  public void setIndexBuffer(IndexBuffer indexBuffer) {
    glBindVertexArray(rendererID);
    indexBuffer.bind();

    this.indexBuffer = indexBuffer;
  }

  @Override
  public void destroy() {
    indexBuffer.destroy();
    glDeleteVertexArrays(rendererID);
  }
}
