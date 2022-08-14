package jazel.engine.events.key;

import jazel.engine.events.enumeration.EventType;
import lombok.Getter;

@Getter
public class KeyPressedEvent extends KeyEvent {

    private final int repeatCount;

    public KeyPressedEvent(int keyCode, int repeatCount) {
        super(keyCode, EventType.KEY_PRESSED);
        this.repeatCount = repeatCount;
    }

    @Override
    public String toString() {
        return "KeyPressedEvent: " + keyCode + "(" + repeatCount + ")";
    }
}
