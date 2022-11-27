package jazel.engine.renderer.shader;

import jazel.engine.core.Log;
import jazel.engine.renderer.renderer.RendererAPI;
import jazel.platform.opengl.container.OpenGLVertexBuffer;
import jazel.platform.opengl.shader.OpenGLShader;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.List;

@NoArgsConstructor
public abstract class Shader {

    protected static final String rootPath = "assets/shaders/";

    protected int rendererID;
    @Getter
    protected String name;

    public Shader(String name) {
        this.name = name;
    }

    public abstract void bind();

    public abstract void unbind();

    public abstract void setInt(String name, int value);

    public abstract void setIntArray(String name, int[] value);

    public abstract void setFloat(String name, float value);

    public abstract void setFloat2(String name, Vector2f value);

    public abstract void setFloat3(String name, Vector3f value);

    public abstract void setFloat4(String name, Vector4f value);

    public abstract void setMat4(String name, Matrix4f value);

    public abstract void destroy();

    public static Shader create(String name) {
        switch (RendererAPI.getAPI()) {
        case NONE -> {
            Log.getCoreLogger().error("RendererAPI NONE is not supported!");
            return null;
        }
        case OPENGL -> {
            return new OpenGLShader(name);
        }
        }

        Log.getCoreLogger().error("Unknown RendererAPI!");
        return null;
    }

    public static Shader createFromFile(String vertexPath, String fragmentPath) {

        switch (RendererAPI.getAPI()) {
        case NONE -> {
            Log.getCoreLogger().error("RendererAPI NONE is not supported!");
            return null;
        }
        case OPENGL -> {
            return new OpenGLShader(vertexPath, fragmentPath);
        }
        }

        Log.getCoreLogger().error("Unknown RendererAPI!");
        return null;
    }

    public static Shader createFromFiles(List<String> shaderPaths) {

        switch (RendererAPI.getAPI()) {
        case NONE -> {
            Log.getCoreLogger().error("RendererAPI NONE is not supported!");
            return null;
        }
        case OPENGL -> {
            return new OpenGLShader(shaderPaths);
        }
        }

        Log.getCoreLogger().error("Unknown RendererAPI!");
        return null;
    }
}
