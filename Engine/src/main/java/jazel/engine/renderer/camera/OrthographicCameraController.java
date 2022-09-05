package jazel.engine.renderer.camera;

import jazel.engine.events.EventDispatcher;
import jazel.engine.events.annotation.EventHandler;
import jazel.engine.events.application.WindowCloseEvent;
import jazel.engine.events.application.WindowResizeEvent;
import jazel.engine.events.enumeration.EventType;
import jazel.engine.events.mouse.MouseScrolledEvent;
import jazel.engine.renderer.renderer.Renderer;
import lombok.Getter;
import lombok.Setter;

import static java.lang.Math.max;

public class OrthographicCameraController {

    private boolean zoomAllowed;
    private boolean rotationAllowed;

    private float aspectRatio;
    @Getter private float zoomLevel = 1.0f;

    @Setter @Getter private OrthographicCamera camera;

    @Setter @Getter
    private float cameraTranslationSpeed = 5.0f, cameraRotationSpeed = 180.0f;
    public OrthographicCameraController(float aspectRatio, boolean allowZoom, boolean allowRotation) {
        zoomAllowed = allowZoom;
        rotationAllowed = allowRotation;

        this.aspectRatio = aspectRatio;
        camera = new OrthographicCamera(-aspectRatio * zoomLevel, aspectRatio * zoomLevel, -zoomLevel, zoomLevel);

        EventDispatcher.register(this);
    }

    public void onUpdate() {

    }

    @EventHandler(type = EventType.MOUSE_SCROLLED)
    public boolean onMouseScrolled(MouseScrolledEvent event) {
        if(zoomAllowed) {
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
