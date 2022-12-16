package jazel.engine.objects;

import jazel.engine.objects.primitives.Quad;
import jazel.engine.renderer.renderer.Renderer;
import jazel.engine.renderer.texture.SubTexture;
import jazel.engine.renderer.texture.Texture;
import org.joml.Vector2f;

public abstract class Actor {

    protected Quad quad;

    public Actor() {
        this.quad = new Quad();
    }

    public Actor(Vector2f initialPos) {
        this.quad = new Quad();
        quad.setPos(initialPos);
    }

    public abstract void act(float deltaTime);

    public void draw() {
        if (!isRotated()) {
            Renderer.draw(quad.getPosVector(), quad.getSizeVector(), quad.getColorVector());
        } else {
            Renderer.drawRotated(quad.getPosVector(), quad.getSizeVector(), quad.getColorVector(), quad.getRotation());
        }
    }

    public void draw(Texture texture) {
        if (!isRotated()) {
            Renderer.draw(quad.getPosVector(), quad.getSizeVector(), quad.getColorVector(), texture);
        } else {
            Renderer.drawRotated(quad.getPosVector(), quad.getSizeVector(), quad.getColorVector(), quad.getRotation(), texture);
        }
    }

    public void draw(SubTexture subTexture) {
        if (!isRotated()) {
            Renderer.draw(quad.getPosVector(), quad.getSizeVector(), quad.getColorVector(), subTexture);
        } else {
            Renderer.drawRotated(quad.getPosVector(), quad.getSizeVector(), quad.getColorVector(), quad.getRotation(), subTexture);
        }
    }

    private boolean isRotated() {
        var rotation = quad.getRotation();
        return rotation != 0 || rotation != 360;
    }

    public void destroy() {
        quad.destroy();
    }

}
