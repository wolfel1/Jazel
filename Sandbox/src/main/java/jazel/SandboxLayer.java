package jazel;

import jazel.engine.core.layer.Layer;
import jazel.engine.events.Event;
import jazel.engine.renderer.renderer.RenderCommand;
import jazel.engine.renderer.renderer.Renderer;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class SandboxLayer extends Layer {

    public SandboxLayer() {
        super("jazel.Sandbox");
    }

    @Override
    public void onAttach() {

    }

    @Override
    public void onDetach() {

    }

    @Override
    public void onUpdate() {
        RenderCommand.setClearColor(new Vector4f(0.2f, 0.2f, 0.2f, 1.0f));
        RenderCommand.clear();

        Renderer.beginScene();
        Renderer.drawQuad(new Vector2f(0, 0), new Vector2f(1, 1), new Vector4f(1, 0, 1, 0.5f));
        Renderer.endScene();
    }

    @Override
    public void onEvent(Event event) {

    }

    @Override
    public void onGuiRender() {

    }
}
