package com.mycompany.smartecommercesystem;

import com.mycompany.smartecommercesystem.service.CartManager;
import com.mycompany.smartecommercesystem.service.OrderManager;
import com.mycompany.smartecommercesystem.service.ProductManager;
import com.mycompany.smartecommercesystem.ui.WelcomeFrame;
import javax.swing.SwingUtilities;

public class SmartEcommerceSystem {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ProductManager productManager = new ProductManager();
            CartManager cartManager = new CartManager();
            OrderManager orderManager = new OrderManager();
            new WelcomeFrame(productManager, cartManager, orderManager).setVisible(true);
        });
    }
}
