package com.pizzaria;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.File;
import java.io.IOException;

public class CrudController {

    @FXML
    private TableView<Product> tableProdutos;

    @FXML
    private TableColumn<Product, Integer> colId;

    @FXML
    private TableColumn<Product, String> colNome;

    @FXML
    private TableColumn<Product, String> colDescricao;

    @FXML
    private TableColumn<Product, Double> colPreco;

    @FXML
    private TableColumn<Product, Integer> colEstoque;

    @FXML
    private TableColumn<Product, String> colImagem;

    @FXML
    private TableColumn<Product, String> colCategoria; // Coluna para Categoria

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtDescricao;

    @FXML
    private TextField txtPreco;

    @FXML
    private TextField txtEstoque;

    @FXML
    private ComboBox<String> cmbCategoria;

    @FXML
    private TextField txtImagem;

    private final ProductDAO productDAO = new ProductDAO();
    private ObservableList<Product> productList;

    @FXML
    public void initialize() {
        setupTableColumns();
        loadProducts();
        loadCategories();
    }

    private void setupTableColumns() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("price"));
        colEstoque.setCellValueFactory(new PropertyValueFactory<>("stock"));

        // Configuração da coluna de Imagem
        colImagem.setCellValueFactory(new PropertyValueFactory<>("imagePath"));
        colImagem.setCellFactory(param -> new TableCell<Product, String>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(String imagePath, boolean empty) {
                super.updateItem(imagePath, empty);
                if (empty || imagePath == null) {
                    setGraphic(null);
                } else {
                    File file = new File(imagePath);
                    if (file.exists()) {
                        Image image = new Image(file.toURI().toString());
                        imageView.setImage(image);
                        imageView.setFitHeight(50);
                        imageView.setPreserveRatio(true);
                        setGraphic(imageView);
                    } else {
                        setGraphic(null);
                    }
                }
            }
        });

        // Configuração da coluna de Categoria
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("category"));
    }

    private void loadProducts() {
        // Carregar os produtos do banco de dados
        productList = FXCollections.observableArrayList(productDAO.getAllProducts());
        tableProdutos.setItems(productList);
    }

    @FXML
    public void onVoltar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view/main.fxml"));
            Parent mainRoot = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(mainRoot));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onSalvar(ActionEvent event) {
        try {
            // Valida os campos
            validateFields();

            // Cria um novo produto a partir dos campos preenchidos
            Product product = new Product(
                    txtNome.getText(),
                    txtDescricao.getText(),
                    Double.parseDouble(txtPreco.getText()),
                    Integer.parseInt(txtEstoque.getText()),
                    txtImagem.getText(),  // Caminho da imagem
                    cmbCategoria.getValue() // Categoria selecionada
            );

            // Salva o produto no banco de dados
            productDAO.addProduct(product);

            // Mensagem de sucesso
            showAlert("Sucesso", "Produto salvo com sucesso!", Alert.AlertType.INFORMATION);

            // Atualiza a tabela e limpa os campos
            loadProducts();
            clearFields();
        } catch (NumberFormatException e) {
            showAlert("Erro", "Os campos preço e estoque devem ser numéricos.", Alert.AlertType.ERROR);
        } catch (IllegalArgumentException e) {
            showAlert("Erro", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void onExcluir(ActionEvent event) {
        Product selectedProduct = tableProdutos.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            productDAO.deleteProduct(selectedProduct.getId());
            productList.remove(selectedProduct);
            showAlert("Sucesso", "Produto excluído com sucesso!", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Erro", "Nenhum produto selecionado para exclusão.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void onInserir(ActionEvent event) {
        clearFields();
    }

    @FXML
    private void onEscolherImagem(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecionar Imagem");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            txtImagem.setText(selectedFile.getAbsolutePath());
        }
    }

    private void clearFields() {
        txtNome.clear();
        txtDescricao.clear();
        txtPreco.clear();
        txtEstoque.clear();
        txtImagem.clear();
        cmbCategoria.getSelectionModel().clearSelection();
    }

    private void validateFields() {
        if (txtNome.getText().isEmpty() || txtDescricao.getText().isEmpty() ||
                txtPreco.getText().isEmpty() || txtEstoque.getText().isEmpty() || cmbCategoria.getValue() == null) {
            throw new IllegalArgumentException("Todos os campos devem ser preenchidos.");
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void loadCategories() {
        // Carregar as categorias fixas no ComboBox
        cmbCategoria.setItems(FXCollections.observableArrayList("Pizzas", "Bebidas", "Sobremesas"));
    }
}
