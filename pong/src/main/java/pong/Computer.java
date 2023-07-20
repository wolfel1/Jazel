package pong;

import jazel.engine.core.Input;
import jazel.engine.core.encoding.KeyCode;
import jazel.engine.objects.Actor;
import lombok.Setter;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class Computer extends Actor {

    @Setter
    private float moveSpeed = 2;

    public Computer() {
        super();
        quad.setPos(new Vector2f(1.2f, 0));
        quad.scale(new Vector2f(0.05f, 0.25f));
        quad.setColor(new Vector4f(1f, 1f, 1f, 1));
    }

    @Override
    public void act(float deltaTime) {
    }
}
