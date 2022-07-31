package jazel.events.application.listener;

import java.util.EventListener;
import jazel.events.Event;
import jazel.events.JazelEventListener;
import jazel.events.application.WindowCloseEvent;
import jazel.events.application.WindowResizeEvent;
import jazel.events.enumeration.EventType;

public abstract class WindowCloseEventListener implements JazelEventListener {
  @Override
  public void onEvent(Event event) {
      event.setHandled(onWindowClose((WindowCloseEvent) event));
  }

  public abstract boolean onWindowClose(WindowCloseEvent event);

  @Override
  public EventType getHandledEventType() {
    return EventType.WINDOW_CLOSE;
  }
}
