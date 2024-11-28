package handler;

import event.Event;
import event.EventHandler;
import model.Task;
import util.SQLiteUtil;

public class CreateTaskHandler implements EventHandler {

    @Override
    public void handle(Event event) {
        Task task = (Task) event.getData();
        SQLiteUtil.insertTask(task.getId(), task.getTitle(), task.getDescription());
        System.out.println("Task created: " + task.getId());
    }
}

