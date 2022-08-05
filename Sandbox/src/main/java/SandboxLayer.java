import jazel.core.layer.Layer;
import jazel.events.Event;
import jazel.renderer.RenderCommand;
import org.joml.Vector4f;

public class SandboxLayer extends Layer {

  public SandboxLayer() {
    super("Sandbox");
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
  }

  @Override
  public void onEvent(Event event) {

  }

  @Override
  public void onGuiRender() {

  }
}
