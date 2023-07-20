package jazel;

import jazel.engine.core.Input;
import jazel.engine.core.encoding.KeyCode;
import jazel.engine.objects.Actor;
import lombok.Setter;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class Box extends Actor {

    @Setter
    private float moveSpeed = 2;

    @Setter
    private float rotateSpeed = 180;

    public Box() {
        super();
        quad.setPos(new Vector2f(-1, 0));
        quad.scale(0.2f);
        quad.setColor(new Vector4f(1f, 1f, 1f, 1));
    }

    @Override
    public void act(float deltaTime) {
        if (Input.isKeyPressed(KeyCode.A)) {
            quad.moveLeft(moveSpeed * deltaTime);
        } else if (Input.isKeyPressed(KeyCode.D)) {
            quad.moveRight(moveSpeed * deltaTime);
        }

        if (Input.isKeyPressed(KeyCode.W)) {
            quad.moveUp(moveSpeed * deltaTime);
        } else if (Input.isKeyPressed(KeyCode.S)) {
            quad.moveDown(moveSpeed * deltaTime);
        }

        if (Input.isKeyPressed(KeyCode.Q)) {
            quad.rotateLeft(rotateSpeed * deltaTime);
        } else if (Input.isKeyPressed(KeyCode.E)) {
            quad.rotateRight(rotateSpeed * deltaTime);
        }
    }
}
