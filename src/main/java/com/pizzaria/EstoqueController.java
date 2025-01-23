package com.pizzaria;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import java.io.IOException;

public class EstoqueController {

    @FXML
    private void gerenciarEstoque(ActionEvent event) {
        // Lógica para gerenciar o estoque (pode ser expandida)
    }

    @FXML
    private void voltar(ActionEvent event) throws IOException {
        App.setRoot("main"); // Redireciona para "main.fxml"
    }
}
