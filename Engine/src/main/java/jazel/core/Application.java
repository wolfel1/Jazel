package jazel.core;

import jazel.core.encoding.KeyCode;
import jazel.core.layer.Layer;
import jazel.core.layer.LayerStack;
import jazel.core.window.Window;
import jazel.core.window.WindowProps;
import jazel.events.Event;
import jazel.events.EventDispatcher;
import jazel.events.EventRegistry;
import jazel.events.annotation.EventHandler;
import jazel.events.application.WindowCloseEvent;
import jazel.events.application.WindowResizeEvent;
import jazel.events.enumeration.EventType;
import jazel.events.key.KeyReleasedEvent;
import jazel.gui.ImGuiLayer;
import lombok.Getter;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class Application {

  private static Application instance;

  @Getter private final Window window;
  private final LayerStack layerStack;

  private ImGuiLayer imGuiLayer;

  private boolean running;
  private boolean minimized;

  public Application() {
    Core.assertion(instance != null, "Application already exists");

    instance = this;
    layerStack = new LayerStack();

    window = Window.create(new WindowProps("Jazel", 1920, 1080));
    running = true;
    minimized = false;

    imGuiLayer = new ImGuiLayer();
    pushOverlay(imGuiLayer);

    EventDispatcher.register(this);
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

    if (event.getType() == EventType.KEY_RELEASED) {
      KeyReleasedEvent e = (KeyReleasedEvent) event;
      if (e.getKeyCode() == KeyCode.ESCAPE) {
        EventRegistry.register(new WindowCloseEvent());
        event.setHandled(true);
      }
    }
    var iterator = layerStack.getLayers().descendingIterator();
    while (iterator.hasNext()) {
      if (!event.isHandled()) {
        iterator.next().onEvent(event);
      } else {
        iterator.next();
      }
    }
  }

  @EventHandler(type = EventType.WINDOW_CLOSE)
  public boolean onWindowClose(WindowCloseEvent event) {
    Log.getCoreLogger().info("{}", event);
    running = false;

    return true;
  }

  @EventHandler(type = EventType.WINDOW_RESIZE)
  public boolean onWindowResize(WindowResizeEvent event) {
    Log.getCoreLogger().info("{}", event);
    if (event.getWidth() == 0 && event.getHeight() == 0) {
      minimized = true;
      return true;
    }

    minimized = false;

    return true;
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
