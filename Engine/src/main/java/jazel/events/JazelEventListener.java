package jazel.events;

import java.util.EventListener;
import jazel.events.enumeration.EventType;

public interface JazelEventListener extends EventListener {
  void onEvent(Event event);

  EventType getHandledEventType();
}
