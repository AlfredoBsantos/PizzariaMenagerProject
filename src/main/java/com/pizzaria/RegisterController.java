package com.pizzaria;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button registerButton;

    @FXML
    private Button backButton;

    @FXML
    private void handleRegister(ActionEvent event) {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        // Verifica se os campos estão preenchidos
        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erro de Registro", "Por favor, preencha todos os campos.");
            return;
        }

        // Interação com o UserDAO para registrar o usuário
        UserDAO userDAO = new UserDAO();
        boolean isRegistered = userDAO.registerUser(username, password);

        if (isRegistered) {
            showAlert(Alert.AlertType.INFORMATION, "Registro Concluído", "Usuário registrado com sucesso.");
            // Trocar para a tela de login
            SceneManager.switchScene((Stage) registerButton.getScene().getWindow(), "view/login.fxml");
        } else {
            showAlert(Alert.AlertType.ERROR, "Erro de Registro", "Não foi possível registrar o usuário. Tente novamente.");
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        // Trocar para a tela de login
        SceneManager.switchScene((Stage) backButton.getScene().getWindow(), "view/login.fxml");
    }

    /**
     * Exibe um alerta na tela.
     *
     * @param alertType Tipo do alerta (ERRO, INFORMAÇÃO, etc.).
     * @param title Título da janela de alerta.
     * @param message Mensagem exibida no corpo do alerta.
     */
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
