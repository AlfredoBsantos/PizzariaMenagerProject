package com.pizzaria;

public class Client {
    private int id;
    private String name;
    private String phone;

    // Construtor completo (usado para buscas e atualizações)
    public Client(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    // Construtor sem ID (usado para inserções)
    public Client(String name, String phone) {
        this.name = name;
        this.phone = phone;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
