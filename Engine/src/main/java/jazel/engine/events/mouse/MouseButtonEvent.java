package jazel.engine.events.mouse;

import jazel.engine.events.Event;
import jazel.engine.events.enumeration.EventCategory;
import jazel.engine.events.enumeration.EventType;
import lombok.Getter;

@Getter
public abstract class MouseButtonEvent extends Event {

    protected final int button;

    protected MouseButtonEvent(int button, EventType type) {
        super(type, EventCategory.MOUSE);
        this.button = button;
    }
}
