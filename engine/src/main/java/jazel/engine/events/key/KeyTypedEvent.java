package jazel.engine.events.key;

import jazel.engine.events.enumeration.EventType;
import lombok.Getter;

@Getter
public class KeyTypedEvent extends KeyEvent {

    public KeyTypedEvent(int keyCode) {
        super(keyCode, EventType.KEY_TYPED);
    }

    @Override
    public String toString() {
        return "KeyTypedEvent: " + keyCode;
    }
}
