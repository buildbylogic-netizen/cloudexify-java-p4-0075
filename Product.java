package com.mycompany.smartecommercesystem.model;

import com.mycompany.smartecommercesystem.enums.ProductCategory;
import java.io.Serializable;

public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    private final int productId;
    private String name;
    private String description;
    private double price;
    private int stock;
    private ProductCategory category;

    public Product(int productId, String name, String description, 
            double price, int stock, ProductCategory category)
    {
        if (productId <= 0 || name == null || name.isBlank() || 
                price < 0 || stock < 0 || category == null)
        {
            throw new IllegalArgumentException("Invalid product information.");
        }
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category = category;
    }

    public int getProductId()
    { 
        return productId; 
    }
    public String getName()
    { 
        return name; 
    }
    public String getDescription()
    { 
        return description;
    }
    public double getPrice()
    {
        return price;
    }
    public int getStock() 
    { 
        return stock;
    }
    public ProductCategory getCategory()
    { 
        return category;
    }
    public void setName(String name)
    { 
        this.name = name;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }
    public void setPrice(double price) 
    { 
        this.price = price;
    }
    public void setStock(int stock)
    {
        this.stock = stock; 
    }
    public void setCategory(ProductCategory category)
    { 
        this.category = category; 
    }

    public boolean reduceStock(int quantity)
    {
        if (quantity <= 0 || quantity > stock) return false;
        stock -= quantity;
        return true;
    }

    public String toString() {
        return name + " - Rs. " + String.format("%.2f", price);
    }
}
