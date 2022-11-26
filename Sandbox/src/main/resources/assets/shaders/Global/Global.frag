#version 330 core

layout(location = 0) out vec4 color;

in vec4 vColor;
in vec2 vTexCoord;
in float vTexIndex;

uniform sampler2D uTextures[32];

void main()
{
    color = texture(uTextures[int(vTexIndex)], vTexCoord * 1) * vColor;
}