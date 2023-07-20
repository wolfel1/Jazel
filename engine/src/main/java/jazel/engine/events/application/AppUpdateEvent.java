package jazel.engine.events.application;

import jazel.engine.events.Event;
import jazel.engine.events.enumeration.EventCategory;
import jazel.engine.events.enumeration.EventType;

public class AppUpdateEvent extends Event {

    public AppUpdateEvent() {
        super(EventType.APP_UPDATE, EventCategory.APPLICATION);
    }
}
