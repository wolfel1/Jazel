package jazel.events.mouse;

import jazel.events.Event;
import jazel.events.enumeration.EventCategory;
import jazel.events.enumeration.EventType;
import lombok.Getter;

@Getter
public class MouseScrolledEvent extends Event {

    private final float offsetX;
    private final float offsetY;

    public MouseScrolledEvent(float offsetX, float offsetY) {
        super(EventType.MOUSE_SCROLLED, EventCategory.MOUSE);
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    @Override
    public String toString() {
        return "MouseScrolledEvent: " + offsetX + ", " + offsetY;
    }
}
