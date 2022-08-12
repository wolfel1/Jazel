package jazel.events.application;

import jazel.events.Event;
import jazel.events.enumeration.EventCategory;
import jazel.events.enumeration.EventType;

public class AppTickEvent extends Event {

    public AppTickEvent() {
        super(EventType.APP_TICK, EventCategory.APPLICATION);
    }
}
