package com.example.assignment2_cashregister;

import java.io.Serializable;

public class Product implements Serializable {
    private String name;
    private int quantity;
    private double price;

    public Product(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public void restock(int quantity) {
        this.quantity += quantity;
    }

    public double reducestock(int quantity) {
        this.quantity -= quantity;
        return quantity * price;
    }

    public boolean hasEnoughStock(int quantity) {
        if (this.quantity >= quantity) return true;
        else return false;
    }
}
