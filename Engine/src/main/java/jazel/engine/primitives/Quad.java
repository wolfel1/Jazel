package jazel.engine.primitives;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

@Getter
@NoArgsConstructor
public final class Quad {
    private float[] pos = new float[]{0, 0, 0};
    private float[] size = new float[]{1, 1};
    private float[] color = new float[]{1, 1, 1, 1.0f};

    public Quad(Vector2f pos, Vector2f size, Vector4f color) {
        this.pos = new float[]{pos.x,pos.y, 0};
        this.size = new float[]{size.x, size.y};
        this.color = new float[]{color.x, color.y, color.z, color.w};
    }

    public Quad(Vector3f pos, Vector2f size, Vector4f color) {
        this.pos = new float[]{pos.x,pos.y, pos.z};
        this.size = new float[]{size.x, size.y};
        this.color = new float[]{color.x, color.y, color.z, color.w};
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
