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

public class OrderItemPnExample extends JFrame {

    public OrderItemPnExample() {
        setTitle("Order Item Panel Example");
        setSize(500, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Create an OrderItemPn
        OrderItemPn itemPanel = new OrderItemPn("Pizza", 2);
        add(itemPanel);

        // Create Another OrderItemPn
        OrderItemPn itemPanel2 = new OrderItemPn("Burger", 1);
        add(itemPanel2);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new OrderItemPnExample();
            }
        });
    }
}