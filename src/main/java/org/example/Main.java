package org.example;

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
        DataBaseConnection dataBaseConnection = new DataBaseConnection();

        try (Connection connect = dataBaseConnection.connect()) {
            BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Выберите действие:");
            System.out.println("1. Добавить задачу");
            System.out.println("2. Удалить задачу");
            System.out.println("3. Изменить статус задачи на DONE");
            String choice = bf.readLine();

            switch (choice) {
                case "1":
                    System.out.print("Введите название задачи: ");
                    String title = bf.readLine();
                    System.out.print("Введите описание задачи: ");
                    String description = bf.readLine();
                    LocalDateTime dateTime = LocalDateTime.now();
                    insertTask(title, description, dateTime, connect);
                    break;
                case "2":
                    System.out.print("Введите id: ");
                    int deleteId = Integer.parseInt(bf.readLine());
                    deleteTask(deleteId, connect);
                    break;
                case "3":
                    System.out.print("Введите ID задачи для изменения статуса: ");
                    int updateId = Integer.parseInt(bf.readLine());
                    updateTaskStatus(updateId, connect);
                    break;

                default:
                    System.out.println("Некорректный выбор");
            }
        } catch (SQLException | IOException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    public static void insertTask(String title, String desc, LocalDateTime dateTime, Connection connect) throws SQLException {
        String sql = "INSERT INTO test (title, description, status, date_time) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connect.prepareStatement(sql)){
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, desc);
            preparedStatement.setString(3, "TODO");
            preparedStatement.setTimestamp(4, Timestamp.valueOf(dateTime));
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " вставлено строк");
        }
    }

    public static void deleteTask(int id, Connection connect) throws SQLException {
        String sql = "DELETE FROM test WHERE id = ?;";
        try (PreparedStatement preparedStatement = connect.prepareStatement(sql)){
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " удалено строк");
        }
    }

    public static void updateTaskStatus(int id, Connection connect) throws SQLException {
        String sql = "UPDATE test SET status = ? WHERE id = ? AND status = 'TODO';";
        try (PreparedStatement preparedStatement = connect.prepareStatement(sql)){
            preparedStatement.setString(1, "DONE");
            preparedStatement.setInt(2, id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Статус задачи обновлён на 'DONE'");
            } else {
                System.out.println("Задача не найдена или уже выполнена");
            }
        }
    }
}