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
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ButtonCellRenderer extends JPanel implements TableCellRenderer {
    public ButtonCellRenderer() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
//        panel.add(new JButton("Chi tiết"));
//        panel.add(new JButton("Đặt bàn"));
//        panel.add(new JButton("Hủy"));
        JButton orderButton = new JButton("Đặt bàn");
        JButton detailButton = new JButton("Chi tiết");
        JButton cancelButton = new JButton("Hủy");
        
         if (table.getModel() instanceof MyTableModel) {
            MyTableModel model = (MyTableModel) table.getModel();
            Object[] rowData =  model.getRowValues(row);
            if (  rowData[1] == "Đang dùng") {
                panel.add(detailButton);
                panel.add(cancelButton);
            } else {
                panel.add(orderButton);
            }
        }
        return panel;
    }
}