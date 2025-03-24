/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.retaurant.GUI.DatBan;

/**
 *
 * @author light
 */
import com.example.retaurant.DTO.BanDTO;
import javax.swing.*;
import java.awt.*;

public class ScrollableRowPanelExample extends JFrame {

    public ScrollableRowPanelExample() {
        setTitle("Scrollable Row Panel Example");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BanDTO a = new BanDTO();
        ScrollableRowPanel scrollablePanel = new ScrollableRowPanel(a);
    
        
        // Create row panels
        for (int i = 0; i < 10; i++) {
            OrderItemPn rowPanel = new OrderItemPn("name" + i,1);
            rowPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

            scrollablePanel.addRowPanel(rowPanel);
        }

        add(scrollablePanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ScrollableRowPanelExample();
            }
        });
    }
}