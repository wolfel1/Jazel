package jazel.events;

import jazel.core.Application;

import java.util.Collection;
import java.util.HashSet;

public class EventDispatcher {

  private static final Collection<JazelEventListener> listenerSet = new HashSet<>();

  public static void register(JazelEventListener listener) {
    listenerSet.add(listener);
  }

  public static void unregister(JazelEventListener listener) {
    listenerSet.remove(listener);
  }

  public void dispatch(Event event) {
    var app = Application.getInstance();
    app.onEvent(event);
    for (JazelEventListener listener : listenerSet) {
      if (event.getType() == listener.getHandledEventType()) {
        listener.onEvent(event);
      }
    }
  }
}
