package jazel.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {

  private static Logger clientLogger;
  private static Logger coreLogger;

  private Log() {}

  public static void init() {
    coreLogger = LoggerFactory.getLogger("Core");
    clientLogger = LoggerFactory.getLogger("Client");
  }

  public static Logger getCoreLogger() {
    if (coreLogger == null) {
      init();
    }
    return coreLogger;
  }

  public static Logger getClientLogger() {
    if (clientLogger == null) {
      init();
    }
    return clientLogger;
  }
}
