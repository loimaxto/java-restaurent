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
import java.util.Vector;

public class MyTableModel extends AbstractTableModel {
    private final String[] columnNames = {"Mã bàn","Tên bàn", "Tình trạng", "Hành động"};
    private final List<Object[]> data;

    public MyTableModel() {
        data = new ArrayList<>();

    }
    public void addRow(Vector row) {
         data.add(new Object[]{row.get(0),row.get(1), getTableStatus(row.get(2)), ""});
 
    }
    public Object[] getRowValues(int rowIndex) {
        return data.get(rowIndex);
    }
    private String getTableStatus(Object status) {
        if ( (int) status == 0) {
            return "Trống";
        } else {
            return "Đang dùng";
        }
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
        return columnIndex == 3;
    }
    
    public void resetData() {
        data.clear();  
        fireTableDataChanged(); 
    }
}
