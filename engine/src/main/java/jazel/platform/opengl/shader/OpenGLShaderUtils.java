package jazel.platform.opengl.shader;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_INT;
import static org.lwjgl.opengl.GL20.*;

import jazel.engine.core.Log;
import jazel.engine.renderer.shader.ShaderUtils;
import jazel.engine.renderer.shader.enumeration.ShaderDataType;

public class OpenGLShaderUtils extends ShaderUtils {
    public static int shaderDataTypeToOpenGLBaseType(ShaderDataType type) {
        switch (type) {
        case FLOAT:
        case FLOAT2:
        case FLOAT3:
        case FLOAT4:
        case MAT3:
        case MAT4:
            return GL_FLOAT;
        case INT:
        case INT2:
        case INT3:
        case INT4:
            return GL_INT;
        case BOOL:
            return GL_BOOL;
        }

        Log.getCoreLogger().error("Unknown ShaderDataType!");
        return 0;
    }

    public static int getShaderTypeFromString(String type) {
        if (type.equals("vertex") || type.equals("vert")) {
            return GL_VERTEX_SHADER;
        }
        if (type.equals("fragment") || type.equals("frag")) {
            return GL_FRAGMENT_SHADER;
        }

        Log.getCoreLogger().error("Unknown shader type!");
        return 0;
    }
}
