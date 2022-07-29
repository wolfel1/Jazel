package jazel.events;

import jazel.events.enumeration.EventCategory;
import jazel.events.enumeration.EventType;
import lombok.Getter;
import lombok.Setter;


@Getter
public abstract class Event {

    private final EventType type;
    private final EventCategory category;

    @Setter
    private boolean handled;

    public Event(EventType type, EventCategory category) {
        this.category = category;
        this.type = type;
    }
    public String getName() {
        return type.toString();
    }
    @Override
    public String toString() {
        return getName();
    }

    public boolean isInCategory(EventCategory category) {
        return getCategory().equals(category);
    }
}
