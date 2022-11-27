package jazel;

import imgui.ImGui;
import imgui.type.ImString;
import jazel.engine.core.layer.Layer;
import jazel.engine.primitives.Quad;
import jazel.engine.events.Event;
import jazel.engine.renderer.camera.OrthographicCameraController;
import jazel.engine.renderer.renderer.RenderCommand;
import jazel.engine.renderer.renderer.Renderer;
import jazel.engine.renderer.texture.SubTexture;
import jazel.engine.renderer.texture.Texture;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class SandboxLayer extends Layer {

    private OrthographicCameraController cameraController;
    private Quad firstQuad;
    private Quad texturedQuad;

    private Texture spritesheet;
    private Quad spritesheetQuad;
    private SubTexture front;

    private final ImString outputName = new ImString();

    public SandboxLayer() {
        super("jazel.Sandbox");
    }

    @Override
    public void onAttach() {
        cameraController = new OrthographicCameraController(1920.0f / 1080.0f);
        cameraController.setAllowMove(true);
        cameraController.setAllowZoom(true);
        
        firstQuad = new Quad();
        firstQuad.setPos(new Vector2f(-1,0));
        firstQuad.setColor(new Vector4f(0.5f, 0.2f,0.8f, 1));

        texturedQuad = new Quad();
        texturedQuad.setPos(new Vector2f(1,0));
        texturedQuad.setTexture(Texture.create("Checkerboard.png"));

        spritesheet = Texture.create("spritesheet.png");
        spritesheetQuad = new Quad();
        front = SubTexture.create(spritesheet, new Vector2f(0,0), new Vector2f(256,320));
    }

    @Override
    public void onDetach() {
        firstQuad.destroy();
        texturedQuad.destroy();
        spritesheetQuad.destroy();
        spritesheet.destroy();
    }

    @Override
    public void onUpdate(float deltaTime) {
        RenderCommand.setClearColor(new Vector4f(0.2f, 0.2f, 0.2f, 1.0f));
        RenderCommand.clear();

        cameraController.onUpdate(deltaTime);

        Renderer.beginScene(cameraController.getCamera());
        Renderer.draw(firstQuad.getPosVector(), firstQuad.getSizeVector(), firstQuad.getColorVector());
        Renderer.draw(texturedQuad.getPosVector(), texturedQuad.getSizeVector(), texturedQuad.getColorVector(), texturedQuad.getTexture());
        Renderer.draw(spritesheetQuad.getPosVector(), spritesheetQuad.getSizeVector(), spritesheetQuad.getColorVector(), front);
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
