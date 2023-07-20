package jazel.engine.renderer.utils;

import org.joml.Vector3f;
import org.joml.Vector4f;

public class VectorUtils {

    public static Vector3f convertVec4ToVec3(Vector4f input) {
        return new Vector3f(input.x, input.y, input.z);
    }
}
