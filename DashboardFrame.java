package com.mycompany.smartecommercesystem.ui;

import com.mycompany.smartecommercesystem.service.CartManager;
import com.mycompany.smartecommercesystem.service.OrderManager;
import com.mycompany.smartecommercesystem.service.ProductManager;
import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {
    private final ProductManager productManager; 
    private final CartManager cartManager; 
    private final OrderManager orderManager;
    private JLabel cartLabel;

    public DashboardFrame(ProductManager pm, CartManager cm, OrderManager om) {
        productManager = pm; 
        cartManager = cm; 
        orderManager = om; 
        setTitle("Smart E-Commerce System"); 
        setSize(900, 600); 
        setLocationRelativeTo(null); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setResizable(false); 
        build();
    }

    private void build() {
        JPanel main = new JPanel(new BorderLayout(20, 20)); 
        main.setBorder(BorderFactory.createEmptyBorder(30, 40, 25, 40));

        JLabel title = new JLabel("WELCOME TO SMART SHOP", SwingConstants.CENTER); 
        title.setFont(new Font("Arial", Font.BOLD, 30)); 
        main.add(title, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(2, 2, 20, 20));
        JButton browse = button("BROWSE PRODUCTS"); 
        JButton cart = button("SHOPPING CART"); 
        JButton history = button("ORDER HISTORY"); 
        JButton about = button("ABOUT");

        center.add(browse); 
        center.add(cart); 
        center.add(history); 
        center.add(about); 
        main.add(center, BorderLayout.CENTER);

        JPanel south = new JPanel(new BorderLayout()); 
        cartLabel = new JLabel(); 
        updateCartLabel(); 
        south.add(cartLabel, BorderLayout.WEST); 

        JButton exit = button("EXIT"); 
        exit.setPreferredSize(new Dimension(130, 42)); 
        south.add(exit, BorderLayout.EAST); 
        main.add(south, BorderLayout.SOUTH);

        browse.addActionListener(e -> new ProductCatalogFrame(productManager, cartManager, this).setVisible(true));
        cart.addActionListener(e -> new CartFrame(cartManager, orderManager, productManager, this).setVisible(true));
        history.addActionListener(e -> new OrderHistoryFrame(orderManager).setVisible(true));
        about.addActionListener(e -> new AboutFrame().setVisible(true));
        exit.addActionListener(e -> ExitConfirmation.showExitConfirmation(this)); 
        
        add(main);
    }

    private JButton button(String s) {
        JButton b = new JButton(s); 
        b.setFont(new Font("Arial", Font.BOLD, 15)); 
        b.setFocusPainted(false); 
        return b;
    }

    public void updateCartLabel() { 
        if (cartLabel != null) {
            cartLabel.setText("Items in cart: " + cartManager.getItemCount() + "    |    Cart total: Rs. " + String.format("%.2f", cartManager.getCartTotal())); 
        }
    }
}
