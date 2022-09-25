package jazel.engine.core;

import jazel.engine.core.encoding.KeyCode;
import jazel.engine.core.layer.Layer;
import jazel.engine.core.layer.LayerStack;
import jazel.engine.core.window.Window;
import jazel.engine.core.window.WindowProps;
import jazel.engine.events.Event;
import jazel.engine.events.EventDispatcher;
import jazel.engine.events.EventRegistry;
import jazel.engine.events.annotation.EventHandler;
import jazel.engine.events.application.WindowCloseEvent;
import jazel.engine.events.application.WindowResizeEvent;
import jazel.engine.events.enumeration.EventType;
import jazel.engine.events.key.KeyReleasedEvent;
import jazel.engine.gui.ImGuiLayer;
import jazel.engine.renderer.renderer.Renderer;
import lombok.Getter;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class Application {

    private static Application instance;

    @Getter
    private final Window window;
    private final LayerStack layerStack;

    private ImGuiLayer imGuiLayer;

    private boolean running;
    private boolean minimized;
    private float lastFrameTime = 0.0f;

    public Application() {
        Core.checkEnvironment();

        Core.assertion(instance != null, "Application already exists");

        instance = this;
        layerStack = new LayerStack();

        window = Window.create(new WindowProps("Jazel", 1920, 1080));
        running = true;
        minimized = false;

        Renderer.init();

        imGuiLayer = new ImGuiLayer();
        pushOverlay(imGuiLayer);

        EventDispatcher.register(this);
    }

    public void run() {
        while (running) {
            float time = (float) glfwGetTime();
            float deltaTime = time - lastFrameTime;
            lastFrameTime = time;

            if (!minimized) {
                for (Layer layer : layerStack.getLayers()) {
                    layer.onUpdate(deltaTime);
                }
            }

            imGuiLayer.begin();
            for (Layer layer : layerStack.getLayers()) {
                layer.onGuiRender();
            }
            imGuiLayer.end();

            window.onUpdate();

            EventRegistry.handleEvents();
        }

        window.shutdown();
        Renderer.shutdown();
    }

    public void onEvent(Event event) {

        if (event.getType() == EventType.KEY_RELEASED) {
            KeyReleasedEvent e = (KeyReleasedEvent) event;
            if (e.getKeyCode() == KeyCode.ESCAPE) {
                EventRegistry.register(new WindowCloseEvent());
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
        running = false;

        return true;
    }

    @EventHandler(type = EventType.WINDOW_RESIZE)
    public boolean onWindowResize(WindowResizeEvent event) {
        if (event.getWidth() == 0 && event.getHeight() == 0) {
            minimized = true;
            return true;
        }

        minimized = false;
        Renderer.onWindowResize(event.getWidth(), event.getHeight());

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
