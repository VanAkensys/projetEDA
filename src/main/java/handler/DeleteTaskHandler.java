package handler;

import event.Event;
import event.EventHandler;
import util.SQLiteUtil;

import java.util.Map;

public class DeleteTaskHandler implements EventHandler {

    @Override
    public void handle(Event event) {
        Map<String, String> data = (Map<String, String>) event.getData();
        String taskId = data.get("id");
        SQLiteUtil.deleteTask(taskId);
        System.out.println("Task deleted: " + taskId);
    }
}

