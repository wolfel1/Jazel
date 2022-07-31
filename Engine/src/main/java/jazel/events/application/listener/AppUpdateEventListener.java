package jazel.events.application.listener;

import java.util.EventListener;
import jazel.events.application.AppUpdateEvent;
import jazel.events.application.WindowResizeEvent;

public interface AppUpdateEventListener extends EventListener {

  boolean onAppUpdate(AppUpdateEvent event);
}
