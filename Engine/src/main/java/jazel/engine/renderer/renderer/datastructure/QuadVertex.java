package jazel.engine.renderer.renderer.datastructure;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.system.MemoryUtil;

public final class QuadVertex {

    public Vector3f position;
    public Vector4f color;
    public Vector2f texCoord;
    public float texIndex;

    public static int getSize() {
        return (3 + 4 + 2 + 1) * Float.SIZE / 8;
    }

    public float[] toArray() {
        return new float[] { position.x, position.y, position.z,
                 color.x,
                 color.y,
                 color.z,
                 color.w,
                texCoord.x,
                texCoord.y,
                texIndex
        };
    }
}
