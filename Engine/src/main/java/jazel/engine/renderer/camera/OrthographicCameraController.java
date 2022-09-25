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
    private boolean zoomAllowed;
    @Setter
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

    public void onUpdate(float deltaTime) {

        var deltaPosition = new Vector3f();
            if (Input.isKeyPressed(KeyCode.A)) {
                deltaPosition.x -= Math.cos(Math.toRadians(camera.getRotation())) * cameraTranslationSpeed * deltaTime;
                deltaPosition.y -= Math.sin(Math.toRadians(camera.getRotation())) * cameraTranslationSpeed * deltaTime;
            } else if (Input.isKeyPressed(KeyCode.D)) {
                deltaPosition.x += Math.cos(Math.toRadians(camera.getRotation())) * cameraTranslationSpeed * deltaTime;
                deltaPosition.y += Math.sin(Math.toRadians(camera.getRotation())) * cameraTranslationSpeed * deltaTime;
            }

            if (Input.isKeyPressed(KeyCode.W)) {
                deltaPosition.x += -Math.sin(Math.toRadians(camera.getRotation())) * cameraTranslationSpeed * deltaTime;
                deltaPosition.y += Math.cos(Math.toRadians(camera.getRotation())) * cameraTranslationSpeed * deltaTime;
            } else if (Input.isKeyPressed(KeyCode.S)) {
                deltaPosition.x -= -Math.sin(Math.toRadians(camera.getRotation())) * cameraTranslationSpeed * deltaTime;
                deltaPosition.y -= Math.cos(Math.toRadians(camera.getRotation())) * cameraTranslationSpeed * deltaTime;
            }
            camera.addPosition(deltaPosition);

        if (rotationAllowed) {
            var rotation = 0.0f;
            if (Input.isKeyPressed(KeyCode.Q)) {
                rotation -= cameraRotationSpeed * deltaTime;
            } else if (Input.isKeyPressed(KeyCode.E)) {
                rotation += cameraRotationSpeed * deltaTime;
            }
            camera.addRotation(rotation);
        }
        cameraTranslationSpeed = zoomLevel;
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
