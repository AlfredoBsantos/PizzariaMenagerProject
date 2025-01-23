package com.pizzaria;

import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class MainController {

    @FXML
    private TextField searchField;

    @FXML
    private Button searchButton;

    @FXML
    private ListView<String> categoryList;

    @FXML
    private ListView<HBox> productList;

    @FXML
    private ListView<HBox> cartList;

    @FXML
    private Label totalLabel;

    @FXML
    private Button checkoutButton;

    @FXML
    private Button crudButton;

    @FXML
    private Button logoutButton;

    @FXML
    private ImageView imgProduto; // ImageView para exibir a imagem do produto selecionado

    private ObservableList<HBox> cartItems;
    private ObservableList<String> categories;

    private double totalAmount = 0.0;

    @FXML
    public void initialize() {
        // Inicializa a lista do carrinho
        cartItems = FXCollections.observableArrayList();
        cartList.setItems(cartItems);

        // Inicializa a lista de categorias
        categories = FXCollections.observableArrayList("Pizzas", "Bebidas", "Sobremesas");
        categoryList.setItems(categories);

        // Carrega os produtos
        loadProducts();

        // Configurações de eventos
        checkoutButton.setOnAction(event -> finalizePurchase());
        crudButton.setOnAction(event -> abrirCrud());
        searchButton.setOnAction(event -> searchProducts());
        logoutButton.setOnAction(event -> logout());

        // Adiciona evento de seleção na lista de categorias
        categoryList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> filterProductsByCategory(newVal));

        // Adiciona evento de seleção na lista de produtos
        productList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> updateProductImage(newVal));
    }

    private void loadProducts() {
        ProductDAO productDAO = new ProductDAO();
        List<Product> products = productDAO.getAllProducts();

        for (Product product : products) {
            HBox productBox = createProductBox(product);
            productList.getItems().add(productBox);
        }
    }

    private HBox createProductBox(Product product) {
        HBox productBox = new HBox();

        // Usando ImageView para carregar a imagem
        ImageView productImage;
        try {
            if (product.getImagePath() != null && !product.getImagePath().isEmpty()) {
                productImage = new ImageView(new Image("file:" + product.getImagePath())); // Usando caminho do arquivo diretamente
            } else {
                // Se o caminho da imagem for nulo ou vazio, define uma imagem padrão
                productImage = new ImageView(new Image(getClass().getResourceAsStream("/com/pizzaria/images/default.png")));
            }
        } catch (Exception e) {
            // Em caso de erro no carregamento da imagem, define uma imagem padrão
            productImage = new ImageView(new Image(getClass().getResourceAsStream("/com/pizzaria/images/default.png")));
            System.out.println("Erro ao carregar a imagem do produto: " + product.getName());
        }

        productImage.setFitWidth(50);
        productImage.setFitHeight(50);

        Label productName = new Label(product.getName());
        Label productPrice = new Label("$" + product.getPrice());
        Button addToCartButton = new Button("Adicionar ao Carrinho");

        addToCartButton.setOnAction(event -> addToCart(product));

        productBox.getChildren().addAll(productImage, productName, productPrice, addToCartButton);
        productBox.setSpacing(10);

        return productBox;
    }

    private void addToCart(Product product) {
        HBox cartItem = new HBox();

        Label productName = new Label(product.getName());
        Label productPrice = new Label("$" + product.getPrice());

        cartItem.getChildren().addAll(productName, productPrice);
        cartItem.setSpacing(10);

        cartItems.add(cartItem);
        totalAmount += product.getPrice();
        totalLabel.setText("Total: $" + totalAmount);
    }

    private void finalizePurchase() {
        if (cartItems.isEmpty()) {
            System.out.println("Carrinho vazio. Adicione itens antes de finalizar a compra.");
        } else {
            System.out.println("Compra finalizada. Total: $" + totalAmount);
            cartItems.clear();
            totalAmount = 0.0;
            totalLabel.setText("Total: $0.0");
        }
    }

    @FXML
    public void abrirCrud() {
        try {
            // Redireciona para a página CRUD
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/pizzaria/view/crud.fxml"));
            Scene crudScene = new Scene(loader.load());

            // Obtém o estágio atual e define a nova cena
            Stage stage = (Stage) crudButton.getScene().getWindow();
            stage.setScene(crudScene);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao abrir a página CRUD.");
        }
    }

    @FXML
    public void searchProducts() {
        String query = searchField.getText();
        System.out.println("Buscando produtos: " + query);

        // Aqui você pode implementar uma busca no banco de dados ou uma filtragem local
        // Exemplo de limpeza e exibição:
        productList.getItems().clear();
        ProductDAO productDAO = new ProductDAO();
        List<Product> products = productDAO.searchProducts(query);

        for (Product product : products) {
            HBox productBox = createProductBox(product);
            productList.getItems().add(productBox);
        }
    }

    @FXML
    public void filterProductsByCategory(String category) {
        System.out.println("Filtrando produtos pela categoria: " + category);

        // Aqui você pode implementar uma lógica para filtrar os produtos
        productList.getItems().clear();
        ProductDAO productDAO = new ProductDAO();
        List<Product> products = productDAO.getProductsByCategory(category);

        for (Product product : products) {
            HBox productBox = createProductBox(product);
            productList.getItems().add(productBox);
        }
    }

    @FXML
    public void logout() {
        System.out.println("Usuário deslogado.");
        // Aqui você pode redirecionar para a tela de login ou fechar o aplicativo
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/pizzaria/view/login.fxml"));
            Scene loginScene = new Scene(loader.load());
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(loginScene);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao redirecionar para a página de login.");
        }
    }

    // Método para atualizar a imagem do produto selecionado
    private void updateProductImage(HBox selectedProductBox) {
        if (selectedProductBox != null) {
            Label productNameLabel = (Label) selectedProductBox.getChildren().get(1); // O nome do produto está na segunda posição
            String productName = productNameLabel.getText();

            // Aqui você pode buscar a imagem baseada no nome do produto ou qualquer outra lógica
            ProductDAO productDAO = new ProductDAO();
            Product selectedProduct = productDAO.getProductByName(productName); // Supondo que tenha esse método no ProductDAO

            if (selectedProduct != null && selectedProduct.getImagePath() != null) {
                File file = new File(selectedProduct.getImagePath());
                if (file.exists()) {
                    Image image = new Image(file.toURI().toString());
                    imgProduto.setImage(image); // Atualiza a imagem do produto na tela principal
                }
            }
        }
    }
}
