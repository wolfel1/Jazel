package jazel.events.application.listener;

import java.util.EventListener;
import jazel.events.application.AppRenderEvent;
import jazel.events.application.WindowResizeEvent;

public interface AppRenderEventListener extends EventListener {

  boolean onAppRender(AppRenderEvent event);
}
