package pong;

import jazel.engine.core.Input;
import jazel.engine.core.encoding.KeyCode;
import jazel.engine.renderer.camera.OrthographicCameraController;
import lombok.Getter;
import lombok.Setter;
import org.joml.Math;
import org.joml.Vector3f;

public class CameraController extends OrthographicCameraController {

    public CameraController(float width, float height) {
        super(width, height);
    }

    public void update(float deltaTime) {

    }
}
