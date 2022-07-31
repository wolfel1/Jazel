package jazel.events;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class EventRegistry {

  private static final Queue<Event> eventRegister = new LinkedList<>();
  private static final EventDispatcher dispatcher = new EventDispatcher();

  public static void register(Event event) {
    eventRegister.add(event);
  }

  public static void unregister(Event event) {
    eventRegister.remove(event);
  }
  public static void handleEvents() {
    for (Event event : eventRegister) {
      if (!event.isHandled()) {
        dispatcher.dispatch(event);
      }
    }
  }
}
