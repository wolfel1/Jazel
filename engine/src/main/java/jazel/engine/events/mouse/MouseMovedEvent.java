package jazel.engine.events.mouse;

import jazel.engine.events.Event;
import jazel.engine.events.enumeration.EventCategory;
import jazel.engine.events.enumeration.EventType;
import lombok.Getter;

@Getter
public class MouseMovedEvent extends Event {

    private final float mouseX;
    private final float mouseY;

    public MouseMovedEvent(float x, float y) {
        super(EventType.MOUSE_MOVED, EventCategory.MOUSE);
        mouseX = x;
        mouseY = y;
    }

    @Override
    public String toString() {
        return "MouseMovedEvent: " + mouseX + ", " + mouseY;
    }
}
