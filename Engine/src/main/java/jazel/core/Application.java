package jazel.core;

import jazel.events.Event;
import jazel.events.EventDispatcher;
import jazel.events.application.WindowCloseEvent;
import jazel.events.application.WindowResizeEvent;
import org.lwjgl.glfw.GLFW;

public class Application {

  private static Application instance;

  private final Window window;
  private LayerStack layerStack;

  private boolean running;
  private boolean minimized;

  public Application() {
    Core.assertion(instance == null, "Application already exists");

    instance = this;
    layerStack = new LayerStack();

    window = Window.create(new WindowProps("Jazel", 1920, 1080));
    running = true;
    minimized = false;
  }

  public void run() {
    while (running) {
      float time = (float) GLFW.glfwGetTime();

      if (!minimized) {
        for (Layer layer : layerStack.getLayers()) {
          layer.onUpdate();
        }
      }

      window.onUpdate();
    }
  }

  public void onEvent(Event event) {
    EventDispatcher.dispatch(event);

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

  public boolean onWindowClose(WindowCloseEvent event) {
    running = false;

    return true;
  }

  public boolean onWindowResize(WindowResizeEvent event) {
    if (event.getWidth() == 0 && event.getHeight() == 0) {
      minimized = true;
      return false;
    }

    minimized = false;

    return true;
  }

  public static Application getInstance() {
    return instance;
  }
}
