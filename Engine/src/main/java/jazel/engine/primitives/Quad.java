package jazel.engine.primitives;

import jazel.engine.renderer.texture.Texture;
import jazel.engine.renderer.texture.Texture2D;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

@Getter
public final class Quad {

    private static Texture2D WHITE_TEXTURE;
    private float[] pos = new float[]{0, 0, 0};
    private float[] size = new float[]{1, 1};
    private float[] color = new float[]{1, 1, 1, 1.0f};
    @Setter private Texture2D texture;

    public Quad() {
        if(WHITE_TEXTURE == null) {
            setDefaultTexture();
        }
        texture = WHITE_TEXTURE;
    }

    private void setDefaultTexture() {
        WHITE_TEXTURE = Texture2D.create(1,1);
        assert WHITE_TEXTURE != null;
        int[] textureData = new int[]{ 0xffffffff };
        WHITE_TEXTURE.setData(textureData);
    }

    public Vector3f getPosVector() {
        return new Vector3f(pos);
    }

    public Vector2f getSizeVector() {
        return new Vector2f(size);
    }

    public Vector4f getColorVector() {
        return new Vector4f(color);
    }

    public void setPos(Vector3f pos) {
        this.pos[0] = pos.x;
        this.pos[1] = pos.y;
        this.pos[2] = pos.z;
    }

    public void setPos(Vector2f pos) {
        this.pos[0] = pos.x;
        this.pos[1] = pos.y;
        this.pos[2] = 0;
    }

    public void setSize(Vector2f size) {
        this.size[0] = size.x;
        this.size[1] = size.y;
    }

    public void setColor(Vector4f color) {
        this.color[0] = color.x;
        this.color[1] = color.y;
        this.color[2] = color.z;
        this.color[3] = color.w;
    }
}
