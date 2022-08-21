package jazel.platform.opengl.container;

import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL45.glCreateBuffers;

import jazel.engine.renderer.container.IndexBuffer;

public class OpenGLIndexBuffer extends IndexBuffer {

    public OpenGLIndexBuffer(int[] indices, int count) {
        super(count);

        rendererID = glCreateBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, rendererID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);
    }

    @Override
    public void bind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, rendererID);
    }

    @Override
    public void unbind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

    }

    @Override
    public void destroy() {
        glDeleteBuffers(rendererID);
    }
}
