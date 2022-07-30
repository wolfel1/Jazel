package jazel.events.mouse;

import jazel.events.Event;
import jazel.events.enumeration.EventCategory;
import jazel.events.enumeration.EventType;
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
