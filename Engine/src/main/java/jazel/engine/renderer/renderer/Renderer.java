package jazel.engine.renderer.renderer;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import jazel.engine.primitives.Quad;
import jazel.engine.renderer.camera.OrthographicCamera;
import jazel.engine.renderer.container.*;
import jazel.engine.renderer.renderer.datastructure.QuadModelData;
import jazel.engine.renderer.renderer.datastructure.QuadVertex;
import jazel.engine.renderer.renderer.datastructure.RenderData;
import jazel.engine.renderer.shader.Shader;
import jazel.engine.renderer.shader.enumeration.ShaderDataType;
import jazel.engine.renderer.texture.Texture;
import jazel.engine.renderer.texture.Texture2D;
import org.joml.Math;
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
        bufferElements.add(new BufferElement("aTexCoord", ShaderDataType.FLOAT2, false));
        bufferElements.add(new BufferElement("aTexIndex", ShaderDataType.FLOAT, false));
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

        int[] samplers = new int[RenderData.MAX_TEXTURE_SLOTS];
        for (int i = 0; i < samplers.length; i++) {
            samplers[i] = i;
        }

        renderData.globalShader = Shader.create("Global");
        assert renderData.globalShader != null;
        renderData.globalShader.bind();
        renderData.globalShader.setIntArray("uTextures", samplers);

        renderData.textureSlots = new Texture2D[RenderData.MAX_TEXTURE_SLOTS];
    }



    public static void beginScene(OrthographicCamera camera) {
        renderData.globalShader.bind();
        renderData.globalShader.setMat4("uViewProjection", camera.getViewProjectionMatrix());
        reset();
    }

    private static void reset() {
        renderData.quadIndexCount = 0;
        renderData.quadVertexIndex = 0;

        renderData.textureSlotIndex = 0;
    }

    public static void drawQuad(Quad quad) {
        drawQuad(quad.getPosVector(), quad.getSizeVector(), quad.getColorVector(), quad.getTexture());
    }

    public static void drawQuad(Vector3f position, Vector2f size, Vector4f color, Texture2D texture) {
        if (renderData.quadIndexCount >= RenderData.MAX_INDICES) {
            flushAndReset();
        }

        int textureIndex = searchTextureSlots(texture);

        var transform = new Matrix4f().translate(position).scale(new Vector3f(size, 0));

        draw(transform, color, textureIndex);
    }

    public static void drawRotatedQuad(Quad quad, float degrees) {
        drawRotatedQuad(quad.getPosVector(), quad.getSizeVector(), quad.getColorVector(), quad.getTexture(), degrees);
    }

    public static void drawRotatedQuad(Vector3f position, Vector2f size, Vector4f color, Texture2D texture, float degrees) {
        if (renderData.quadIndexCount >= RenderData.MAX_INDICES) {
            flushAndReset();
        }

        int textureIndex = searchTextureSlots(texture);

        var transform = new Matrix4f().translate(position)
                .rotateZ(Math.toRadians(degrees))
                .scale(new Vector3f(size, 0));

        draw(transform, color, textureIndex);
    }
    private static int searchTextureSlots(Texture2D texture) {
        int index = -1;
        for (int i = 0; i < renderData.textureSlotIndex; i++) {
            if (renderData.textureSlots[i] != null && renderData.textureSlots[i].equals(texture)) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            if (renderData.textureSlotIndex == RenderData.MAX_TEXTURE_SLOTS) {
                flushAndReset();
            }
            index = renderData.textureSlotIndex;
            renderData.textureSlots[renderData.textureSlotIndex] = texture;
            renderData.textureSlotIndex++;
        }
        return index;
    }

    private static void draw(Matrix4f transformMatrix, Vector4f color, int texIndex) {
        for (var i = 0; i < 4; i++) {
            renderData.quadVertices[renderData.quadVertexIndex] = new QuadVertex();
            renderData.quadVertices[renderData.quadVertexIndex].position = convertVec4ToVec3(
                    transformMatrix.transform(QuadModelData.vertexPositions[i], new Vector4f()));
            renderData.quadVertices[renderData.quadVertexIndex].color = color;
            renderData.quadVertices[renderData.quadVertexIndex].texCoord = QuadModelData.textureCoordinates[i];
            renderData.quadVertices[renderData.quadVertexIndex].texIndex = texIndex;
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
        for (int i = 0; i < renderData.textureSlotIndex; i++) {
            renderData.textureSlots[i].bind(i);
        }

        RenderCommand.drawIndexed(renderData.quadVertexArray, renderData.quadIndexCount);
    }

    public static void shutdown() {
        renderData.quadVertexBuffer.destroy();
        renderData.globalShader.destroy();
    }

    public static void onWindowResize(int width, int height) {
        RenderCommand.setViewport(0,0, width, height);
    }
}
