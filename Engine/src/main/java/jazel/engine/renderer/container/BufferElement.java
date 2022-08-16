package jazel.engine.renderer.container;

import jazel.engine.core.Core;
import jazel.engine.renderer.shader.ShaderUtils;
import jazel.engine.renderer.shader.enumeration.ShaderDataType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class BufferElement {

  private String name;
  private ShaderDataType shaderDataType;
  @Getter private int size;
  @Setter private int  offset;
  private boolean normalized;

  public BufferElement(String name, ShaderDataType shaderDataType, boolean normalized) {
    this.name = name;
    this.shaderDataType = shaderDataType;
    this.normalized = normalized;
    this.size = ShaderUtils.shaderDataTypeSize(shaderDataType);
    this.offset = 0;
  }

  public int getComponentCount() {
    switch (shaderDataType) {
      case FLOAT:
      case INT:
      case BOOL:
        return 1;
      case FLOAT2:
      case INT2:
        return 2;
      case FLOAT3:
      case MAT3:
      case INT3:
        return 3;
      case FLOAT4:
      case MAT4:
      case INT4:
        return 4;
    }

    Core.assertion(false, "Unknown ShaderDataType!");
    return 0;
  }
}
