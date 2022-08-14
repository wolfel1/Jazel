package jazel.engine.events.key;

import jazel.engine.events.Event;
import jazel.engine.events.enumeration.EventCategory;
import jazel.engine.events.enumeration.EventType;
import lombok.Getter;

@Getter
public class KeyEvent extends Event {

    protected final int keyCode;

    protected KeyEvent(int keyCode, EventType type) {
        super(type, EventCategory.KEYBOARD);
        this.keyCode = keyCode;
    }
}
