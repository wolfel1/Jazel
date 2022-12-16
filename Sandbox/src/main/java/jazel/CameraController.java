package jazel;

import jazel.engine.core.Input;
import jazel.engine.core.encoding.KeyCode;
import jazel.engine.renderer.camera.OrthographicCameraController;
import lombok.Getter;
import lombok.Setter;
import org.joml.Math;
import org.joml.Vector3f;

public class CameraController extends OrthographicCameraController {

    @Setter
    private boolean allowRotation = false;
    @Setter
    private boolean allowMove = false;

    @Setter
    @Getter
    private float cameraTranslationSpeed = 5.0f, cameraRotationSpeed = 180.0f;
    public CameraController(float width, float height) {
        super(width, height);
    }

    public void update(float deltaTime) {

        if (allowMove) {
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
        }
        if (allowRotation) {
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
}
