package jazel.engine.renderer.texture;

import lombok.Getter;
import org.joml.Vector2f;

@Getter
public class SubTexture {
    private Texture texture;

    private Vector2f coordinates[] = new Vector2f[4];

    public SubTexture(Texture texture, Vector2f min, Vector2f max) {
        this.texture = texture;

        coordinates[0] = new Vector2f(min.x, min.y);
        coordinates[1] = new Vector2f(max.x, min.y);
        coordinates[2] = new Vector2f(max.x, max.y);
        coordinates[3] = new Vector2f(min.x, max.y);
    }

    public static SubTexture create(Texture texture, Vector2f coordinates, Vector2f cellSize) {
        Vector2f min = new Vector2f (coordinates.x / texture.getWidth(), coordinates.y / texture.getHeight() );
        Vector2f max = new Vector2f ((coordinates.x +  cellSize.x) / texture.getWidth(), (coordinates.y + cellSize.y) / texture.getHeight() );
        return new SubTexture(texture, min, max);
    }


}
