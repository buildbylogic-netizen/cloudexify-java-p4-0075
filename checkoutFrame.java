package com.mycompany.smartecommercesystem.ui;

import com.mycompany.smartecommercesystem.service.CartManager;
import com.mycompany.smartecommercesystem.service.OrderManager;
import com.mycompany.smartecommercesystem.service.ProductManager;
import javax.swing.*;
import java.awt.*;

public class checkoutFrame extends JFrame {
    private final CartManager cartManager; 
    private final OrderManager orderManager; 
    private final ProductManager productManager; 
    private final DashboardFrame dashboard; 
    private final CartFrame cartFrame;
    private JTextField name, address, card, expiry, cvv;

    public checkoutFrame(CartManager cm, OrderManager om, ProductManager pm, DashboardFrame d, CartFrame cf) {
        cartManager = cm;
        orderManager = om;
        productManager = pm;
        dashboard = d;
        cartFrame = cf;
        setTitle("Checkout");
        setSize(600, 520);
        setLocationRelativeTo(cf);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        build();
    }

    private void build() {
        JPanel main = new JPanel(new BorderLayout(12, 12));
        main.setBorder(BorderFactory.createEmptyBorder(18, 25, 18, 25));

        JLabel title = new JLabel("CHECKOUT", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        main.add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(6, 2, 10, 12));
        name = new JTextField();
        address = new JTextField();
        card = new JTextField();
        expiry = new JTextField();
        cvv = new JTextField();

        form.add(new JLabel("Card Holder Name:"));
        form.add(name);
        form.add(new JLabel("Delivery Address:"));
        form.add(address);
        form.add(new JLabel("Card Number:"));
        form.add(card);
        form.add(new JLabel("Expiry (MM/YY):"));
        form.add(expiry);
        form.add(new JLabel("CVV:"));
        form.add(cvv);
        form.add(new JLabel("Order Total:"));
        form.add(new JLabel("Rs. " + String.format("%.2f", cartManager.getCartTotal())));
        main.add(form, BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        JButton place = new JButton("PLACE ORDER");
        JButton cancel = new JButton("CANCEL");
        buttons.add(place);
        buttons.add(cancel);
        main.add(buttons, BorderLayout.SOUTH);

        place.addActionListener(e -> placeOrder());
        cancel.addActionListener(e -> dispose());

        add(main);
    }

    private void placeOrder() {
        String n = name.getText().trim();
        String a = address.getText().trim();
        String c = card.getText().replaceAll("\\s+", "").trim();
        String ex = expiry.getText().trim();
        String v = cvv.getText().trim();

        if (n.isEmpty() || a.isEmpty() || c.isEmpty() || ex.isEmpty() || v.isEmpty()) {
            warn("Please fill in all checkout fields.");
            return;
        }

        if (!c.matches("\\d{16}")) {
            warn("Card number must contain 16 digits.");
            return;
        } 
        
        if (!ex.matches("(0[1-9]|1[0-2])/\\d{2}")) {
            warn("Expiry must be in MM/YY format.");
            return;
        } 
        
        if (!v.matches("\\d{3}")) {
            warn("CVV must contain 3 digits.");
            return;
        }

        if (!orderManager.placeOrder(n, a, c.substring(12), cartManager)) {
            warn("Order could not be placed. Please check stock and try again.");
            return;
        }

        productManager.saveData(); 
        dashboard.updateCartLabel(); 
        cartFrame.refreshAfterCheckout(); 

        JOptionPane.showMessageDialog(
            this,
            "Order placed successfully!\nOrder ID: " + (orderManager.nextOrderId() - 1) + "\nPayment: Card ending in " + c.substring(12),
            "Order Confirmed",
            JOptionPane.INFORMATION_MESSAGE
        );
        
        dispose();
    }

    private void warn(String s) {
        JOptionPane.showMessageDialog(this, s, "Checkout Error", JOptionPane.WARNING_MESSAGE);
    }
}
