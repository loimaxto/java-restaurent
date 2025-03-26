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
import javax.swing.table.TableColumn;
import java.awt.*;

public class TableWithButtons extends JFrame {

    public TableWithButtons() {
        setTitle("JTable with Buttons");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        MyTableModel tableModel = new MyTableModel();
        JTable table = new JTable(tableModel);

        TableColumn column = table.getColumnModel().getColumn(2);
        column.setCellRenderer(new ButtonCellRenderer());
//        column.setCellEditor(new ButtonCellEditor(table, tableModel));

        add(new JScrollPane(table));
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TableWithButtons::new);
    }
}
