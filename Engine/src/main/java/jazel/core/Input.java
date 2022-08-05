package jazel.core;

import jazel.core.encoding.CursorMode;
import jazel.core.encoding.KeyCode;
import jazel.core.encoding.MouseCode;
import org.joml.Vector2f;
import platform.window.WindowInput;

public abstract class Input {

  private static Input instance;

  protected abstract boolean isKeyPressedImpl(int  key);
  protected abstract boolean isMouseButtonPressedImpl(int button);
  protected abstract Vector2f getMousePositionImpl();
  protected abstract void setMousePositionImpl(Vector2f  position);
  protected abstract void setCursorModeImpl(int mode);
  protected abstract float getMouseXImpl();
  protected abstract float getMouseYImpl();

  public static boolean isKeyPressed(int key) {
    return instance.isKeyPressedImpl(key);
  }

  public static boolean isMouseButtonPressed(int button) {
    return instance.isMouseButtonPressedImpl(button);
  }

  public static Vector2f getMousePosition() {
    return instance.getMousePositionImpl();
  }

  public static void setMousePosition(Vector2f position) {
    instance.setMousePositionImpl(position);
  }
  
  public static void setCursorMode(int mode) {
    instance.setCursorModeImpl(mode);
  }

  public static float getMouseX() {
    return instance.getMouseXImpl();
  }

  public static float getMouseY() {
    return instance.getMouseYImpl();
  }

  public static void create() {
    if (instance == null) {
      instance = new WindowInput();
    }
  }
}
