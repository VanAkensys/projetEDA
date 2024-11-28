import event.Event;
import event.EventBus;
import event.EventType;
import handler.*;
import model.Task;
import util.SQLiteUtil;

import java.util.Map;

public class Main {
    public static void main(String[] args) {

        EventBus.subscribe(EventType.CREATE_TASK, new CreateTaskHandler());
        EventBus.subscribe(EventType.READ_TASK, new ReadTaskHandler());
        EventBus.subscribe(EventType.UPDATE_TASK, new UpdateTaskHandler());
        EventBus.subscribe(EventType.DELETE_TASK, new DeleteTaskHandler());


        Task task = new Task("1", "Learn Event-Driven", "Explore event-driven architecture");
        EventBus.publish(new Event(EventType.CREATE_TASK, task));

        EventBus.publish(new Event(EventType.READ_TASK, Map.of("id", "1")));
    }
}
