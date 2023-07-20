package pong;

import jazel.engine.core.layer.Layer;
import jazel.engine.events.Event;
import jazel.engine.renderer.renderer.RenderCommand;
import jazel.engine.renderer.renderer.Renderer;

public class GameLayer extends Layer {

    private Player player;
    private Computer computer;
    private CameraController cameraController;
    public GameLayer(String debugName) {
        super(debugName);
    }

    @Override
    public void onAttach() {
        cameraController = new CameraController(Pong.WIDTH, Pong.HEIGHT);

        player = new Player();
        computer = new Computer();
    }

    @Override
    public void onDetach() {
        player.destroy();
        computer.destroy();
    }

    @Override
    public void onUpdate(float deltaTime) {
        RenderCommand.clear();

        player.act(deltaTime);
        computer.act(deltaTime);
        cameraController.update(deltaTime);

        Renderer.beginScene(cameraController.getCamera());
        player.draw();
        computer.draw();
        Renderer.endScene();
    }

    @Override
    public void onEvent(Event event) {

    }

    @Override
    public void onGuiRender() {

    }
}
