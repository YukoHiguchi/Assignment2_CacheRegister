package com.example.assignment2_cashregister;

import java.io.Serializable;
import java.util.Date;

// PurchasedProduct class
// This class holds user's purchased item information
public class PurchasedProduct implements Serializable {
    private final String name;
    private final int quantity;
    private final double totalPrice;
    private final Date timestamp;


    public PurchasedProduct(String name, int quantity, double totalPrice) {
        this.name = name;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.timestamp = new Date();
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
