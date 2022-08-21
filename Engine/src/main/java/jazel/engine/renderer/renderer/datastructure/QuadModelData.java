package jazel.engine.renderer.renderer.datastructure;

import org.joml.Vector2f;
import org.joml.Vector4f;

public final class QuadModelData {
    public static final Vector4f[] vertexPositions = new Vector4f[]{
        new Vector4f(-0.5f, -0.5f, 0.0f, 1.0f ),
        new Vector4f(0.5f, -0.5f, 0.0f, 1.0f ),
        new Vector4f(0.5f,  0.5f, 0.0f, 1.0f ),
        new Vector4f(-0.5f,  0.5f, 0.0f, 1.0f)
    };

    public static final Vector2f[] textureCoordinates = new Vector2f[]{
        new Vector2f(0.0f, 0.0f ),
        new Vector2f(1.0f, 0.0f ),
        new Vector2f(1.0f, 1.0f ),
        new Vector2f(0.0f, 1.0f )
    };
}
