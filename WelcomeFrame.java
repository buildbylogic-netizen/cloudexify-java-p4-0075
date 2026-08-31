package com.mycompany.smartecommercesystem.ui;

import com.mycompany.smartecommercesystem.service.CartManager;
import com.mycompany.smartecommercesystem.service.OrderManager;
import com.mycompany.smartecommercesystem.service.ProductManager;
import javax.swing.*;
import java.awt.*;

public class WelcomeFrame extends JFrame {
    private final ProductManager productManager;
    private final CartManager cartManager;
    private final OrderManager orderManager;

    public WelcomeFrame(ProductManager productManager, CartManager cartManager,
            OrderManager orderManager)
    {
        this.productManager = productManager; 
        this.cartManager = cartManager; 
        this.orderManager = orderManager;
        setTitle("Smart E-Commerce System"); 
        setSize(760, 500); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setResizable(false);
        build();
    }

    private void build() {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints(); 
        g.gridx = 0; 
        g.fill = GridBagConstraints.HORIZONTAL; 
        g.insets = new Insets(10, 10, 10, 10);

        JLabel title = new JLabel("SMART E-COMMERCE SYSTEM", SwingConstants.CENTER); 
        title.setFont(new Font("Arial", Font.BOLD, 34)); 
        g.gridy = 0; 
        p.add(title, g);

        JLabel sub = new JLabel("Browse products, add items to your cart and place your order", SwingConstants.CENTER); 
        sub.setFont(new Font("Arial", Font.PLAIN, 16)); 
        g.gridy = 1; 
        p.add(sub, g);

        JButton start = new JButton("START SHOPPING"); 
        start.setFont(new Font("Arial", Font.BOLD, 18)); 
        start.setPreferredSize(new Dimension(220, 50)); 
        g.gridy = 2; 
        g.insets = new Insets(35, 10, 10, 10); 
        p.add(start, g);

        start.addActionListener(e -> {
            new DashboardFrame(productManager, cartManager, orderManager).setVisible(true); 
            dispose();
        });

        add(p);
    }
}
