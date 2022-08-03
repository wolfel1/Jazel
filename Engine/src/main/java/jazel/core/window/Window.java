package jazel.core.window;

import jazel.core.Log;
import jazel.renderer.GraphicsContext;
import platform.windows.WindowsWindow;

public interface Window {

  void onUpdate();

  int getWidth();

  int getHeight();

  void setVSync(boolean enabled);

  boolean isVSync();

  long getNativeWindow();

  void shutdown();

  static Window create(WindowProps props) {
    switch (GraphicsContext.getPlatform()) {
      case NONE:
        Log.getCoreLogger().error("RendererAPI NONE is not supported!");
      case WINDOWS:
        return new WindowsWindow(props);
    }
    Log.getCoreLogger().error("Unknown RendererAPI!");

    return null;
  };
}
