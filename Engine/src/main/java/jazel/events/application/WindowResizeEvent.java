package jazel.events.application;

import jazel.events.Event;
import jazel.events.enumeration.EventCategory;
import jazel.events.enumeration.EventType;
import lombok.Getter;

@Getter
public class WindowResizeEvent extends Event {

    private final int width;
    private final int height;

    public WindowResizeEvent(int width, int height) {
        super(EventType.WINDOW_RESIZE, EventCategory.APPLICATION);
        this.width = width;
        this.height = height;
    }

    @Override
    public String toString() {
        return "WindowResizeEvent: " + width + ", " + height;
    }
}
