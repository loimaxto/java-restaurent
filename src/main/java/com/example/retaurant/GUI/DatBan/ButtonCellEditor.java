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
            detailButton = createChiTietBtn();
            cancelButton = createCancelButton();
            panel.add(detailButton);
            panel.add(cancelButton);
        } else {
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
                Object tenBan = tableModel.getValueAt(currentRow, 0);
                int currentIdBan = getBanIdTuTenBan(tenBan.toString());
                datBanPN.setTenBanXemChiTietHienTai("Bàn " + currentIdBan);
                BanDTO currentBanDTO = busBan.getBanById(currentIdBan);
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

                Object tenBan = tableModel.getValueAt(currentRow, 0);
                int currentIdBan = getBanIdTuTenBan(tenBan.toString());
                datBanPN.setTenBanXemChiTietHienTai("Bàn " + currentIdBan);
                int newHoaDonId = busHoaDon.addDefaultHoaDon(currentIdBan,datBanPN.getCreatorId());

                // lay id ban nut duoc nhan và cập nhật hóa đơn của bàn
                BanDTO currentBanDTO = busBan.getBanById(currentIdBan);
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

                Object tenBan = tableModel.getValueAt(currentRow, 0);
                int currentIdBan = getBanIdTuTenBan(tenBan.toString());
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
                    // loai bo dto ban,dto hoa don trong panel chứa thông tin hóa đơn
                    datBanPN.resetThongTinHoaDon();
                    datBanPN.renderThongTinBan();
                    stopCellEditing();
                } 
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
