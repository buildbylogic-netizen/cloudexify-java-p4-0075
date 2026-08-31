package com.mycompany.smartecommercesystem.ui;

import javax.swing.*;
import java.awt.*;

public class AboutFrame extends JFrame {

    public AboutFrame() {
        setTitle("About");
        setSize(600, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel p = new JPanel(new BorderLayout(15, 15));
        p.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));

        JLabel t = new JLabel("SMART E-COMMERCE SYSTEM", SwingConstants.CENTER);
        t.setFont(new Font("Arial", Font.BOLD, 25));
        p.add(t, BorderLayout.NORTH);

        JTextArea a = new JTextArea(
            "This desktop e-commerce application lets a customer browse products, search by name or category,"
                    + " add products to a shopping cart, review prices and quantities, "
                    + "complete checkout using card details,"
                    + " and view previous orders.\n\nBuilt in Java Swing using object-oriented programming, "
                    + "collections, file handling, validation, and separate model, "
                    + "service, and UI classes."
        );
        a.setFont(new Font("Arial", Font.PLAIN, 15));
        a.setLineWrap(true);
        a.setWrapStyleWord(true);
        a.setEditable(false);
        a.setBackground(p.getBackground());
        p.add(a, BorderLayout.CENTER);

        JButton c = new JButton("CLOSE");
        c.addActionListener(e -> dispose());

        JPanel b = new JPanel();
        b.add(c);
        p.add(b, BorderLayout.SOUTH);

        add(p);
    }
}
