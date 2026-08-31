package com.mycompany.smartecommercesystem.ui;

import com.mycompany.smartecommercesystem.enums.ProductCategory;
import com.mycompany.smartecommercesystem.model.Product;
import com.mycompany.smartecommercesystem.service.CartManager;
import com.mycompany.smartecommercesystem.service.ProductManager;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ProductCatalogFrame extends JFrame {
    private final ProductManager productManager; 
    private final CartManager cartManager; 
    private final DashboardFrame dashboard;
    private JPanel productsPanel; 
    private JTextField searchField; 
    private JComboBox<String> categoryBox;

    public ProductCatalogFrame(ProductManager pm, CartManager cm, DashboardFrame d) {
        productManager = pm; 
        cartManager = cm; 
        dashboard = d; 
        setTitle("Browse Products"); 
        setSize(1000, 650); 
        setLocationRelativeTo(d); 
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        build(); 
        refresh();
    }

    private void build() {
        JPanel main = new JPanel(new BorderLayout(12, 12)); 
        main.setBorder(BorderFactory.createEmptyBorder(18, 20, 18, 20));

        JLabel title = new JLabel("BROWSE PRODUCTS", SwingConstants.CENTER); 
        title.setFont(new Font("Arial", Font.BOLD, 27)); 
        main.add(title, BorderLayout.NORTH);

        JPanel filter = new JPanel(); 
        searchField = new JTextField(20); 
        categoryBox = new JComboBox<>(); 
        categoryBox.addItem("ALL CATEGORIES"); 
        for (ProductCategory c : ProductCategory.values()) {
            categoryBox.addItem(c.name());
        }

        JButton search = new JButton("SEARCH"); 
        JButton all = new JButton("SHOW ALL"); 

        filter.add(new JLabel("Search:")); 
        filter.add(searchField); 
        filter.add(new JLabel("Category:")); 
        filter.add(categoryBox); 
        filter.add(search); 
        filter.add(all); 
        main.add(filter, BorderLayout.SOUTH);
        productsPanel = new JPanel(new GridLayout(0, 2, 15, 15));

        JScrollPane productScrollPane = new JScrollPane(productsPanel);
        productScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        productScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        main.add(productScrollPane, BorderLayout.CENTER);

        search.addActionListener(e -> refresh());

       categoryBox.addActionListener(e -> refresh());

       all.addActionListener(e -> 
       {
         searchField.setText("");
         categoryBox.setSelectedIndex(0);
        refresh();
       });

      searchField.addActionListener(e -> refresh());

       add(main);
    }

    private void refresh() {
        if (productsPanel == null) {
            return;
        }

        productsPanel.removeAll(); 
        String q = searchField == null ? "" : searchField.getText().trim().toLowerCase(); 
        String cat = categoryBox == null ? "ALL CATEGORIES" : (String) categoryBox.getSelectedItem();

        List<Product> list = productManager.getProducts(); 
        for (Product p : list) {
            boolean match = q.isEmpty() || p.getName().toLowerCase().contains(q) || p.getDescription().toLowerCase().contains(q); 
            boolean cm = "ALL CATEGORIES".equals(cat) || p.getCategory().name().equals(cat); 
            if (match && cm) {
                productsPanel.add(createCard(p));
            }
        }

        if (productsPanel.getComponentCount() == 0) {
            productsPanel.add(new JLabel("No products found.", SwingConstants.CENTER));
        }

        productsPanel.revalidate(); 
        productsPanel.repaint();
    }

    private JPanel createCard(Product p) {
        JPanel card = new JPanel(new BorderLayout(8, 8)); 
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        JLabel name = new JLabel(p.getName(), SwingConstants.CENTER); 
        name.setFont(new Font("Arial", Font.BOLD, 18)); 
        card.add(name, BorderLayout.NORTH);

        JTextArea desc = new JTextArea(p.getDescription()); 
        desc.setEditable(false); 
        desc.setLineWrap(true); 
        desc.setWrapStyleWord(true); 
        desc.setBackground(card.getBackground()); 
        card.add(desc, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new GridLayout(3, 1, 5, 5)); 
        JLabel price = new JLabel("Rs. " + String.format("%.2f", p.getPrice()), SwingConstants.CENTER); 
        price.setFont(new Font("Arial", Font.BOLD, 16)); 

        JLabel stock = new JLabel(p.getStock() > 0 ? "In Stock: " + p.getStock() : "OUT OF STOCK", SwingConstants.CENTER); 
        JSpinner qty = new JSpinner(new SpinnerNumberModel(1, 1, Math.max(1, p.getStock()), 1)); 

        JPanel row = new JPanel(); 
        row.add(new JLabel("Qty:")); 
        row.add(qty); 

        JButton add = new JButton("ADD TO CART"); 
        add.setEnabled(p.getStock() > 0); 
        row.add(add); 

        bottom.add(price); 
        bottom.add(stock); 
        bottom.add(row); 
        card.add(bottom, BorderLayout.SOUTH);

        add.addActionListener(e -> {
            int q = (Integer) qty.getValue(); 
            if (cartManager.addToCart(p, q)) {
                dashboard.updateCartLabel(); 
                JOptionPane.showMessageDialog(this, p.getName() + " added to cart.", "Cart", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Requested quantity is not available in stock.", "Stock Limit", JOptionPane.WARNING_MESSAGE);
            }
        }); 

        return card;
    }
}
