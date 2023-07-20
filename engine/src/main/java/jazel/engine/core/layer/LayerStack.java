package jazel.engine.core.layer;

import lombok.Getter;

import java.util.LinkedList;

/**
 * Class which manages the layers
 */
public class LayerStack {

    @Getter
    private final LinkedList<Layer> layers = new LinkedList<>();
    private int layerInsertIndex = 0;

    public void pushLayer(Layer layer) {
        layers.add(layerInsertIndex, layer);
        layerInsertIndex++;
    }

    public void pushOverlay(Layer overlay) {
        layers.addLast(overlay);
    }

    public void popLayer(Layer layer) {
        layer.onDetach();
        layers.remove(layer);
    }

    public void popOverlay(Layer overlay) {
        overlay.onDetach();
        layers.remove(overlay);
    }
}
