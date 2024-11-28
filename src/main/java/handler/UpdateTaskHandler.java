package handler;

import event.Event;
import event.EventHandler;
import model.Task;
import util.SQLiteUtil;

public class UpdateTaskHandler implements EventHandler {

    @Override
    public void handle(Event event) {
        Task task = (Task) event.getData();
        SQLiteUtil.updateTask(task.getId(), task.getTitle(), task.getDescription());
        System.out.println("Task updated: " + task.getId());
    }
}
