/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.retaurant.GUI.HoaDon;

/**
 *
 * @author light
 */
import com.example.retaurant.GUI.DatBan.*;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class HoaDonTableCellRender extends JPanel implements TableCellRenderer {
    public HoaDonTableCellRender() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        JButton detailButton = new JButton("Chi tiết");
        
         if (table.getModel() instanceof HoaDonTableModel) {
            panel.add(detailButton);
        }
        return panel;
    }
}