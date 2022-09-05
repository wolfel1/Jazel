package jazel.engine.renderer.renderer;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import jazel.engine.renderer.container.*;
import jazel.engine.renderer.renderer.datastructure.QuadModelData;
import jazel.engine.renderer.renderer.datastructure.QuadVertex;
import jazel.engine.renderer.renderer.datastructure.RenderData;
import jazel.engine.renderer.shader.Shader;
import jazel.engine.renderer.shader.enumeration.ShaderDataType;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import static jazel.engine.renderer.utils.VectorUtils.convertVec4ToVec3;

public class Renderer {

    public static RenderData renderData = new RenderData();

    public static void init() {
        RenderCommand.init();

        renderData.quadVertexArray = VertexArray.create();

        renderData.quadVertexBuffer = VertexBuffer.create((long) RenderData.MAX_VERTICES * QuadVertex.getSize());

        var bufferElements = new ArrayList<BufferElement>();
        bufferElements.add(new BufferElement("aPosition", ShaderDataType.FLOAT3, false));
        bufferElements.add(new BufferElement("aColor", ShaderDataType.FLOAT4, false));
        // bufferElements.add(new BufferElement("aTexCoord", ShaderDataType.FLOAT2, false));
        // bufferElements.add(new BufferElement("aTexIndex", ShaderDataType.FLOAT, false));
        // bufferElements.add(new BufferElement("aTilingFactor", ShaderDataType.FLOAT, false));
        renderData.quadVertexBuffer.setLayout(new BufferLayout(bufferElements));

        renderData.quadVertexArray.addVertexBuffer(renderData.quadVertexBuffer);

        renderData.quadVertices = new QuadVertex[RenderData.MAX_VERTICES];

        int[] quadIndices = new int[RenderData.MAX_INDICES];
        int offset = 0;
        for (int i = 0; i < RenderData.MAX_INDICES; i += 6) {
            quadIndices[i] = offset;
            quadIndices[i + 1] = offset + 1;
            quadIndices[i + 2] = offset + 2;

            quadIndices[i + 3] = offset + 2;
            quadIndices[i + 4] = offset + 3;
            quadIndices[i + 5] = offset;

            offset += 4;
        }

        var quadIndexBuffer = IndexBuffer.create(quadIndices, RenderData.MAX_INDICES);
        renderData.quadVertexArray.setIndexBuffer(quadIndexBuffer);

        renderData.globalShader = Shader.create("Global");
    }

    public static void beginScene() {
        renderData.globalShader.bind();
        reset();
    }

    private static void reset() {
        renderData.quadIndexCount = 0;
        renderData.quadVertexIndex = 0;
    }

    public static void drawQuad(Vector2f position, Vector2f size, Vector4f color) {
        drawQuad(new Vector3f(position, 0), size, color);
    }

    public static void drawQuad(Vector3f position, Vector2f size, Vector4f color) {
        if (renderData.quadIndexCount >= RenderData.MAX_INDICES) {
            flushAndReset();
        }

        var transform = new Matrix4f().translate(position).scale(new Vector3f(size, 0));

        draw(transform, color, 0, 0);
    }

    private static void draw(Matrix4f transformMatrix, Vector4f color, float textureIndex, float tilingFactor) {
        for (var i = 0; i < 4; i++) {
            renderData.quadVertices[renderData.quadVertexIndex] = new QuadVertex();
            renderData.quadVertices[renderData.quadVertexIndex].position = convertVec4ToVec3(
                    transformMatrix.transform(QuadModelData.vertexPositions[i]));
            renderData.quadVertices[renderData.quadVertexIndex].color = color;
            renderData.quadVertices[renderData.quadVertexIndex].texCoord = QuadModelData.textureCoordinates[i];
            renderData.quadVertices[renderData.quadVertexIndex].texIndex = textureIndex;
            renderData.quadVertices[renderData.quadVertexIndex].tilingFactor = tilingFactor;
            renderData.quadVertexIndex++;
        }

        renderData.quadIndexCount += 6;
    }

    private static void flushAndReset() {
        endScene();
        reset();
    }

    public static void endScene() {
        var buffer = FloatBuffer.allocate(RenderData.MAX_VERTICES * QuadVertex.getSize());
        for (int i = 0; i < renderData.quadVertices.length; i++) {
            if (renderData.quadVertices[i] != null) {
                var array = renderData.quadVertices[i].toArray();
                buffer.put(array);
            }
        }
        renderData.quadVertexBuffer.setData(buffer.flip());

        flush();
    }

    private static void flush() {
        if (renderData.quadIndexCount == 0) {
            return;
        }

        RenderCommand.drawIndexed(renderData.quadVertexArray, renderData.quadIndexCount);
    }

    public static void shutdown() {
        renderData.quadVertexBuffer.destroy();
        renderData.globalShader.destroy();
    }

    public static void onWindowResize(int width, int height) {
    }
}
