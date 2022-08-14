package jazel.platform.window;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_REPEAT;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetCharCallback;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetScrollCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowCloseCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.system.MemoryUtil.NULL;

import jazel.engine.core.Core;
import jazel.engine.core.Input;
import jazel.engine.core.Log;
import jazel.engine.core.window.Window;
import jazel.engine.core.window.WindowProps;
import jazel.engine.events.EventRegistry;
import jazel.engine.events.application.WindowCloseEvent;
import jazel.engine.events.application.WindowResizeEvent;
import jazel.engine.events.key.KeyPressedEvent;
import jazel.engine.events.key.KeyReleasedEvent;
import jazel.engine.events.key.KeyTypedEvent;
import jazel.engine.events.mouse.MouseButtonPressedEvent;
import jazel.engine.events.mouse.MouseButtonReleasedEvent;
import jazel.engine.events.mouse.MouseMovedEvent;
import jazel.engine.events.mouse.MouseScrolledEvent;
import jazel.engine.renderer.GraphicsContext;
import jazel.engine.renderer.RendererAPI;
import jazel.engine.renderer.enumeration.API;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;

public class WindowImpl extends Window {

    private static class WindowData {
        String title;
        int width, height;
        boolean vSync;
    }

    private long window;
    private GraphicsContext context;

    private final WindowData data = new WindowData();

    private boolean glfwInitialized;

    public WindowImpl(WindowProps props) {
        init(props);
        Input.create();
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
            glfwSetErrorCallback((error, description) -> Log.getCoreLogger().error("GLFW Error ({}): {}", error,
                    GLFWErrorCallback.getDescription(description)));
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
        glfwSetWindowSizeCallback(window, (long window, int width, int height) -> {
            data.width = width;
            data.height = height;

            var event = new WindowResizeEvent(width, height);

            EventRegistry.register(event);
        });

        glfwSetWindowCloseCallback(window, (long window) -> {
            var event = new WindowCloseEvent();

            EventRegistry.register(event);
        });

        glfwSetKeyCallback(window, (long window, int key, int scanCode, int action, int mods) -> {
            switch (action) {
            case GLFW_PRESS: {
                var event = new KeyPressedEvent(key, 0);

                EventRegistry.register(event);
            }
            case GLFW_RELEASE: {
                var event = new KeyReleasedEvent(key);

                EventRegistry.register(event);
            }
            case GLFW_REPEAT: {
                var event = new KeyPressedEvent(key, 1);

                EventRegistry.register(event);
            }
            }
        });

        glfwSetCharCallback(window, (long window, int keycode) -> {
            var event = new KeyTypedEvent(keycode);

            EventRegistry.register(event);
        });

        glfwSetMouseButtonCallback(window, (long window, int button, int action, int mods) -> {
            switch (action) {
            case GLFW_PRESS: {
                var event = new MouseButtonPressedEvent(button);

                EventRegistry.register(event);
            }
            case GLFW_RELEASE: {
                var event = new MouseButtonReleasedEvent(button);

                EventRegistry.register(event);
            }
            }
        });

        glfwSetScrollCallback(window, (long window, double xOffset, double yOffset) -> {
            var event = new MouseScrolledEvent((float) xOffset, (float) yOffset);

            EventRegistry.register(event);
        });

        glfwSetCursorPosCallback(window, (long window, double xPos, double yPos) -> {
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
