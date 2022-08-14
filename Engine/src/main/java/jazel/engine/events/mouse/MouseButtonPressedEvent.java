package jazel.engine.events.mouse;

import jazel.engine.events.enumeration.EventType;

public class MouseButtonPressedEvent extends MouseButtonEvent {

    public MouseButtonPressedEvent(int button) {
        super(button, EventType.MOUSE_BUTTON_PRESSED);
    }

    @Override
    public String toString() {
        return "MouseButtonPressedEvent: " + button;
    }
}
