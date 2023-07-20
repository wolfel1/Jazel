package jazel.engine.renderer.camera;

import lombok.Getter;
import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector3f;

@Getter
public class OrthographicCamera {

    private Matrix4f projectionMatrix;
    private Matrix4f viewMatrix;
    private Matrix4f viewProjectionMatrix = new Matrix4f();

    private Vector3f position = new Vector3f(0, 0, 0);
    private float rotation = 0.0f;

    public OrthographicCamera(float left, float right, float bottom, float top) {
        projectionMatrix = new Matrix4f().setOrtho(left, right, bottom, top, -1.0f, 1.0f);
        viewMatrix = new Matrix4f();

        projectionMatrix.mul(viewMatrix, viewProjectionMatrix);
    }

    public void setProjection(float left, float right, float bottom, float top) {
        projectionMatrix = projectionMatrix.setOrtho(left, right, bottom, top, -1, 1);
        projectionMatrix.mul(viewMatrix, viewProjectionMatrix);
    }

    public void addPosition(Vector3f deltaPosition) {
        this.position.add(deltaPosition);
        recalculateViewMatrix();
    }

    public void addRotation(float deltaRotation) {
        this.rotation += deltaRotation;
        recalculateViewMatrix();
    }

    private void recalculateViewMatrix() {
        var transform = new Matrix4f().translate(position).rotate(Math.toRadians(rotation), new Vector3f(0, 0, 1));

        viewMatrix = transform.invert();
        projectionMatrix.mul(viewMatrix, viewProjectionMatrix);
    }
}
