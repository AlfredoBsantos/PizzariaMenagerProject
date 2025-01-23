/*package com.pizzaria;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.SQLException;

public class PaymentPopupController {

    @FXML
    private Label totalAmountLabel; // Rótulo para exibir o valor total do pedido
    @FXML
    private ComboBox<String> paymentMethodComboBox; // ComboBox para selecionar a forma de pagamento
    @FXML
    private ComboBox<String> clientComboBox; // ComboBox para selecionar um cliente existente
    @FXML
    private CheckBox newClientCheckBox; // CheckBox para indicar se o cliente é novo
    @FXML
    private TextField clientNameTextField; // Campo de texto para o nome do novo cliente
    @FXML
    private Button confirmButton; // Botão de confirmação do pedido

    // Método de inicialização
    @FXML
    private void initialize() {
        // Preenche as formas de pagamento no ComboBox
        paymentMethodComboBox.getItems().addAll("Cartão de Crédito", "Cartão de Débito", "Dinheiro", "Pix");

        // Carrega os clientes existentes no ComboBox
        Database.loadClients(clientComboBox);

        // Inicialmente, esconde o campo de texto para novo cliente
        clientNameTextField.setVisible(false);

        // Listener para alternar entre cliente existente e novo cliente
        newClientCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            clientNameTextField.setVisible(newValue); // Mostra o campo para novo cliente
            clientComboBox.setVisible(!newValue); // Esconde o ComboBox de clientes existentes
        });
    }

    // Método chamado ao clicar no botão de confirmar pedido
    @FXML
    private void handleConfirm(ActionEvent event) {
        String selectedPaymentMethod = paymentMethodComboBox.getValue();
        String clientName;

        // Verifica se é um novo cliente ou existente
        if (newClientCheckBox.isSelected()) {
            clientName = clientNameTextField.getText();
        } else {
            clientName = clientComboBox.getValue();
        }

        // Validações
        if (clientName == null || clientName.isEmpty()) {
            showAlert("Erro", "Por favor, selecione ou cadastre um cliente.", Alert.AlertType.WARNING);
            return;
        }

        if (selectedPaymentMethod == null || selectedPaymentMethod.isEmpty()) {
            showAlert("Erro", "Por favor, selecione uma forma de pagamento.", Alert.AlertType.WARNING);
            return;
        }

        double totalAmount;
        try {
            String totalText = totalAmountLabel.getText().replace("Total: R$ ", "").trim();
            totalAmount = Double.parseDouble(totalText);
        } catch (NumberFormatException e) {
            showAlert("Erro", "Erro ao ler o valor total.", Alert.AlertType.ERROR);
            return;
        }

        // Registra o cliente (se necessário) e obtém o ID
        try {
            String clientId = Database.getClientIdOrRegister(clientName);

            if (clientId == null) {
                showAlert("Erro", "Falha ao registrar o cliente.", Alert.AlertType.ERROR);
                return;
            }

            // Obtém a data e hora atuais
            String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            // Registra o pedido no banco de dados
            Database.insertOrder(clientId, totalAmount, selectedPaymentMethod, dateTime);

            showAlert("Sucesso", "Pedido registrado com sucesso!", Alert.AlertType.INFORMATION);
        } catch (SQLException e) {
            showAlert("Erro", "Erro ao processar o pedido: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // Método para configurar o valor total no rótulo
    public void setTotalAmount(double amount) {
        totalAmountLabel.setText("Total: R$ " + String.format("%.2f", amount));
    }

    // Método para exibir alertas
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}*/
