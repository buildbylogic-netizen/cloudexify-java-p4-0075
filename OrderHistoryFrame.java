package com.mycompany.smartecommercesystem.ui;

import com.mycompany.smartecommercesystem.model.CartItem;
import com.mycompany.smartecommercesystem.model.Order;
import com.mycompany.smartecommercesystem.service.OrderManager;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class OrderHistoryFrame extends JFrame {
    private final OrderManager orderManager; 
    private DefaultTableModel model;

    public OrderHistoryFrame(OrderManager om) {
        orderManager = om;
        setTitle("Order History");
        setSize(950, 570);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        build();
        refresh();
    }

    private void build() {
        JPanel main = new JPanel(new BorderLayout(12, 12));
        main.setBorder(BorderFactory.createEmptyBorder(18, 20, 18, 20));

        JLabel title = new JLabel("MY ORDER HISTORY", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 27));
        main.add(title, BorderLayout.NORTH);

        String[] cols = {"Order ID", "Customer", "Date", "Items", "Total", "Payment", "Status"};
        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(28);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

       table.getColumnModel().getColumn(0).setPreferredWidth(100);  
       table.getColumnModel().getColumn(1).setPreferredWidth(160);  
       table.getColumnModel().getColumn(2).setPreferredWidth(160);
       table.getColumnModel().getColumn(3).setPreferredWidth(300);  
       table.getColumnModel().getColumn(4).setPreferredWidth(130);  
       table.getColumnModel().getColumn(5).setPreferredWidth(140);  
       table.getColumnModel().getColumn(6).setPreferredWidth(130);  
       
       JScrollPane scrollPane = new JScrollPane(table);
       main.add(new JScrollPane(table), BorderLayout.CENTER);
       table.getColumnModel().getColumn(3).setPreferredWidth(350);

        JButton details = new JButton("VIEW DETAILS");
        JButton close = new JButton("CLOSE");
        JPanel bp = new JPanel();
        bp.add(details);
        bp.add(close);
        main.add(bp, BorderLayout.SOUTH);

        details.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r < 0) {
                JOptionPane.showMessageDialog(this, "Select an order first.", "Notice", JOptionPane.WARNING_MESSAGE);
                return;
            }
            showDetails((Integer) model.getValueAt(r, 0));
        });

        close.addActionListener(e -> dispose());
        add(main);
    }

    private void refresh() {
        model.setRowCount(0);
        for (Order o : orderManager.getOrders()) {
            StringBuilder items = new StringBuilder();
            for (CartItem i : o.getItems()) {
                if (items.length() > 0) {
                    items.append(", ");
                }
                items.append(i.getProduct().getName()).append(" x").append(i.getQuantity());
            }
            model.addRow(new Object[]{
                o.getOrderId(),
                o.getCustomerName(),
                o.getOrderDate(),
                items.toString(),
                String.format("Rs. %.2f", o.getTotalAmount()),
                "**** " + o.getCardLastFour(),
                o.getStatus()
            });
        }
    }

    private void showDetails(int id) {
        for (Order o : orderManager.getOrders()) {
            if (o.getOrderId() == id) {
                StringBuilder s = new StringBuilder("Order ID: " + o.getOrderId() + "\nCustomer: " + o.getCustomerName() + "\nAddress: " + o.getShippingAddress() + "\nDate: " + o.getOrderDate() + "\n\nItems:\n");
                for (CartItem i : o.getItems()) {
                    s.append("• ").append(i.getProduct().getName()).append(" x ").append(i.getQuantity()).append(" = Rs. ").append(String.format("%.2f", i.getSubtotal())).append("\n");
                }
                s.append("\nTotal: Rs. ").append(String.format("%.2f", o.getTotalAmount())).append("\nPayment: Card ending ").append(o.getCardLastFour());
                JOptionPane.showMessageDialog(this, s.toString(), "Order Details", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }
    }
}
