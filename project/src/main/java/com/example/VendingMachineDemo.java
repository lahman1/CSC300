package com.example;

import java.util.HashMap;
import java.util.Map;

// Product Class
class Product {
    String productID;
    String name;
    float price;
    int stock;

    public Product(String productID, String name, float price, int stock) {
        this.productID = productID;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public void updateStock(int quantity) {
        stock += quantity;
    }
}

// VendingMachine Class
class VendingMachine {
    private Map<String, Product> products = new HashMap<>();
    private float currentAmount;

    public void addProduct(Product product) {
        products.put(product.productID, product);
    }

    public void selectProduct(String productID) {
        if (products.containsKey(productID)) {
            Product product = products.get(productID);
            if (product.stock > 0) {
                if (currentAmount >= product.price) {
                    dispenseProduct(product);
                } else {
                    System.out.println("Insufficient funds. Please add more money.");
                }
            } else {
                System.out.println("Product sold out.");
            }
        } else {
            System.out.println("Invalid product selection.");
        }
    }

    public void addCoin(float coinValue) {
        currentAmount += coinValue;
        System.out.println("Inserted: " + coinValue);
    }

    public void swipeCard(float amount) {
        currentAmount += amount;
        System.out.println("Card swiped: " + amount);
    }

    private void dispenseProduct(Product product) {
        product.stock--;
        currentAmount -= product.price;
        System.out.println("Dispensing: " + product.name);
        if (currentAmount > 0) {
            returnCoins();
        }
    }

    public void returnCoins() {
        System.out.println("Returning change: " + currentAmount);
        currentAmount = 0;
    }

    public void restockProduct(String productID, int quantity) {
        if (products.containsKey(productID)) {
            products.get(productID).updateStock(quantity);
            System.out.println("Restocked " + quantity + " units of " + productID);
        } else {
            System.out.println("Product not found for restocking.");
        }
    }
}

// Operator Class
class Operator {
    String operatorID;

    public Operator(String operatorID) {
        this.operatorID = operatorID;
    }

    public void restockProducts(VendingMachine machine, String productID, int quantity) {
        machine.restockProduct(productID, quantity);
    }

    public void removeMoney(VendingMachine machine) {
        System.out.println("Money removed from vending machine.");
        // Simulate money removal logic here
    }
}

// User Class
class User {
    String userID;
    float insertedAmount;

    public User(String userID) {
        this.userID = userID;
    }

    public void insertCoin(VendingMachine machine, float coinValue) {
        machine.addCoin(coinValue);
    }

    public void selectProduct(VendingMachine machine, String productID) {
        machine.selectProduct(productID);
    }

    public void swipeCard(VendingMachine machine, float amount) {
        machine.swipeCard(amount);
    }
}

// Main Class to Demonstrate the Simulation
public class VendingMachineDemo {
    public static void main(String[] args) {
        // Create a VendingMachine instance
        VendingMachine machine = new VendingMachine();

        // Create and add products
        Product coke = new Product("P001", "Coke", 1.5f, 10);
        Product chips = new Product("P002", "Chips", 2.0f, 5);
        machine.addProduct(coke);
        machine.addProduct(chips);

        // Create a User instance
        User user = new User("U001");

        // User interacts with the vending machine
        user.insertCoin(machine, 1.0f);
        user.insertCoin(machine, 0.5f);
        user.selectProduct(machine, "P001");

        // Demonstrate card payment and purchase
        user.swipeCard(machine, 5.0f);
        user.selectProduct(machine, "P002");

        // Create an Operator instance
        Operator operator = new Operator("O001");
        operator.restockProducts(machine, "P002", 10);
        operator.removeMoney(machine);
    }
}
