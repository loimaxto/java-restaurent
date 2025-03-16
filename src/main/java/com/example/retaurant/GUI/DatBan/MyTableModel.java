/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.retaurant.GUI.DatBan;

/**
 *
 * @author light
 */
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class MyTableModel extends AbstractTableModel {
    private final String[] columnNames = {"ID", "Name", "Actions"};
    private final List<Object[]> data;

    public MyTableModel() {
        data = new ArrayList<>();
        data.add(new Object[]{1, "Table 1", ""});
        data.add(new Object[]{2, "Table 2", ""});
        data.add(new Object[]{3, "Table 3", ""});
        data.add(new Object[]{4, "Table 4", ""});
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data.get(rowIndex)[columnIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 2;
    }
}
