package jazel.engine.primitives;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class Quad {
    public final float[] pos = new float[]{0, 0};
    public final float[] size = new float[]{1, 1};
    public final float[] color = new float[]{1, 1, 1, 1.0f};
}
