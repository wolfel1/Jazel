package jazel.core;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

import jazel.events.Event;
import jazel.events.EventDispatcher;
import jazel.events.EventRegistry;
import jazel.events.JazelEventListener;
import jazel.events.application.WindowCloseEvent;
import jazel.events.application.WindowResizeEvent;
import jazel.events.application.listener.WindowCloseEventListener;
import jazel.events.application.listener.WindowResizeEventListener;
import lombok.Getter;
import org.lwjgl.glfw.GLFW;

public class Application {

  private static Application instance;

  @Getter private final Window window;
  private final LayerStack layerStack;

  private boolean running;
  private boolean minimized;

  public Application() {
    Core.assertion(instance != null, "Application already exists");

    instance = this;
    layerStack = new LayerStack();

    window = Window.create(new WindowProps("Jazel", 1920, 1080));
    running = true;
    minimized = false;

    EventDispatcher.register(new WindowCloseEventListener() {
      @Override
      public boolean onWindowClose(WindowCloseEvent event) {
        Log.getCoreLogger().info("{}", event);
        running = false;

        return true;
      }
    });

    EventDispatcher.register(new WindowResizeEventListener() {
      @Override
      public boolean onWindowResize(WindowResizeEvent event) {
        Log.getCoreLogger().info("{}", event);
        if (event.getWidth() == 0 && event.getHeight() == 0) {
          minimized = true;
          return true;
        }

        minimized = false;

        return true;
      }
    });
  }

  public void run() {
    while (running) {
      float time = (float) glfwGetTime();

      if (!minimized) {
        for (Layer layer : layerStack.getLayers()) {
          layer.onUpdate();
        }
      }

      window.onUpdate();

      EventRegistry.handleEvents();
    }

    window.shutdown();
  }

  public void onEvent(Event event) {
    Log.getCoreLogger().info("{}", event);
    var iterator = layerStack.getLayers().descendingIterator();
    while (iterator.hasNext()) {
      if (!event.isHandled()) {
        iterator.next().onEvent(event);
      }
    }
  }

  public void pushLayer(Layer layer) {
    layerStack.pushLayer(layer);
    layer.onAttach();
  }

  public void pushOverlay(Layer overlay) {
    layerStack.pushOverlay(overlay);
    overlay.onAttach();
  }

  public static Application getInstance() {
    return instance;
  }
}
