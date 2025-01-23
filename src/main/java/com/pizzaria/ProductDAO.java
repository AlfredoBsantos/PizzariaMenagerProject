package com.pizzaria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    // Método para inserir um novo produto
    public boolean addProduct(Product product) {
        String sql = "INSERT INTO products (name, description, price, stock, image, category) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Atribuindo corretamente os valores às colunas do banco de dados
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setDouble(3, product.getPrice());
            statement.setInt(4, product.getStock());
            statement.setString(5, product.getImagePath()); // Garantindo que a imagem vai para a coluna 'image'
            statement.setString(6, product.getCategory()); // Garantindo que a categoria vai para a coluna 'category'

            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Erro ao adicionar produto: " + e.getMessage());
            return false;
        }
    }

    public Product getProductByName(String productName) {
        // Exemplo simples. Aqui você pode procurar o produto no banco de dados ou lista.
        for (Product product : getAllProducts()) {
            if (product.getName().equalsIgnoreCase(productName)) {
                return product;
            }
        }
        return null; // Retorna null se não encontrar
    }


    // Método para listar todos os produtos
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                // Criando o produto com os dados recuperados do banco
                Product product = new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("stock"),
                        resultSet.getString("image"),  // O campo no banco de dados é "image"
                        resultSet.getString("category") // O campo no banco de dados é "category"
                );
                products.add(product);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar produtos: " + e.getMessage());
        }

        return products;
    }

    // Método para buscar produtos pelo nome
    public List<Product> searchProducts(String query) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE name LIKE ?";

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, "%" + query + "%");
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    // Criando o produto com os dados recuperados
                    Product product = new Product(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("description"),
                            resultSet.getDouble("price"),
                            resultSet.getInt("stock"),
                            resultSet.getString("image"),  // O campo no banco de dados é "image"
                            resultSet.getString("category") // O campo no banco de dados é "category"
                    );
                    products.add(product);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar produtos: " + e.getMessage());
        }

        return products;
    }

    // Método para filtrar produtos por categoria
    public List<Product> getProductsByCategory(String category) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE category = ?";

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, category);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    // Criando o produto com os dados recuperados
                    Product product = new Product(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("description"),
                            resultSet.getDouble("price"),
                            resultSet.getInt("stock"),
                            resultSet.getString("image"),  // O campo no banco de dados é "image"
                            resultSet.getString("category") // O campo no banco de dados é "category"
                    );
                    products.add(product);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao filtrar produtos por categoria: " + e.getMessage());
        }

        return products;
    }

    // Método para atualizar um produto
    public boolean updateProduct(Product product) {
        String sql = "UPDATE products SET name = ?, description = ?, price = ?, stock = ?, image = ?, category = ? WHERE id = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Atualizando os dados corretamente
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setDouble(3, product.getPrice());
            statement.setInt(4, product.getStock());
            statement.setString(5, product.getImagePath());  // Garantindo que a imagem vai para a coluna 'image'
            statement.setString(6, product.getCategory());   // Garantindo que a categoria vai para a coluna 'category'
            statement.setInt(7, product.getId());
            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar produto: " + e.getMessage());
            return false;
        }
    }

    // Método para excluir um produto
    public boolean deleteProduct(int id) {
        String sql = "DELETE FROM products WHERE id = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Erro ao excluir produto: " + e.getMessage());
            return false;
        }
    }
}
