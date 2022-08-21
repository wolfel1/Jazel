package jazel.platform.opengl;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL43.GL_DEBUG_OUTPUT;
import static org.lwjgl.opengl.GL43.GL_DEBUG_OUTPUT_SYNCHRONOUS;
import static org.lwjgl.opengl.GL43.GL_DEBUG_SEVERITY_HIGH;
import static org.lwjgl.opengl.GL43.GL_DEBUG_SEVERITY_LOW;
import static org.lwjgl.opengl.GL43.GL_DEBUG_SEVERITY_MEDIUM;
import static org.lwjgl.opengl.GL43.GL_DEBUG_SEVERITY_NOTIFICATION;
import static org.lwjgl.opengl.GL43.glDebugMessageCallback;
import static org.lwjgl.opengl.GL43.glDebugMessageControl;
import static org.lwjgl.opengl.GLDebugMessageCallback.getMessage;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.IntBuffer;
import jazel.engine.core.Log;
import jazel.engine.renderer.container.VertexArray;
import jazel.engine.renderer.renderer.RendererAPI;
import org.joml.Vector4f;

public class OpenGLRendererAPI extends RendererAPI {

    @Override
    public void init() {

        // Debug
        glEnable(GL_DEBUG_OUTPUT);
        glEnable(GL_DEBUG_OUTPUT_SYNCHRONOUS);
        glDebugMessageCallback(
                (int source, int type, int id, int severity, int length, long message, long userParam) -> {
                    switch (severity) {
                    case GL_DEBUG_SEVERITY_HIGH -> Log.getCoreLogger().error(getMessage(length, message));
                    case GL_DEBUG_SEVERITY_MEDIUM -> Log.getCoreLogger().debug(getMessage(length, message));
                    case GL_DEBUG_SEVERITY_LOW -> Log.getCoreLogger().warn(getMessage(length, message));
                    case GL_DEBUG_SEVERITY_NOTIFICATION -> Log.getCoreLogger().trace(getMessage(length, message));
                    }
                }, NULL);
        glDebugMessageControl(GL_DONT_CARE, GL_DONT_CARE, GL_DEBUG_SEVERITY_NOTIFICATION, (IntBuffer) null, false);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        glEnable(GL_DEPTH_TEST);
    }

    @Override
    public void setViewport(int x, int y, int width, int height) {
        glViewport(x, y, width, height);
    }

    @Override
    public void setClearColor(Vector4f color) {
        glClearColor(color.x, color.y, color.z, color.w);
    }

    @Override
    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    @Override
    public void drawIndexed(VertexArray vertexArray, int indexCount) {
        glDrawElements(GL_TRIANGLES, indexCount, GL_UNSIGNED_INT, NULL);
    }
}
