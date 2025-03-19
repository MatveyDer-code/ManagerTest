package org.example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public String getAllTasks(Model model) {
        model.addAttribute("tasks", taskService.getAllTasks());
        return "tasks";
    }

    @GetMapping("/todo")
    public String getAllTasksTodo(Model model) {
        model.addAttribute("tasks", taskService.getAllTasksByStatus("TODO"));
        return "tasks";
    }

    @GetMapping("/done")
    public String getAllTasksDone(Model model) {
        model.addAttribute("tasks", taskService.getAllTasksByStatus("DONE"));
        return "tasks";
    }

    @PostMapping("/add")
    public String addTask(@RequestParam("title") String title, @RequestParam("description") String description) {
        taskService.createTask(title, description);
        return "redirect:/tasks/todo";
    }

    @PostMapping("/update")
    public String updateTaskStatus(@RequestParam("id") long id) {
        taskService.updateTask(id);
        return "redirect:/tasks/done";
    }

    @PostMapping("/delete")
    public String deleteTask(@RequestParam("id") long id) {
        taskService.deleteTask(id);
        return "redirect:/tasks";
    }
}
