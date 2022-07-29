package jazel.events.application;

import jazel.events.Event;
import jazel.events.enumeration.EventCategory;
import jazel.events.enumeration.EventType;

public class AppRenderEvent extends Event {

    public AppRenderEvent() {
        super(EventType.APP_RENDER, EventCategory.APPLICATION);
    }
}
