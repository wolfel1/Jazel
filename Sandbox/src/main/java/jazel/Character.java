package jazel;

import jazel.engine.objects.Actor;
import jazel.engine.renderer.texture.SubTexture;
import jazel.engine.renderer.texture.Texture;
import org.joml.Vector2f;

public class Character extends Actor {

    private Texture spritesheet;
    private SubTexture front;

    public Character() {
        spritesheet = Texture.create("spritesheet.png");
        front = SubTexture.create(spritesheet, new Vector2f(0, 0), new Vector2f(256, 320));
    }

    @Override
    public void act(float deltaTime) {

    }

    @Override
    public void draw() {
        super.draw(front);
    }

    @Override
    public void destroy() {
        super.destroy();
        spritesheet.destroy();
    }
}
