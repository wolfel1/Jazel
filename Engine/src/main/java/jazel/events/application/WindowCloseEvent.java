package jazel.events.application;

import jazel.events.Event;
import jazel.events.enumeration.EventCategory;
import jazel.events.enumeration.EventType;
import lombok.Getter;

public class WindowCloseEvent extends Event {

    public WindowCloseEvent() {
        super(EventType.WINDOW_CLOSE, EventCategory.APPLICATION);
    }
}
