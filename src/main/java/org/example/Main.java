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
            System.out.print("Введите название задачи: ");
            String title = bf.readLine();
            System.out.print("Введите описание задачи: ");
            String description = bf.readLine();
            LocalDateTime dateTime = LocalDateTime.now();
            insertTask(title, description, dateTime, connect);

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
}