package jazel.engine.renderer.camera;

import jazel.engine.core.Input;
import jazel.engine.core.encoding.KeyCode;
import jazel.engine.events.EventDispatcher;
import jazel.engine.events.annotation.EventHandler;
import jazel.engine.events.application.WindowResizeEvent;
import jazel.engine.events.enumeration.EventType;
import jazel.engine.events.mouse.MouseScrolledEvent;
import lombok.Getter;
import lombok.Setter;
import org.joml.Math;
import org.joml.Vector3f;

import static java.lang.Math.max;

public class OrthographicCameraController {

    @Setter
    private boolean allowZoom = false;
    private float aspectRatio;
    @Getter
    protected float zoomLevel = 1.0f;
    @Setter
    @Getter
    protected OrthographicCamera camera;


    public OrthographicCameraController(float width, float height) {
        this.aspectRatio = width / height;
        camera = new OrthographicCamera(-aspectRatio * zoomLevel, aspectRatio * zoomLevel, -zoomLevel, zoomLevel);

        EventDispatcher.register(this);
    }

    public void onUpdate(float deltaTime) {
    }

    @EventHandler(type = EventType.MOUSE_SCROLLED)
    public boolean onMouseScrolled(MouseScrolledEvent event) {
        if (allowZoom) {
            zoomLevel -= event.getOffsetY() * 0.25f;
            zoomLevel = max(zoomLevel, 0.25f);

            calculateView();
        }
        return true;
    }

    @EventHandler(type = EventType.WINDOW_RESIZE)
    public boolean onWindowResized(WindowResizeEvent event) {
        aspectRatio = (float) event.getWidth() / (float) event.getHeight();

        calculateView();
        return true;
    }

    private void calculateView() {
        camera.setProjection(-aspectRatio * zoomLevel, aspectRatio * zoomLevel, -zoomLevel, zoomLevel);
    }
}
