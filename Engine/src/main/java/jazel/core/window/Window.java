package jazel.core.window;

import platform.window.WindowImpl;

public interface Window {

  void onUpdate();

  int getWidth();

  int getHeight();

  void setVSync(boolean enabled);

  boolean isVSync();

  long getNativeWindow();

  void shutdown();

  static Window create(WindowProps props) {
        return new WindowImpl(props);
  }
}
