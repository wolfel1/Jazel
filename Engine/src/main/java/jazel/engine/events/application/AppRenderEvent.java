package jazel.engine.events.application;

import jazel.engine.events.Event;
import jazel.engine.events.enumeration.EventCategory;
import jazel.engine.events.enumeration.EventType;

public class AppRenderEvent extends Event {

    public AppRenderEvent() {
        super(EventType.APP_RENDER, EventCategory.APPLICATION);
    }
}
