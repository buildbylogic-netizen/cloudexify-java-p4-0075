package com.mycompany.smartecommercesystem.service;

import com.mycompany.smartecommercesystem.model.CartItem;
import com.mycompany.smartecommercesystem.model.Product;
import java.util.ArrayList;

public class CartManager {
    private final ArrayList<CartItem> items = new ArrayList<>();

    public ArrayList<CartItem> getCartItems() { 
        return items; 
    }

    public boolean addToCart(Product product, int quantity) {
        if (product == null || quantity <= 0 || quantity > product.getStock()) {
            return false;
        }
        
        CartItem existing = findItem(product.getProductId());
        int newQty = quantity + (existing == null ? 0 : existing.getQuantity());
        
        if (newQty > product.getStock()) {
            return false;
        }
        
        if (existing == null) {
            items.add(new CartItem(product, quantity)); 
        } else {
            existing.setQuantity(newQty);
        }
        
        return true;
    }

    public boolean removeFromCart(int productId) {
        CartItem item = findItem(productId);
        return item != null && items.remove(item);
    }

    public boolean updateQuantity(int productId, int quantity) {
        CartItem item = findItem(productId);
        if (item == null || quantity <= 0 || quantity > item.getProduct().getStock()) {
            return false;
        }
        item.setQuantity(quantity); 
        return true;
    }

    public CartItem findItem(int productId) {
        for (CartItem i : items) {
            if (i.getProduct().getProductId() == productId) {
                return i;
            }
        }
        return null;
    }

    public double getCartTotal() { 
        double t = 0; 
        for (CartItem i : items) {
            t += i.getSubtotal(); 
        }
        return t; 
    }

    public int getItemCount() { 
        int n = 0; 
        for (CartItem i : items) {
            n += i.getQuantity(); 
        }
        return n; 
    }

    public boolean isEmpty() { 
        return items.isEmpty(); 
    }
    public void clearCart() { 
        items.clear(); 
    }
}
