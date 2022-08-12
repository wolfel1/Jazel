package jazel.events;

import jazel.core.Application;
import jazel.core.Log;
import jazel.events.annotation.EventHandler;
import jazel.events.enumeration.EventType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventDispatcher {

    private static final Map<EventType, List<Listener>> listenersByEventClass = new HashMap<>();

    public static void register(final Object listenerInstance) {
        for (Method method : listenerInstance.getClass().getMethods()) {
            if (method.isAnnotationPresent(EventHandler.class)) {
                if (method.getParameterCount() != 1) {
                    Log.getCoreLogger().error(
                            "Ignoring illegal event handler: {}: " + "Wrong number of arguments (required: exactly 1)",
                            method.getName());
                    continue;
                }

                // illegal parameter
                if (!Event.class.isAssignableFrom(method.getParameterTypes()[0])) {
                    Log.getCoreLogger().error("Ignoring illegal event handler: {}: " + "Argument must extend {}",
                            method.getName(), Event.class.getName());
                    continue;
                }
                var eventType = method.getAnnotation(EventHandler.class).type();

                Listener listener = new Listener(listenerInstance, method);
                addListener(eventType, listener);
            }
        }
    }

    private static void addListener(EventType eventType, Listener listener) {
        if (listenersByEventClass.containsKey(eventType)) {
            listenersByEventClass.get(eventType).add(listener);
        } else {
            var list = new ArrayList<Listener>();
            list.add(listener);
            listenersByEventClass.put(eventType, list);
        }
    }

    public static void unregisterListenerInstance(Listener listenerClassInstance) {
        for (var listeners : listenersByEventClass.values()) {
            listeners.removeIf(listener -> listener.listenerClassInstance.equals(listenerClassInstance));
        }
    }

    public static void unregisterEvent(EventType eventType) {
        listenersByEventClass.remove(eventType);
    }

    public void dispatch(Event event) {
        var app = Application.getInstance();
        app.onEvent(event);

        var eventListeners = listenersByEventClass.get(event.getType());
        if (eventListeners != null) {
            for (var listener : eventListeners) {
                if (!event.isHandled()) {
                    try {
                        listener.listenerMethod.setAccessible(true);
                        listener.listenerMethod.invoke(listener.listenerClassInstance, event);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        Log.getCoreLogger().error("Could not dispatch event {} to handler {}", event,
                                listener.listenerMethod.getName());
                    }
                }
            }
        }
    }

    private record Listener(Object listenerClassInstance, Method listenerMethod) {
    }
}
