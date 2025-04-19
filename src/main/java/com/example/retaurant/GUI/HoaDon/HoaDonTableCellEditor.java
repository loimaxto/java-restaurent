package com.example.retaurant.GUI.HoaDon;

import com.example.retaurant.DTO.HoaDonDTO2;
import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import javax.swing.AbstractCellEditor;

public class HoaDonTableCellEditor extends AbstractCellEditor implements TableCellEditor {

    private JPanel panel;
    private JButton detailButton;
    private int currentRow;
    private HoaDonTableModel tableModel;
    private JTable table;
    private HoaDonPN hdPn;
    public HoaDonTableCellEditor(JTable table, HoaDonTableModel tableModel, HoaDonPN hoaDonPN) {
        this.tableModel = tableModel;
        this.hdPn = hoaDonPN;
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));

    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.currentRow = row;
        this.table = table;
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
             System.out.println("asd");
            HoaDonDTO2 hdDto = tableModel.getHoaDonDTO(currentRow);
            System.out.println(hdDto.toString());
            ChiTietHoaDonModal cthdm = new ChiTietHoaDonModal(hdPn, hdDto);
            cthdm.setLocationRelativeTo(hdPn); // Center relative to the parent
            cthdm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Crucial line
            cthdm.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
            cthdm.setVisible(true);
        });
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return null;
    }
}
