package jazel;

import jazel.engine.objects.Sprite;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class Box extends Sprite {

    public Box() {
        super();
        quad.setPos(new Vector2f(-1, 0));
        quad.setColor(new Vector4f(0.5f, 0.2f, 0.8f, 1));
    }

    @Override
    public void act() {

    }
}
