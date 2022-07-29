package jazel.events;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EventDispatcher {

    private Event event;

    Boolean dispatch() {
        event.setHandled(true);
        return true;
    }
}
