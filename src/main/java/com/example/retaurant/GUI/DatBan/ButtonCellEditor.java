package com.example.retaurant.GUI.DatBan;

import com.example.retaurant.BUS.BanBUS;
import com.example.retaurant.BUS.HoaDonBUS;
import com.example.retaurant.DTO.BanDTO;
import com.example.retaurant.DTO.HoaDonDTO;
import org.apache.xmlbeans.impl.xb.xsdschema.impl.PublicImpl;

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

        Object rowData = tableModel.getValueAt(currentRow, 2);
        if ((String) rowData != "Trống") {
            System.out.println("trong");
            detailButton = createChiTietBtn();
            cancelButton = createCancelButton();
            panel.add(detailButton);
            panel.add(cancelButton);
        } else {
            System.out.println("aaa");
            orderButton = createDatBanBtn();
            panel.add(orderButton);
        }

        panel.revalidate(); // Revalidate the panel to update its layout
        panel.repaint(); // Repaint the panel to reflect the changes
        return panel;
    }

    private JButton createChiTietBtn() {
        JButton button = new JButton("Chi tiết");
        button.addActionListener(e -> {
            if (currentRow != -1) {
                int currentIdBan = (int) tableModel.getValueAt(currentRow, 0);
                BanDTO currentBanDTO = busBan.getBanById(currentIdBan);
                datBanPN.setTenBanXemChiTietHienTai(currentBanDTO.getTenBan());

                //lay hoa moi tao va cap nhat id hoa don cho ban hiện tại
                HoaDonDTO hoaDonDTO = busHoaDon.getBillById(currentBanDTO.getIdHoaDonHienTai());
                datBanPN.setHoaDonForListScrollItemPN(hoaDonDTO);
                datBanPN.setBanForListScrollItemPN(currentBanDTO);
                datBanPN.renderMonAnTrongHoaDon();
                datBanPN.clearCustomerInforForTable();
                if (hoaDonDTO.getKhId() != null) {
                    System.out.println(hoaDonDTO.toString());
                    datBanPN.updateCustomerInforForTable(hoaDonDTO.getKhId());
                }
            }
        });
        return button;
    }

    private JButton createDatBanBtn() {
        JButton button = new JButton("Đặt bàn");
        button.addActionListener(e -> {
            if (currentRow != -1) {
                int currentIdBan = (int) tableModel.getValueAt(currentRow, 0);
                BanDTO currentBanDTO = busBan.getBanById(currentIdBan);
                datBanPN.setTenBanXemChiTietHienTai(currentBanDTO.getTenBan());
                //tao hoa don moi cho ban được đặt
                int newHoaDonId = busHoaDon.addDefaultHoaDon(currentIdBan, datBanPN.getCreatorId());
                // lay id ban nut duoc nhan và cập nhật hóa đơn của bàn
                busBan.updateBanDangDuocDat(currentBanDTO, newHoaDonId);

                //cap nha thoa moi tao va cap nhat id hoa don cho ban hiện tại
                datBanPN.setHoaDonForListScrollItemPN(busHoaDon.getBillById(newHoaDonId));
                datBanPN.setBanForListScrollItemPN(currentBanDTO);
                datBanPN.renderThongTinBan();
                datBanPN.renderMonAnTrongHoaDon();
                stopCellEditing();
            }
        });
        return button;
    }

    private JButton createCancelButton() {
        JButton button = new JButton("Hủy");
        button.addActionListener(e -> {
            if (currentRow != -1) {
                int currentIdBan = (int) tableModel.getValueAt(currentRow, 0);
                BanDTO currentBanDTO = busBan.getBanById(currentIdBan);
                Object[] options = {"Xác nhận", "Hủy"};
                int choice = JOptionPane.showOptionDialog(null,
                        "Chắc chắn xóa " + currentBanDTO.getTenBan() + " ?",
                        "Xác nhận",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[1]); // Default to "Không đồng ý"
                if (choice == JOptionPane.YES_OPTION) {
                    busBan.updateBanDangDuocDat(currentBanDTO, null);
                    resetCurrentHoaDonAndBanAndTable();
                }
            }
        });
        return button;
    }

    public void resetCurrentHoaDonAndBanAndTable() {
        // loai bo dto ban,dto hoa don trong panel chứa thông tin hóa đơn
        datBanPN.resetThongTinHoaDon();
        datBanPN.renderThongTinBan();
        stopCellEditing();
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
