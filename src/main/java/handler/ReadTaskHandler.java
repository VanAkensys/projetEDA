package handler;

import event.Event;
import event.EventHandler;
import util.SQLiteUtil;

import java.util.Map;

public class ReadTaskHandler implements EventHandler {

    @Override
    public void handle(Event event) {
        Map<String, String> data = (Map<String, String>) event.getData();
        String taskId = data.get("id");
        String task = SQLiteUtil.readTask(taskId);
        System.out.println("Read Task: " + task);
    }
}
