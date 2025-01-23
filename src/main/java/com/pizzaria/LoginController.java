package com.pizzaria;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    private UserDAO userDAO;

    public LoginController() {
        userDAO = new UserDAO();
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erro de Login", "Por favor, preencha todos os campos.");
            return;
        }

        boolean isValidUser = userDAO.validateUser(username, password);

        if (isValidUser) {
            // Redireciona para main.fxml
            SceneManager.switchScene((Stage) loginButton.getScene().getWindow(), "/com/pizzaria/view/main.fxml");
        } else {
            showAlert(Alert.AlertType.ERROR, "Erro de Login", "Usuário ou senha incorretos.");
        }
    }

    @FXML
    public void redirectToRegister(ActionEvent event) {
        // Lógica para redirecionar para a tela de registro
        try {
            App.setRoot("view/register"); // Carrega o arquivo register.fxml
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
