package jazel.events.application;

import jazel.events.Event;
import jazel.events.enumeration.EventCategory;
import jazel.events.enumeration.EventType;

public class AppUpdateEvent extends Event {

    public AppUpdateEvent() {
        super(EventType.APP_UPDATE, EventCategory.APPLICATION);
    }
}
