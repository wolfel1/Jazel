package jazel.renderer;

import jazel.core.Log;
import jazel.renderer.enumeration.Platform;
import platform.opengl.OpenGLContext;

public interface  GraphicsContext {

  static Platform platform = Platform.WINDOWS;
  void init();
  void swapBuffers();

  static Platform getPlatform() {
    return platform;
  }

  static GraphicsContext create(long window) {
    switch (RendererAPI.getAPI()) {
      case NONE: Log.getCoreLogger().error("RendererAPI NONE is not supported!");
      case OPENGL: return new OpenGLContext(window);
    }
    Log.getCoreLogger().error("Unknown RendererAPI!");

    return null;
  };
}
