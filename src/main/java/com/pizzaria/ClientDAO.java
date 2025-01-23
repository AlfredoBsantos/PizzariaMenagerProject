package com.pizzaria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {

    // Método para adicionar um novo cliente ao banco de dados
    public boolean addClient(Client client) {
        String sql = "INSERT INTO clients (name, phone) VALUES (?, ?)";
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, client.getName());
            statement.setString(2, client.getPhone());
            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Erro ao adicionar cliente: " + e.getMessage());
            return false;
        }
    }

    // Método para atualizar os dados de um cliente existente
    public boolean updateClient(Client client) {
        String sql = "UPDATE clients SET name = ?, phone = ? WHERE id = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, client.getName());
            statement.setString(2, client.getPhone());
            statement.setInt(3, client.getId());
            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar cliente: " + e.getMessage());
            return false;
        }
    }

    // Método para excluir um cliente do banco de dados
    public boolean deleteClient(int clientId) {
        String sql = "DELETE FROM clients WHERE id = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, clientId);
            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Erro ao excluir cliente: " + e.getMessage());
            return false;
        }
    }

    // Método para buscar todos os clientes no banco de dados
    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM clients";
        try (Connection connection = Database.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Client client = new Client(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("phone")
                );
                clients.add(client);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar clientes: " + e.getMessage());
        }
        return clients;
    }

    // Método para buscar um cliente pelo ID
    public Client getClientById(int clientId) {
        String sql = "SELECT * FROM clients WHERE id = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, clientId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Client(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("phone")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar cliente por ID: " + e.getMessage());
        }
        return null;
    }
}
