package platform.windows;

import jazel.core.Core;
import jazel.core.Log;
import jazel.core.window.Window;
import jazel.core.window.WindowProps;
import jazel.events.EventRegistry;
import jazel.events.application.WindowCloseEvent;
import jazel.events.application.WindowResizeEvent;
import jazel.events.key.KeyPressedEvent;
import jazel.events.key.KeyReleasedEvent;
import jazel.events.key.KeyTypedEvent;
import jazel.events.mouse.MouseButtonPressedEvent;
import jazel.events.mouse.MouseButtonReleasedEvent;
import jazel.events.mouse.MouseMovedEvent;
import jazel.events.mouse.MouseScrolledEvent;
import jazel.renderer.GraphicsContext;
import jazel.renderer.RendererAPI;
import jazel.renderer.enumeration.API;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class WindowsWindow implements Window {

  private static class WindowData {
    String title;
    int width, height;
    boolean vSync;
  }

  private long window;
  private GraphicsContext context;

  private final WindowData data = new WindowData();

  private boolean glfwInitialized;

  public WindowsWindow(WindowProps props) {
    init(props);
  }

  private void init(WindowProps props) {
    data.title = props.getTitle();
    data.width = props.getWidth();
    data.height = props.getHeight();

    Log.getCoreLogger().info("Creating window {}", props);

    if (!glfwInitialized) {
      boolean success = glfwInit();
      Core.assertion(!success, "Could not initialize GLFW!");
      glfwInitialized = true;
      glfwSetErrorCallback(
          (error, description) ->
              Log.getCoreLogger()
                  .error(
                      "GLFW Error ({}): {}", error, GLFWErrorCallback.getDescription(description)));
    }

    if (RendererAPI.getAPI() == API.OPENGL) {
      glfwWindowHint(GLFW.GLFW_OPENGL_DEBUG_CONTEXT, GLFW.GLFW_TRUE);
    }

    window = glfwCreateWindow(data.width, data.height, data.title, NULL, NULL);

    context = GraphicsContext.create(window);
    Core.assertion(context == null, "Unknown context!");
    context.init();

    setVSync(true);

    setCallbacks();
  }

  private void setCallbacks() {
    glfwSetWindowSizeCallback(
        window,
        (long window, int width, int height) -> {
          data.width = width;
          data.height = height;

          var event = new WindowResizeEvent(width, height);

          EventRegistry.register(event);
        });

    glfwSetWindowCloseCallback(
        window,
        (long window) -> {
          var event = new WindowCloseEvent();

          EventRegistry.register(event);
        });

    glfwSetKeyCallback(
        window,
        (long window, int key, int scanCode, int action, int mods) -> {
          switch (action) {
            case GLFW_PRESS:
              {
                var event = new KeyPressedEvent(key, 0);

                EventRegistry.register(event);
              }
            case GLFW_RELEASE:
              {
                var event = new KeyReleasedEvent(key);

                EventRegistry.register(event);
              }
            case GLFW_REPEAT:
              {
                var event = new KeyPressedEvent(key, 1);

                EventRegistry.register(event);
              }
          }
        });

    glfwSetCharCallback(
        window,
        (long window, int keycode) -> {
          var event = new KeyTypedEvent(keycode);

          EventRegistry.register(event);
        });

    glfwSetMouseButtonCallback(
        window,
        (long window, int button, int action, int mods) -> {
          switch (action) {
            case GLFW_PRESS:
              {
                var event = new MouseButtonPressedEvent(button);

                EventRegistry.register(event);
              }
            case GLFW_RELEASE:
              {
                var event = new MouseButtonReleasedEvent(button);

                EventRegistry.register(event);
              }
          }
        });

    glfwSetScrollCallback(
        window,
        (long window, double xOffset, double yOffset) -> {
          var event = new MouseScrolledEvent((float) xOffset, (float) yOffset);

          EventRegistry.register(event);
        });

    glfwSetCursorPosCallback(
        window,
        (long window, double xPos, double yPos) -> {
          var event = new MouseMovedEvent((float) xPos, (float) yPos);

          EventRegistry.register(event);
        });
  }

  @Override
  public void onUpdate() {
    glfwPollEvents();

    context.swapBuffers();
  }

  @Override
  public int getWidth() {
    return data.width;
  }

  @Override
  public int getHeight() {
    return data.height;
  }

  @Override
  public void setVSync(boolean enabled) {
    if (enabled) {
      GLFW.glfwSwapInterval(1);
    } else {
      GLFW.glfwSwapInterval(0);
    }

    data.vSync = enabled;
  }

  @Override
  public boolean isVSync() {
    return data.vSync;
  }

  @Override
  public long getNativeWindow() {
    return window;
  }

  @Override
  public void shutdown() {
    glfwDestroyWindow(window);

    Log.getCoreLogger().info("Terminate GLFW");
    glfwTerminate();
  }
}
