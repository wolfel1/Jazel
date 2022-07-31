package jazel.events.application.listener;

import java.util.EventListener;
import jazel.events.Event;
import jazel.events.JazelEventListener;
import jazel.events.application.WindowResizeEvent;
import jazel.events.enumeration.EventType;

public abstract class WindowResizeEventListener implements JazelEventListener {

  @Override
  public void onEvent(Event event) {
      event.setHandled(onWindowResize((WindowResizeEvent) event));
  }

  public abstract boolean onWindowResize(WindowResizeEvent event);

  @Override
  public EventType getHandledEventType() {
    return EventType.WINDOW_RESIZE;
  }
}
