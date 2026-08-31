package com.mycompany.smartecommercesystem.service;

import com.mycompany.smartecommercesystem.enums.ProductCategory;
import com.mycompany.smartecommercesystem.model.Product;
import java.io.*;
import java.util.ArrayList;

public class ProductManager {
    private final ArrayList<Product> products = new ArrayList<>();
    private static final String FILE_NAME = "products.dat";

    public ProductManager() {
        loadData();
        if (products.isEmpty()) {
            loadSampleProducts();
        }
    }

    public ArrayList<Product> getProducts() { 
        return products; 
    }

    public Product searchProduct(int id) {
        for (Product p : products) {
            if (p.getProductId() == id) {
                return p;
            }
        }
        return null;
    }

    public ArrayList<Product> searchProducts(String text) {
        ArrayList<Product> result = new ArrayList<>();
        String q = text.toLowerCase().trim();
        for (Product p : products) {
            if (p.getName().toLowerCase().contains(q) ||
                p.getCategory().name().toLowerCase().contains(q) ||
                p.getDescription().toLowerCase().contains(q)) {
                result.add(p);
            }
        }
        return result;
    }

    public void saveData() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(products);
        } catch (IOException e) { 
            System.err.println("Could not save products: " + e.getMessage()); 
        }
    }

    @SuppressWarnings("unchecked")
    private void loadData() {
        File f = new File(FILE_NAME);
        if (!f.exists()) {
            return;
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(f))) {
            products.addAll((ArrayList<Product>) in.readObject());
        } catch (IOException | ClassNotFoundException e) {
            products.clear();
        }
    }

    private void loadSampleProducts() {
        products.add(new Product(101, "Wireless Headphones", "Bluetooth headphones with clear sound and comfortable design.", 4500, 12, ProductCategory.ELECTRONICS));
        products.add(new Product(102, "Smart Watch", "Fitness tracking smart watch with notifications.", 6500, 8, ProductCategory.ELECTRONICS));
        products.add(new Product(103, "Casual Hoodie", "Comfortable cotton hoodie for everyday wear.", 2800, 15, ProductCategory.CLOTHING));
        products.add(new Product(104, "Java Programming Book", "Beginner-friendly guide to Java and OOP concepts.", 2200, 10, ProductCategory.BOOKS));
        products.add(new Product(105, "Coffee Maker", "Compact home coffee maker for quick brewing.", 7200, 6, ProductCategory.HOME_APPLIANCES));
        products.add(new Product(106, "Sports Shoes", "Lightweight running shoes with cushioned sole.", 5200, 9, ProductCategory.SPORTS));
        saveData();
    }
}
