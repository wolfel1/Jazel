package jazel.events.mouse;

import jazel.events.enumeration.EventType;

public class MouseButtonPressedEvent extends MouseButtonEvent {

    public MouseButtonPressedEvent(int button) {
        super(button, EventType.MOUSE_BUTTON_PRESSED);
    }

    @Override
    public String toString() {
        return "MouseButtonPressedEvent: " + button;
    }
}
