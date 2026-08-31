package com.mycompany.smartecommercesystem.model;

import com.mycompany.smartecommercesystem.enums.OrderStatus;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Order implements Serializable {
    private static final long serialVersionUID = 2L;
    private final int orderId;
    private final String customerName;
    private final String shippingAddress;
    private final String cardLastFour;
    private final ArrayList<CartItem> items;
    private final double totalAmount;
    private final String orderDate;
    private OrderStatus status;

    public Order(int orderId, String customerName, String shippingAddress, String cardLastFour,
                 ArrayList<CartItem> items, double totalAmount) 
    {
        this.orderId = orderId;
        this.customerName = customerName;
        this.shippingAddress = shippingAddress;
        this.cardLastFour = cardLastFour;
        this.items = new ArrayList<>(items);
        this.totalAmount = totalAmount;
        this.orderDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        this.status = OrderStatus.CONFIRMED;
    }
    public int getOrderId()
    { 
        return orderId;
    }
    public String getCustomerName() 
    { 
        return customerName; 
    }
    public String getShippingAddress()
    { 
        return shippingAddress; 
    }
    public String getCardLastFour()
    { 
        return cardLastFour; 
    }
    public ArrayList<CartItem> getItems() 
    { 
        return new ArrayList<>(items);
    }
    public double getTotalAmount() 
    {
        return totalAmount;
    }
    public String getOrderDate() 
    { 
        return orderDate; 
    }
    public OrderStatus getStatus() 
   {
       return status; 
   }
    public void setStatus(OrderStatus status)
    { 
        this.status = status; 
    }
}
