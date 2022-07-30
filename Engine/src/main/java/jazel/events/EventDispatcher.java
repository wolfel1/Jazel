package jazel.events;

import jazel.core.Log;

public class EventDispatcher {

  public static boolean dispatch(Event event) {
    Log.getCoreLogger().info("{}", event);
    event.setHandled(true);
    return true;
  }
}
