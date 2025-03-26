/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.retaurant.GUI.DatBan;

/**
 *
 * @author light
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrderItemPn extends JPanel {

    private String itemName;
    private double itemPrice;
    private int quantity;
    private JLabel itemNameLabel;
    private JLabel priceLabel;
    private JLabel quantityLabel;
    private JLabel sumPriceLabel;
    private JButton increaseButton;
    private JButton decreaseButton;

    public OrderItemPn(String itemName, double itemPrice, int quantity) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.quantity = quantity;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Item Name
        itemNameLabel = new JLabel(itemName);
        add(itemNameLabel, gbc);

        // Price
        gbc.gridx++;
        priceLabel = new JLabel("$" + String.format("%.2f", itemPrice));
        add(priceLabel, gbc);

        // Quantity
        gbc.gridx++;
        quantityLabel = new JLabel(String.valueOf(quantity));
        add(quantityLabel, gbc);

        // Sum Price
        gbc.gridx++;
        sumPriceLabel = new JLabel("$" + String.format("%.2f", itemPrice * quantity));
        add(sumPriceLabel, gbc);

        // Increase Button
        gbc.gridx++;
        increaseButton = new JButton("+");
        increaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                increaseQuantity();
            }
        });
        add(increaseButton, gbc);

        // Decrease Button
        gbc.gridx++;
        decreaseButton = new JButton("-");
        decreaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                decreaseQuantity();
            }
        });
        add(decreaseButton, gbc);
    }

    private void increaseQuantity() {
        quantity++;
        updateQuantity();
    }

    private void decreaseQuantity() {
        if (quantity > 1) {
            quantity--;
            updateQuantity();
        }
    }

    private void updateQuantity() {
        quantityLabel.setText(String.valueOf(quantity));
        sumPriceLabel.setText("$" + String.format("%.2f", itemPrice * quantity));
    }

    public int getQuantity(){
        return quantity;
    }

    public double getSumPrice(){
        return itemPrice * quantity;
    }

    public String getItemName(){
        return itemName;
    }
}
