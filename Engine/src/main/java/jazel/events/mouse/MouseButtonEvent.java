package jazel.events.mouse;

import jazel.events.Event;
import jazel.events.enumeration.EventCategory;
import jazel.events.enumeration.EventType;
import lombok.Getter;

@Getter
public abstract class MouseButtonEvent extends Event {

    protected final int button;

    protected MouseButtonEvent(int button, EventType type) {
        super(type, EventCategory.MOUSE);
        this.button = button;
    }
}
