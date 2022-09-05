package jazel.engine.renderer.camera;

import lombok.Getter;
import lombok.Setter;
import org.joml.Matrix4f;
import org.joml.Vector3f;
@Getter
public class OrthographicCamera {

    private Matrix4f projectionMatrix;
    private Matrix4f viewMatrix;
    private Matrix4f viewProjectionMatrix;

    private Vector3f position = new Vector3f(0, 0,0);
    private float rotation = 0.0f;

    public OrthographicCamera(float left, float right, float bottom, float top) {
        projectionMatrix = new Matrix4f().ortho(left, right, bottom, top, -1.0f, 1.0f);
        viewMatrix = new Matrix4f();

        viewProjectionMatrix = projectionMatrix.mul(viewMatrix);
    }

    public void setProjection(float left, float right, float bottom, float top) {
        projectionMatrix = projectionMatrix.setOrtho(left, right, top, bottom, -1, 1);
        viewProjectionMatrix = projectionMatrix.mul(viewMatrix);
    }

    public void setPosition(Vector3f position) {
        this.position = position;
        recalculateViewMatrix();
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
        recalculateViewMatrix();
    }

    private void recalculateViewMatrix() {
        var transform = new Matrix4f().translate(position).rotate(rotation, new Vector3f(0,0,1));

        viewMatrix = transform.invert();
        viewProjectionMatrix = projectionMatrix.mul(viewMatrix);
    }
}
