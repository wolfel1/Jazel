package jazel.engine.renderer.shader;

import jazel.engine.core.Core;
import jazel.engine.renderer.shader.enumeration.ShaderDataType;

public final class ShaderUtils {
  public static int shaderDataTypeSize(ShaderDataType type) {
    switch (type) {
      case FLOAT:
      case INT:
        return 4;
      case FLOAT2:
      case INT2:
        return 4 * 2;
      case FLOAT3:
      case INT3:
        return 4 * 3;
      case FLOAT4:
      case INT4:
        return 4 * 4;
      case MAT3:     return 4 * 3 * 3;
      case MAT4:     return 4 * 4 * 4;
      case BOOL:     return 1;
    }

    Core.assertion(false,"Unknown ShaderDataType!");
    return 0;
  }
}
