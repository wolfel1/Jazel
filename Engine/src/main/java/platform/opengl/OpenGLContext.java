package platform.opengl;

import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.GL_RENDERER;
import static org.lwjgl.opengl.GL11.GL_VENDOR;
import static org.lwjgl.opengl.GL11.GL_VERSION;
import static org.lwjgl.opengl.GL11.glGetInteger;
import static org.lwjgl.opengl.GL11.glGetString;
import static org.lwjgl.opengl.GL30.GL_MAJOR_VERSION;
import static org.lwjgl.opengl.GL30.GL_MINOR_VERSION;

import jazel.core.Log;
import jazel.renderer.GraphicsContext;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL46;

public class OpenGLContext implements GraphicsContext {

  private final long window;

  public OpenGLContext(long window) {
    this.window = window;
  }

  @Override
  public void init() {
    glfwMakeContextCurrent(window);

    GL.createCapabilities(true);

    Log.getCoreLogger().info("OpenGL Info:");
    Log.getCoreLogger().info("  Vendor: {}", glGetString(GL_VENDOR));
    Log.getCoreLogger().info("  Renderer: {}", glGetString(GL_RENDERER));
    Log.getCoreLogger().info("  Version: {}", glGetString(GL_VERSION));

    Log.getCoreLogger()
        .info(
            "  OpenGL version: {}.{}",
            glGetInteger(GL_MAJOR_VERSION),
            glGetInteger(GL_MINOR_VERSION));
  }

  @Override
  public void swapBuffers() {
    glfwSwapBuffers(window);
  }
}
