package jazel.engine.events.key;

import jazel.engine.events.enumeration.EventType;
import lombok.Getter;

@Getter
public class KeyReleasedEvent extends KeyEvent {

    public KeyReleasedEvent(int keyCode) {
        super(keyCode, EventType.KEY_RELEASED);
    }

    @Override
    public String toString() {
        return "KeyReleasedEvent: " + keyCode;
    }
}
