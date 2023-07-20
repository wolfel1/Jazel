package jazel.engine.core.layer;

import jazel.engine.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Base class for each layer which is rendered.
 */
@AllArgsConstructor
@Getter
public abstract class Layer {

    /**
     * Debug name for the layer, only needed for debug purposes.
     */
    protected String debugName;

    /**
     * Called when the layer will be pushed tho the app.
     */
    public abstract void onAttach();

    /**
     * Called to release the resources which are used in the layer.
     */
    public abstract void onDetach();

    /**
     * Called to render the layer.
     * @param deltaTime The time in seconds since the last render.
     */
    public abstract void onUpdate(float deltaTime);

    /**
     * Called to address a event
     * @param event
     */
    public abstract void onEvent(Event event);

    /**
     * Called to render ImGui controls for this layer
     */
    public abstract void onGuiRender();
}
