package jazel;

import imgui.ImGui;
import jazel.engine.core.layer.Layer;
import jazel.engine.primitives.Quad;
import jazel.engine.events.Event;
import jazel.engine.renderer.camera.OrthographicCameraController;
import jazel.engine.renderer.renderer.RenderCommand;
import jazel.engine.renderer.renderer.Renderer;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class SandboxLayer extends Layer {

    private OrthographicCameraController cameraController;
    private Quad firstQuad;
    public SandboxLayer() {
        super("jazel.Sandbox");
        cameraController = new OrthographicCameraController(1920.0f / 1080.0f, true, true);

    }

    @Override
    public void onAttach() {
        firstQuad = new Quad();
    }

    @Override
    public void onDetach() {

    }

    @Override
    public void onUpdate(float deltaTime) {
        RenderCommand.setClearColor(new Vector4f(0.2f, 0.2f, 0.2f, 1.0f));
        RenderCommand.clear();

        cameraController.onUpdate(deltaTime);

        Renderer.beginScene(cameraController.getCamera());
        Renderer.drawQuad(firstQuad);
        Renderer.drawRotatedQuad(firstQuad, 45.0f);
        Renderer.endScene();
    }

    @Override
    public void onEvent(Event event) {

    }

    @Override
    public void onGuiRender() {
        ImGui.begin("Quad Data");

        ImGui.dragFloat2("Position", firstQuad.getPos(), 0.1f, -2.0f, 2.0f);
        ImGui.dragFloat2("Size", firstQuad.getSize(), 0.1f, -2.0f, 2.0f);
        ImGui.colorEdit4("Color", firstQuad.getColor());

        ImGui.end();
    }
}
