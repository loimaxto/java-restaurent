package com.example.retaurant.GUI.HoaDon;

import com.example.retaurant.GUI.DatBan.*;
import com.example.retaurant.BUS.BanBUS;
import com.example.retaurant.BUS.HoaDonBUS;
import com.example.retaurant.DTO.BanDTO;
import com.example.retaurant.DTO.HoaDonDTO;
import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import javax.swing.AbstractCellEditor;

public class HoaDonTableCellEditor extends AbstractCellEditor implements TableCellEditor {

    private JPanel panel;
    private JButton detailButton;
    private int currentRow;
    private JTable table;
    private HoaDonTableModel tableModel;

    public HoaDonTableCellEditor(JTable table, HoaDonTableModel tableModel, HoaDonPN hoaDonPN) {
        this.table = table;
        this.tableModel = tableModel;
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));

    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.currentRow = row;
        panel.removeAll(); // Clear previous buttons

        detailButton = createChiTietBtn();
        panel.add(detailButton);

        panel.revalidate(); // Revalidate the panel to update its layout
        panel.repaint(); // Repaint the panel to reflect the changes
        return panel;
    }

    private JButton createChiTietBtn() {
        JButton button = new JButton("Chi tiết");
        button.addActionListener(e -> {
            System.out.println("test");
        });
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return null;
    }
}
