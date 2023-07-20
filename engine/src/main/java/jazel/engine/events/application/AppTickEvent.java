package jazel.engine.events.application;

import jazel.engine.events.Event;
import jazel.engine.events.enumeration.EventCategory;
import jazel.engine.events.enumeration.EventType;

public class AppTickEvent extends Event {

    public AppTickEvent() {
        super(EventType.APP_TICK, EventCategory.APPLICATION);
    }
}
