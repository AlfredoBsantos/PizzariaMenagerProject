package com.pizzaria;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    // URL do banco de dados - pode ser configurada via variável de ambiente
    private static final String DATABASE_URL = System.getenv("DATABASE_URL") != null
            ? System.getenv("DATABASE_URL")
            : "jdbc:sqlite:database.db";

    /**
     * Obtém uma conexão com o banco de dados.
     *
     * @return Conexão JDBC
     * @throws SQLException Se houver um erro ao conectar
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL);
    }

    /**
     * Verifica a conexão com o banco de dados.
     *
     * @return true se a conexão for bem-sucedida, false caso contrário
     */
    public static boolean testConnection() {
        try (Connection connection = getConnection()) {
            if (connection != null) {
                System.out.println("Conexão com o banco de dados estabelecida com sucesso.");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
        return false;
    }


}
