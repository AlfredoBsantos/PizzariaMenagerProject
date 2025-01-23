package com.pizzaria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {


    public boolean registerUser(String username, String password) {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, password);
            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Erro ao registrar usuário: " + e.getMessage());
            return false;
        }
    }

    // Método para validar as credenciais do usuário
    public boolean validateUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next(); // Retorna true se o usuário foi encontrado
            }

        } catch (SQLException e) {
            System.err.println("Erro ao validar usuário: " + e.getMessage());
            return false;
        }
    }
}
