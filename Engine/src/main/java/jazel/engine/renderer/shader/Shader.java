package jazel.engine.renderer.shader;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.List;

@AllArgsConstructor
public abstract class Shader {

    @Getter protected String name;

    public abstract void bind();
    public abstract void unbind();

    public abstract void setInt(String name, int value);
    public abstract void setIntArray(String name, int[] value, int count);
    public abstract void setFloat(String name, float value);
    public abstract void setFloat3(String name, Vector3f value);
    public abstract void setFloat4(String name, Vector4f value);
    public abstract void setMat4(String name, Matrix4f value);

    public static Shader create(String name) {
        return null;
    }

    public static Shader createFromFile(String vertexPath, String fragmentPath) {
        return null;
    }

    public static Shader createFromFiles(List<String> shaderPaths) {
        return null;
    }
}
