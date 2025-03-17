package org.example;

import org.hibernate.SessionFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        try (SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            BufferedReader bf = new BufferedReader(new InputStreamReader(System.in))) {
            TaskRepository taskRepository = new TaskRepository(sessionFactory);
            TaskService taskService = new TaskService(taskRepository);

            System.out.println("Выберите действие:");
            System.out.println("1. Добавить задачу");
            System.out.println("2. Удалить задачу");
            System.out.println("3. Изменить статус задачи на DONE");
            System.out.println("4. Вывести все невыполненые задачи");
            String choice = bf.readLine();
            long id;

            switch (choice) {
                case "1":
                    System.out.println("Введите название задачи");
                    String title = bf.readLine();
                    System.out.println("Введите описание задачи");
                    String description = bf.readLine();
                    taskService.createTask(title, description);
                    break;
                case "2":
                    System.out.println("Введите ID задачи");
                    id = Long.parseLong(bf.readLine());
                    taskService.deleteTask(id);
                    break;
                case "3":
                    System.out.println("Введите ID задачи");
                    id = Long.parseLong(bf.readLine());
                    taskService.updateTask(id);
                    break;
                case "4":
                    taskService.printAllTasksByStatus();
                    break;
                default:
                    System.out.println("Некорректный выбор");
            }
        } catch (IOException e) {
            System.out.println("Ошибка ввода: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.shutdown();
        }
    }
}