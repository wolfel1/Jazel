package platform.window;

import static org.lwjgl.glfw.GLFW.GLFW_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_REPEAT;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.glfw.GLFW.glfwGetMouseButton;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwSetInputMode;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import jazel.core.Application;
import jazel.core.Input;
import jazel.core.encoding.CursorMode;
import jazel.core.encoding.KeyCode;
import jazel.core.encoding.MouseCode;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

public class WindowInput extends Input {

    @Override
    protected boolean isKeyPressedImpl(int key) {
        var window = Application.getInstance().getWindow().getNativeWindow();
        var state = glfwGetKey(window, key);
        return state == GLFW_PRESS || state == GLFW_REPEAT;
    }

    @Override
    protected boolean isMouseButtonPressedImpl(int button) {
        var window = Application.getInstance().getWindow().getNativeWindow();
        var state = glfwGetMouseButton(window, button);
        return state == GLFW_PRESS;
    }

    @Override
    protected Vector2f getMousePositionImpl() {
        var window = Application.getInstance().getWindow().getNativeWindow();
        DoubleBuffer x = DoubleBuffer.allocate(8), y = DoubleBuffer.allocate(8);
        glfwGetCursorPos(window, x, y);
        return new Vector2f((float) x.get(), (float) y.get());
    }

    @Override
    protected void setMousePositionImpl(Vector2f position) {
        var window = Application.getInstance().getWindow().getNativeWindow();
        glfwSetCursorPos(window, position.x, position.y);
    }

    @Override
    protected void setCursorModeImpl(int mode) {
        var window = Application.getInstance().getWindow().getNativeWindow();
        glfwSetInputMode(window, GLFW_CURSOR, mode);
    }

    @Override
    protected float getMouseXImpl() {
        return getMousePositionImpl().x;
    }

    @Override
    protected float getMouseYImpl() {
        return getMousePositionImpl().y;
    }
}
