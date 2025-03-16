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
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.util.List;
import javax.swing.AbstractCellEditor;

public class ButtonCellEditor extends AbstractCellEditor implements TableCellEditor {
    private JPanel panel;
    private JButton detailButton, orderButton, cancelButton;
    private int currentRow;
    private JTable table;
    private MyTableModel tableModel;

    public ButtonCellEditor(JTable table, MyTableModel tableModel) {
        this.table = table;
        this.tableModel = tableModel;
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));

        detailButton = createButton("Detail");
        orderButton = createButton("Order");
        cancelButton = createButton("Cancel");

        panel.add(detailButton);
        panel.add(orderButton);
        panel.add(cancelButton);
    }

    private JButton createButton(String action) {
        JButton button = new JButton(action);
        button.addActionListener(e -> {
            if (currentRow != -1) {
                Object rowData = tableModel.getValueAt(currentRow, 0);
                System.out.println(action + " clicked for row: " + rowData);
                stopCellEditing();
            }
        });
        return button;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.currentRow = row;
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return null;
    }
}
