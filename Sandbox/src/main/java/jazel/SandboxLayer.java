package jazel;

import imgui.ImGui;
import imgui.type.ImString;
import jazel.engine.core.layer.Layer;
import jazel.engine.objects.primitives.Quad;
import jazel.engine.events.Event;
import jazel.engine.renderer.renderer.RenderCommand;
import jazel.engine.renderer.renderer.Renderer;
import jazel.engine.renderer.texture.SubTexture;
import jazel.engine.renderer.texture.Texture;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class SandboxLayer extends Layer {

    private CameraController cameraController;
    private Box box;
    private Character character;

    private final ImString outputName = new ImString();

    public SandboxLayer() {
        super("jazel.Sandbox");
    }

    @Override
    public void onAttach() {
        cameraController = new CameraController(1920.0f, 1080.0f);
        cameraController.setAllowMove(true);
        cameraController.setAllowZoom(true);

        box = new Box();

        character = new Character();
    }

    @Override
    public void onDetach() {
        box.destroy();
        character.destroy();
    }

    @Override
    public void onUpdate(float deltaTime) {
        RenderCommand.setClearColor(new Vector4f(0.2f, 0.2f, 0.2f, 1.0f));
        RenderCommand.clear();

        cameraController.onUpdate(deltaTime);

        Renderer.beginScene(cameraController.getCamera());
        box.draw();
        character.draw();
        Renderer.endScene();
    }

    @Override
    public void onEvent(Event event) {

    }

    @Override
    public void onGuiRender() {
        ImGui.begin("Quad Data");
        ImGui.inputText("Image name", outputName);
        if (ImGui.button("Render")) {
            RenderCommand.renderImage(outputName.get());
        }

        ImGui.end();
    }
}
