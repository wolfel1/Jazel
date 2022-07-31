package jazel.events;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class EventRegistry {

  private static final Queue<Event> eventRegister = new ConcurrentLinkedQueue<>();
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
      unregister(event);
    }
  }
}
