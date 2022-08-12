package jazel.events.key;

import jazel.events.Event;
import jazel.events.enumeration.EventCategory;
import jazel.events.enumeration.EventType;
import lombok.Getter;

@Getter
public class KeyEvent extends Event {

    protected final int keyCode;

    protected KeyEvent(int keyCode, EventType type) {
        super(type, EventCategory.KEYBOARD);
        this.keyCode = keyCode;
    }
}
