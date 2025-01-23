package com.pizzaria;

public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private int stock;
    private String imagePath;  // Caminho para a imagem do produto
    private String category;   // Categoria do produto (como "Pizzas", "Bebidas", etc.)

    // Construtor completo com id
    public Product(int id, String name, String description, double price, int stock, String imagePath, String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.imagePath = imagePath;  // Garantindo que a imagem seja atribuída corretamente
        this.category = category;    // Garantindo que a categoria seja atribuída corretamente
    }

    // Construtor sem id, para quando estamos criando um novo produto
    public Product(String name, String description, double price, int stock, String imagePath, String category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.imagePath = imagePath;  // Corrigido para garantir que o caminho da imagem seja atribuído corretamente
        this.category = category;    // Garantindo que a categoria seja atribuída corretamente
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
