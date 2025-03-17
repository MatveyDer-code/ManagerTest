package org.example;

import java.time.LocalDateTime;
import java.util.List;

public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService (TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void createTask(String title, String description) {
        if (title == null || title.trim().isEmpty()) {
            System.out.println("Название задачи не может быть пустым!");
            return;
        }

        Task task = new Task(title, description, LocalDateTime.now());
        taskRepository.save(task);
        System.out.println("Задача добавлена");
    }

    public void updateTask(long id) {
        Task task = taskRepository.findById(id);
        if (task == null) {
            System.out.println("Задача не найдена");
            return;
        }
        if ("DONE".equals(task.getStatus())) {
            System.out.println("Эта задача уже завершена!");
            return;
        }
        task.setStatus("DONE");
        taskRepository.update(task);
        System.out.println("Задача выполнена");
    }

    public void deleteTask(long id) {
        Task task = taskRepository.findById(id);
        if (task == null) {
            System.out.println("Задача не найдена");
            return;
        }
        taskRepository.delete(task);
        System.out.println("Задача удалена");
    }

    public void printAllTasksByStatus() {
        List<Task> tasksList = taskRepository.findAllByStatus();

        for (Task task : tasksList) {
            System.out.println(task.toString());
        }
    }
}