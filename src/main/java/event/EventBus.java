package event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventBus {
    private static final Map<EventType, List<EventHandler>> handlers = new HashMap<>();

    public static void subscribe(EventType eventType, EventHandler handler) {
        handlers.computeIfAbsent(eventType, k -> new ArrayList<>()).add(handler);
    }

    public static void publish(Event event) {
        List<EventHandler> eventHandlers = handlers.getOrDefault(event.getType(), new ArrayList<>());
        for (EventHandler handler : eventHandlers) {
            handler.handle(event);
        }
    }
}
