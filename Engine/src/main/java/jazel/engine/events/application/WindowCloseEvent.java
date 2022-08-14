package jazel.engine.events.application;

import jazel.engine.events.Event;
import jazel.engine.events.enumeration.EventCategory;
import jazel.engine.events.enumeration.EventType;

public class WindowCloseEvent extends Event {

    public WindowCloseEvent() {
        super(EventType.WINDOW_CLOSE, EventCategory.APPLICATION);
    }
}
