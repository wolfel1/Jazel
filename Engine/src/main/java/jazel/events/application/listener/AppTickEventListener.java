package jazel.events.application.listener;

import java.util.EventListener;
import jazel.events.application.AppTickEvent;
import jazel.events.application.WindowResizeEvent;

public interface AppTickEventListener extends EventListener {

  boolean onAppTickEvent(AppTickEvent event);
}
