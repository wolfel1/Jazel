package platform.opengl;

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
    GLFW.glfwMakeContextCurrent(window);

    GL.createCapabilities(true);

    Log.getCoreLogger().info("OpenGL Info:");
    Log.getCoreLogger().info("  Vendor: {}", GL46.glGetString(GL46.GL_VENDOR));
    Log.getCoreLogger().info("  Renderer: {}", GL46.glGetString(GL46.GL_RENDERER));
    Log.getCoreLogger().info("  Version: {}", GL46.glGetString(GL46.GL_VERSION));

    Log.getCoreLogger().info("  OpenGL version: {}.{}",
        GL46.glGetInteger(GL46.GL_MAJOR_VERSION),
        GL46.glGetInteger(GL46.GL_MINOR_VERSION));

  }

  @Override
  public void swapBuffers() {
    GLFW.glfwSwapBuffers(window);
  }
}
