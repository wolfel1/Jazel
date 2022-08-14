package jazel.engine.core.layer;

import jazel.engine.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class Layer {

    protected String debugName;

    public abstract void onAttach();

    public abstract void onDetach();

    public abstract void onUpdate();

    public abstract void onEvent(Event event);

    public abstract void onGuiRender();
}
