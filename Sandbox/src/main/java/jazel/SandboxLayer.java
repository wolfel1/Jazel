package jazel;

import imgui.ImGui;
import jazel.engine.core.layer.Layer;
import jazel.engine.primitives.Quad;
import jazel.engine.events.Event;
import jazel.engine.renderer.renderer.RenderCommand;
import jazel.engine.renderer.renderer.Renderer;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class SandboxLayer extends Layer {


    private Quad firstQuad;
    public SandboxLayer() {
        super("jazel.Sandbox");
    }

    @Override
    public void onAttach() {
        firstQuad = new Quad();
    }

    @Override
    public void onDetach() {

    }

    @Override
    public void onUpdate() {
        RenderCommand.setClearColor(new Vector4f(0.2f, 0.2f, 0.2f, 1.0f));
        RenderCommand.clear();

        Renderer.beginScene();
        Renderer.drawQuad(new Vector2f(firstQuad.pos), new Vector2f(firstQuad.size), new Vector4f(firstQuad.color));
        Renderer.endScene();
    }

    @Override
    public void onEvent(Event event) {

    }

    @Override
    public void onGuiRender() {
        ImGui.begin("Quad Data");

        ImGui.colorEdit4("Color", firstQuad.color);

        ImGui.end();
    }
}
