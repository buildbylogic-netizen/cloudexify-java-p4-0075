package com.mycompany.smartecommercesystem.ui;

import com.mycompany.smartecommercesystem.model.CartItem;
import com.mycompany.smartecommercesystem.service.CartManager;
import com.mycompany.smartecommercesystem.service.OrderManager;
import com.mycompany.smartecommercesystem.service.ProductManager;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CartFrame extends JFrame {
    private final CartManager cartManager; 
    private final OrderManager orderManager; 
    private final ProductManager productManager; 
    private final DashboardFrame dashboard;
    private DefaultTableModel model; 
    private JLabel totalLabel;

    public CartFrame(CartManager cm, OrderManager om, ProductManager pm, DashboardFrame d) {
        cartManager = cm;
        orderManager = om;
        productManager = pm;
        dashboard = d;
        setTitle("Shopping Cart");
        setSize(850, 560);
        setLocationRelativeTo(d);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        build();
        refresh();
    }

    private void build() {
        JPanel main = new JPanel(new BorderLayout(12, 12));
        main.setBorder(BorderFactory.createEmptyBorder(18, 20, 18, 20));

        JLabel title = new JLabel("SHOPPING CART", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 27));
        main.add(title, BorderLayout.NORTH);

        String[] cols = {"Product ID", "Product", "Price", "Quantity", "Subtotal"};
        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(28);
        main.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel south = new JPanel(new BorderLayout());
        totalLabel = new JLabel();
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        south.add(totalLabel, BorderLayout.NORTH);

        JPanel buttons = new JPanel();
        JButton update = new JButton("UPDATE QTY");
        JButton remove = new JButton("REMOVE");
        JButton clear = new JButton("CLEAR CART");
        JButton checkout = new JButton("PROCEED TO CHECKOUT");
        JButton close = new JButton("CLOSE");

        for (JButton b : new JButton[]{update, remove, clear, checkout, close}) {
            buttons.add(b);
        }

        south.add(buttons, BorderLayout.SOUTH);
        main.add(south, BorderLayout.SOUTH);

        update.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r < 0) {
                warn("Select an item first.");
                return;
            }
            int id = (Integer) model.getValueAt(r, 0);
        });

        update.addActionListener(e -> updateQuantity(table));
        remove.addActionListener(e -> removeItem(table));
        clear.addActionListener(e -> clearCart());
        checkout.addActionListener(e -> openCheckout());
        close.addActionListener(e -> dispose());

        add(main);
    }

    private void updateQuantity(JTable table) {
        int r = table.getSelectedRow();
        if (r < 0) {
            warn("Select an item first.");
            return;
        }

        int id = (Integer) model.getValueAt(r, 0);
        CartItem item = cartManager.findItem(id);
        String s = JOptionPane.showInputDialog(this, "Enter new quantity:", item.getQuantity());

        if (s == null) {
            return;
        }

        try {
            int q = Integer.parseInt(s.trim());
            if (cartManager.updateQuantity(id, q)) {
                refresh();
            } else {
                warn("Invalid quantity or not enough stock available.");
            }
        } catch (NumberFormatException ex) {
            warn("Quantity must be a whole number.");
        }
    }

    private void removeItem(JTable table) {
        int r = table.getSelectedRow();
        if (r < 0) {
            warn("Select an item first.");
            return;
        }

        int id = (Integer) model.getValueAt(r, 0);
        if (JOptionPane.showConfirmDialog(this, "Remove this item from cart?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            cartManager.removeFromCart(id);
            refresh();
        }
    }

    private void clearCart() {
        if (cartManager.isEmpty()) {
            return;
        }

        if (JOptionPane.showConfirmDialog(this, "Clear all items from the cart?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            cartManager.clearCart();
            refresh();
        }
    }

    private void openCheckout() {
        if (cartManager.isEmpty()) {
            warn("Your shopping cart is empty.");
            return;
        }

        new checkoutFrame(cartManager, orderManager, productManager, dashboard, this).setVisible(true);
    }

    private void refresh() {
        model.setRowCount(0);
        for (CartItem i : cartManager.getCartItems()) {
            model.addRow(new Object[]{
                i.getProduct().getProductId(),
                i.getProduct().getName(),
                String.format("Rs. %.2f", i.getProduct().getPrice()),
                i.getQuantity(),
                String.format("Rs. %.2f", i.getSubtotal())
            });
        }
        totalLabel.setText("Total: Rs. " + String.format("%.2f", cartManager.getCartTotal()));
        dashboard.updateCartLabel();
    }

    public void refreshAfterCheckout() {
        refresh();
    }

    private void warn(String s) {
        JOptionPane.showMessageDialog(this, s, "Notice", JOptionPane.WARNING_MESSAGE);
    }
}
