package com.example.assignment2_cashregister;

import java.util.ArrayList;

// ProductManager class
// Hold Product list and PurchasedProduct records
public class ProductManager {
    private ArrayList<Product> productList = new ArrayList<>(0);
    private ArrayList<PurchasedProduct> records = new ArrayList<>(0);

    public ProductManager(){
        loadProducts();
    }

    // Load four Products
    public void loadProducts() {

        Product product1 = new Product("Shoes", 100, 20.44);
        Product product2 = new Product("Hats", 30, 5.9);
        Product product3 = new Product("Knits",  20,108.99);
        Product product4 = new Product("Coats",  10,159.99);

        productList.add(product1);
        productList.add(product2);
        productList.add(product3);
        productList.add(product4);

    }
    public ArrayList<Product> getProductList() {
        return productList;
    }

    public ArrayList<PurchasedProduct> getRecords() {
        return records;
    }

    public void setProductList(ArrayList<Product> productList) {
        this.productList = productList;
    }

    public void setRecords(ArrayList<PurchasedProduct> records) {
        this.records = records;
    }

    public Product getProductAt(int index) {
        return productList.get(index);
    }

    public PurchasedProduct getRecodeAt(int index) {
        return records.get(index);
    }

    // Purchase the product
    public double purchase(Product product, int quantity) {
        double totalCost = product.getPrice() * quantity;

        // reduce product
        product.reducestock(quantity);

        // Add the purchase to records
        records.add(new PurchasedProduct(product.getName(), quantity, totalCost) );
        return totalCost;
    }

    // Restock product at the index of productList
    public void restockAt(int index, int quantity) {
        Product product = productList.get(index);
        product.restock(quantity);
    }
}
