package jazel.events.mouse;

import jazel.events.enumeration.EventType;

public class MouseButtonReleasedEvent extends MouseButtonEvent {

  public MouseButtonReleasedEvent(int button) {
    super(button, EventType.MOUSE_BUTTON_RELEASED);
  }

  @Override
  public String toString() {
    return "MouseButtonReleasedEvent: " + button;
  }
}
