#version 330 core

layout(location = 0) in vec3 aPosition;
layout(location = 1) in vec4 aColor;
//layout(location = 2) in vec2 aTexCoord;
//layout(location = 3) in float aTexIndex;
//layout(location = 4) in float aTilingFactor;

//uniform mat4 uViewProjection;
//
out vec4 vColor;
//out vec2 vTexCoord;
//out float vTexIndex;
//out float vTilingFactor;

void main()
{
//    vTexIndex = aTexIndex;
//    vTilingFactor = aTilingFactor;
    vColor = aColor;
//    vTexCoord = aTexCoord;
    gl_Position = vec4(aPosition, 1.0);//uViewProjection * vec4(aPosition, 1.0);
}