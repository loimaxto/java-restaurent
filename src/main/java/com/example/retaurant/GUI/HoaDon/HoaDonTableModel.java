/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.retaurant.GUI.HoaDon;

/**
 *
 * @author light
 */
import com.example.retaurant.DTO.HoaDonDTO2;
import com.example.retaurant.GUI.DatBan.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class HoaDonTableModel extends AbstractTableModel {
    private final String[] columnNames = {"Id","Thời gian", "Người tạo", "Khách hàng","Hành động"};
    private final List<HoaDonDTO2> hoaDonList;
    private final List<Object[]> data;
    
    public HoaDonTableModel() {
        hoaDonList = new ArrayList<>();
        data = new ArrayList<>();
    }
    public void addRow(HoaDonDTO2 hd) {
        hoaDonList.add(hd);
        data.add(new Object[]{
            hd.getHdId(),
            hd.getThoiGian(),
            hd.getHoTenNv(),
            hd.getHoKh()+hd.getTenKh(),
            "btn"
        });
    }
    public HoaDonDTO2 getHoaDonDTO(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < hoaDonList.size()) {
            return hoaDonList.get(rowIndex);
        }
        throw new IllegalArgumentException("Invalid row index: " + rowIndex);
    }
    
    

//    @Override
//    public boolean isCellEditable(int rowIndex, int columnIndex) {
//        return columnIndex == 2;
//    }
    
    public void resetData() {
        hoaDonList.clear();  
        fireTableDataChanged(); 
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
