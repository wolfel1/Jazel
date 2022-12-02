package jazel.engine.objects;

import jazel.engine.objects.primitives.Quad;
import jazel.engine.renderer.renderer.Renderer;
import jazel.engine.renderer.texture.SubTexture;
import jazel.engine.renderer.texture.Texture;
import org.joml.Vector2f;

public abstract class Sprite {

    protected Quad quad;

    public Sprite() {
        this.quad = new Quad();
    }

    public Sprite(Vector2f initialPos) {
        this.quad = new Quad();
        quad.setPos(initialPos);
    }

    public abstract void act();

    public void draw() {
        Renderer.draw(quad.getPosVector(), quad.getSizeVector(), quad.getColorVector());
    }

    public void draw(Texture texture) {
        Renderer.draw(quad.getPosVector(), quad.getSizeVector(), quad.getColorVector(), texture);
    }

    public void draw(SubTexture subTexture) {
        Renderer.draw(quad.getPosVector(), quad.getSizeVector(), quad.getColorVector(), subTexture);
    }

    public void destroy() {
        quad.destroy();
    }
}
