package com.example.retaurant.GUI.DatBan;

import com.example.retaurant.BUS.BanBUS;
import com.example.retaurant.BUS.HoaDonBUS;
import com.example.retaurant.DTO.BanDTO;
import com.example.retaurant.DTO.HoaDonDTO;
import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import javax.swing.AbstractCellEditor;

public class ButtonCellEditor extends AbstractCellEditor implements TableCellEditor {
    private JPanel panel;
    private JButton detailButton, orderButton, cancelButton;
    private int currentRow;
    private JTable table;
    private MyTableModel tableModel;
    private DatBanPN datBanPN;
    static private BanBUS busBan = new BanBUS();
    static private HoaDonBUS busHoaDon = new HoaDonBUS();
    
    public ButtonCellEditor(JTable table, MyTableModel tableModel, DatBanPN datBanPN) {
        this.table = table;
        this.tableModel = tableModel;
        this.datBanPN = datBanPN;
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
    }
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.currentRow = row;
        panel.removeAll(); // Clear previous buttons

        Object rowData = tableModel.getValueAt(currentRow, 1);
        if ((String) rowData != "Trống") {
            detailButton = createDetailButton();
            cancelButton = createCancelButton();
            panel.add(detailButton);
            panel.add(cancelButton);
        } else {
            orderButton = createOrderButton();
            panel.add(orderButton);
        }

        panel.revalidate(); // Revalidate the panel to update its layout
        panel.repaint(); // Repaint the panel to reflect the changes
        return panel;
    }

    private JButton createDetailButton() {
        JButton button = new JButton("Chi tiết");
        button.addActionListener(e -> {
            if (currentRow != -1) {
                
                System.out.println("detail" + tableModel.getRowValues(currentRow)[0]);
                stopCellEditing();
            }
        });
        return button;
    }
    private JButton createOrderButton() {
        JButton button = new JButton("Đặt bàn");
        button.addActionListener(e -> {
            if (currentRow != -1) {
                Object tenBan = tableModel.getValueAt(currentRow, 0);
                int currentIdBan = getBanIdTuTenBan(tenBan.toString());
                System.out.println(" dat ban: " + currentIdBan);
                datBanPN.setTenBanXemChiTietHienTai("Bàn " + currentIdBan );
                int newHoaDonId = busHoaDon.addDefaultHoaDon(currentIdBan);
                
               
                // lay id ban nut duoc nhan 
                BanDTO currentBanDTO = busBan.getBanById(currentIdBan);
                busBan.updateBanDangDuocDat(currentBanDTO,newHoaDonId);
                
                //cai hoa moi tao va cap nhat id hoa don cho ban hiện tại
                datBanPN.setHoaDonForListScrollItemPN(busHoaDon.getBillById(newHoaDonId));
                datBanPN.setBanForListScrollItemPN(currentBanDTO);
                stopCellEditing();
            }
        });
        return button;
    }
    private JButton createCancelButton() {
        JButton button = new JButton("Hủy");
        button.addActionListener(e -> {
            if (currentRow != -1) {
                Object rowData = tableModel.getValueAt(currentRow, 0);
                System.out.println(" huy " + rowData);
                stopCellEditing();
            }
        });
        return button;
    }
     public static int getBanIdTuTenBan(String tenBan) {
        if (tenBan == null || tenBan.isEmpty()) {
            return -1; 
        }
        StringBuilder numbers = new StringBuilder();
        for (char c : tenBan.toCharArray()) {
            if (Character.isDigit(c)) {
                numbers.append(c);
            }
        }
        if (numbers.length() > 0) {
            try {
                return Integer.parseInt(numbers.toString());
            } catch (NumberFormatException e) {
                return -1; 
            }
        } else {
            return -1; 
        }
    }
    @Override
    public Object getCellEditorValue() {
        return null;
    }
}