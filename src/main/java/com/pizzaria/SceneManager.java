package com.pizzaria;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class SceneManager {

    private static Stage stage;

    // Método para configurar o Stage principal
    public static void setStage(Stage mainStage) {
        stage = mainStage;
    }

    // Método para carregar uma nova cena utilizando o Stage global
    public static void changeScene(String fxmlPath) {
        if (stage == null) {
            throw new IllegalStateException("O Stage principal não foi configurado. Use setStage primeiro.");
        }
        switchScene(stage, fxmlPath);
    }

    // Método para carregar uma nova cena com um Stage específico
    public static void switchScene(Stage targetStage, String fxmlPath) {
        try {
            URL fxmlUrl = SceneManager.class.getResource(fxmlPath);
            if (fxmlUrl == null) {
                throw new IllegalArgumentException("O arquivo FXML não foi encontrado: " + fxmlPath);
            }
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();
            Scene scene = new Scene(root);

            targetStage.setScene(scene);
            targetStage.show();
        } catch (IOException e) {
            System.err.println("Erro ao carregar a cena: " + fxmlPath);
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}
