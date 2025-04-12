package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/friendsofhuman"; // URL базы данных
    private static final String USER = "root"; // Имя пользователя для MySQL
    private static final String PASSWORD = "password"; // Пароль для MySQL

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
