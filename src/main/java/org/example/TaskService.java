package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    @Autowired
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
        taskRepository.findById(id).ifPresentOrElse(task -> {
            if ("DONE".equals(task.getStatus())) {
                System.out.println("Эта задача уже завершена!");
            } else {
                task.setStatus("DONE");
                taskRepository.save(task);
                System.out.println("Задача выполнена");
            }
        }, () -> System.out.println("Задача не найдена"));
    }

    public void deleteTask(long id) {
        taskRepository.findById(id).ifPresentOrElse(task -> {
            taskRepository.delete(task);
            System.out.println("Задача удалена");
        }, () -> System.out.println("Задача не найдена"));
    }

    public void printAllTasksDone() {
        List<Task> tasksList = taskRepository.findAllByStatus("DONE");

        for (Task task : tasksList) {
            System.out.println(task.toString());
        }
    }

    public void printAllTasksTodo() {
        List<Task> tasksList = taskRepository.findAllByStatus("TODO");

        for (Task task : tasksList) {
            System.out.println(task.toString());
        }
    }

    public List<Task> getAllTasksByStatus(String status) {
        return taskRepository.findAllByStatus(status);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
}