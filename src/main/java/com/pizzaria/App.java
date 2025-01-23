package com.pizzaria;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class App extends Application {

    private static final String DATABASE_URL = "jdbc:sqlite:database.db";
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) {
        try {
            // Criação do banco de dados e tabelas, se não existirem
            initializeDatabase();

            // Define o palco principal
            primaryStage = stage;

            // Carregamento da tela de login
            setRoot("view/login");

            primaryStage.setTitle("Sistema de Gerenciamento");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void initializeDatabase() {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             Statement statement = connection.createStatement()) {

            // Criação da tabela users
            String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "username TEXT NOT NULL UNIQUE, " +
                    "password TEXT NOT NULL);";
            statement.execute(createUsersTable);

            // Criação da tabela products com a coluna category
            String createProductsTable = "CREATE TABLE IF NOT EXISTS products (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT NOT NULL, " +
                    "description TEXT, " +
                    "price REAL NOT NULL, " +
                    "stock INTEGER NOT NULL, " +
                    "image TEXT, " +
                    "category TEXT NOT NULL DEFAULT 'Pizzas');"; // Nova coluna 'category'
            statement.execute(createProductsTable);

            // Criação da tabela clients
            String createClientsTable = "CREATE TABLE IF NOT EXISTS clients (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT NOT NULL, " +
                    "phone TEXT NOT NULL);";
            statement.execute(createClientsTable);

            System.out.println("Banco de dados inicializado com sucesso.");
        } catch (Exception e) {
            System.err.println("Erro ao inicializar o banco de dados: " + e.getMessage());
        }
    }

    public static void setRoot(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
            Scene scene = new Scene(loader.load());
            primaryStage.setScene(scene);
        } catch (Exception e) {
            System.err.println("Erro ao carregar a tela: " + fxml + ".fxml");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
        initializeDatabase();
    }
}
