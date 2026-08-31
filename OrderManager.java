package com.mycompany.smartecommercesystem.service;

import com.mycompany.smartecommercesystem.model.CartItem;
import com.mycompany.smartecommercesystem.model.Order;
import java.io.*;
import java.util.ArrayList;

public class OrderManager {
    private final ArrayList<Order> orders = new ArrayList<>();
    private static final String FILE_NAME = "orders.dat";

    public OrderManager() { 
        loadData(); 
    }

    public ArrayList<Order> getOrders() { 
        return orders; 
    }

    public int nextOrderId() { 
        int max = 1000; 
        for (Order o : orders) {
            if (o.getOrderId() > max) {
                max = o.getOrderId();
            }
        }
        return max + 1; 
    }

    public boolean placeOrder(String customerName, String address, String cardLastFour, CartManager cart) {
        if (customerName == null || customerName.isBlank() || address == null || address.isBlank() || cart == null || cart.isEmpty()) {
            return false;
        }

        for (CartItem item : cart.getCartItems()) {
            if (item.getQuantity() > item.getProduct().getStock()) {
                return false;
            }
        }

        ArrayList<CartItem> snapshot = new ArrayList<>();
        for (CartItem item : cart.getCartItems()) {
            item.getProduct().reduceStock(item.getQuantity());
            snapshot.add(new CartItem(item.getProduct(), item.getQuantity()));
        }

        orders.add(new Order(nextOrderId(), customerName.trim(), address.trim(), cardLastFour, snapshot, cart.getCartTotal()));
        saveData(); 
        cart.clearCart(); 
        return true;
    }

    public void saveData() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(orders);
        } catch (IOException e) {
            System.err.println("Could not save orders: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked") 
    private void loadData() {
        File f = new File(FILE_NAME); 
        if (!f.exists()) {
            return;
        }
        
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(f))) {
            orders.addAll((ArrayList<Order>) in.readObject());
        } catch (IOException | ClassNotFoundException e) {
            orders.clear();
        }
    }
}
