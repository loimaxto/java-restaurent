package com.example.retaurant.GUI.PhieuNhap;

import com.example.retaurant.BUS.PhieuNhapBUS;
import com.example.retaurant.BUS.NguyenLieuBUS;
import com.example.retaurant.DTO.ChiTietPhieuNhapDTO;
import com.example.retaurant.DTO.NguyenLieuDTO;
import com.example.retaurant.DTO.NhaCungCapDTO;
import com.example.retaurant.DTO.PhieuNhapDTO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PhieuNhapGUI extends JPanel {
    private PhieuNhapBUS phieuNhapBUS;
    private NguyenLieuBUS nguyenLieuBUS;
    private DefaultTableModel tableModel;
    private DefaultTableModel chiTietModel;
    private JTable table;
    private JTable chiTietTable;
    private JComboBox<NguyenLieuDTO> cbNguyenLieu;
    private JComboBox<NhaCungCapDTO> cbNhaCungCap;
    private JTextField txtSoLuong;
    private JTextField txtDonGia;
    private JTextArea txtGhiChu;
    private List<ChiTietPhieuNhapDTO> chiTietList;

    public PhieuNhapGUI(Connection connection) {
        this.phieuNhapBUS = new PhieuNhapBUS(connection);
        this.nguyenLieuBUS = new NguyenLieuBUS();
        this.chiTietList = new ArrayList<>();
        initComponents();
        loadDataToTable();
        loadNguyenLieuComboBox();
        loadNhaCungCapComboBox();
    }

    private void initComponents() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Main table for PhieuNhap
        tableModel = new DefaultTableModel(
                new Object[]{"Mã PN", "Ngày nhập", "Nhà cung cấp", "Người nhập", "Tổng tiền"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách phiếu nhập"));

        // Detail panel for ChiTietPhieuNhap
        JPanel detailPanel = new JPanel(new BorderLayout(10, 10));
        chiTietModel = new DefaultTableModel(
                new Object[]{"Tên NL", "Đơn vị", "Số lượng", "Đơn giá", "Thành tiền"}, 0);
        chiTietTable = new JTable(chiTietModel);
        JScrollPane chiTietScroll = new JScrollPane(chiTietTable);
        chiTietScroll.setBorder(BorderFactory.createTitledBorder("Chi tiết phiếu nhập"));
        
        // Input panel for adding new items
        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Thông tin phiếu nhập"));
        
        cbNhaCungCap = new JComboBox<>();
        inputPanel.add(new JLabel("Nhà cung cấp:"));
        inputPanel.add(cbNhaCungCap);
        
        inputPanel.add(new JLabel("Nguyên liệu:"));
        cbNguyenLieu = new JComboBox<>();
        inputPanel.add(cbNguyenLieu);
        
        inputPanel.add(new JLabel("Số lượng:"));
        txtSoLuong = new JTextField();
        inputPanel.add(txtSoLuong);
        
        inputPanel.add(new JLabel("Đơn giá:"));
        txtDonGia = new JTextField();
        inputPanel.add(txtDonGia);
        
        JButton btnAdd = new JButton("Thêm vào phiếu");
        btnAdd.addActionListener(e -> addToPhieuNhap());
        inputPanel.add(btnAdd);
        
        // Ghi chú panel
        JPanel ghiChuPanel = new JPanel(new BorderLayout());
        ghiChuPanel.setBorder(BorderFactory.createTitledBorder("Ghi chú"));
        txtGhiChu = new JTextArea(3, 20);
        ghiChuPanel.add(new JScrollPane(txtGhiChu));
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnCreate = new JButton("Tạo phiếu nhập");
        btnCreate.addActionListener(e -> createPhieuNhap());
        buttonPanel.add(btnCreate);
        
        JButton btnRefresh = new JButton("Làm mới");
        btnRefresh.addActionListener(e -> refreshForm());
        buttonPanel.add(btnRefresh);
        
        // Add row selection listener to show details when a row is selected
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int pnId = (int) tableModel.getValueAt(table.getSelectedRow(), 0);
                showChiTietPhieuNhap(pnId);
            }
        });
        
        // Layout organization
        detailPanel.add(inputPanel, BorderLayout.NORTH);
        detailPanel.add(chiTietScroll, BorderLayout.CENTER);
        detailPanel.add(ghiChuPanel, BorderLayout.SOUTH);
        
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(detailPanel, BorderLayout.EAST);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadDataToTable() {
        tableModel.setRowCount(0);
        List<PhieuNhapDTO> list = phieuNhapBUS.getAllPhieuNhap();
        if (list != null) {
            for (PhieuNhapDTO pn : list) {
                tableModel.addRow(new Object[]{
                    pn.getPnId(),
                    pn.getNgayNhap(),
                    getNhaCungCapName(pn.getNccId()),
                    getNhanVienName(pn.getNguoiNhapId()),
                    pn.getTongTien()
                });
            }
        }
    }

    private String getNhaCungCapName(int nccId) {
        List<NhaCungCapDTO> list = phieuNhapBUS.getAllNhaCungCap();
        if (list != null) {
            for (NhaCungCapDTO ncc : list) {
                if (ncc.getNcc_id() == nccId) {
                    return ncc.getTen_ncc();
                }
            }
        }
        return "Unknown";
    }

    private String getNhanVienName(int nvId) {
        // You would need to implement this method to get employee name from database
        return "NV " + nvId;
    }

    private void loadNguyenLieuComboBox() {
        List<NguyenLieuDTO> list = nguyenLieuBUS.getAllNguyenLieu();
        if (list != null) {
            for (NguyenLieuDTO nl : list) {
                cbNguyenLieu.addItem(nl);
            }
        }
    }

    private void loadNhaCungCapComboBox() {
        List<NhaCungCapDTO> list = phieuNhapBUS.getAllNhaCungCap();
        if (list != null) {
            for (NhaCungCapDTO ncc : list) {
                cbNhaCungCap.addItem(ncc);
            }
        }
    }

    private void addToPhieuNhap() {
        try {
            NguyenLieuDTO selectedNL = (NguyenLieuDTO) cbNguyenLieu.getSelectedItem();
            long soLuong = Long.parseLong(txtSoLuong.getText());
            long donGia = Long.parseLong(txtDonGia.getText());
            
            if (selectedNL == null || soLuong <= 0 || donGia <= 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin hợp lệ");
                return;
            }
            
            ChiTietPhieuNhapDTO chiTiet = new ChiTietPhieuNhapDTO();
            chiTiet.setNlId(selectedNL.getNlId());
            chiTiet.setTenSp(selectedNL.getTenNl());
            chiTiet.setSoLuong(soLuong);
            chiTiet.setDonGiaMoiDonVi(donGia);
            chiTiet.setTongTien(soLuong * donGia);
            
            chiTietList.add(chiTiet);
            updateChiTietTable();
            
            // Clear input fields
            txtSoLuong.setText("");
            txtDonGia.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số lượng và đơn giá phải là số");
        }
    }

    private void updateChiTietTable() {
        chiTietModel.setRowCount(0);
        for (ChiTietPhieuNhapDTO ct : chiTietList) {
            chiTietModel.addRow(new Object[]{
                ct.getTenSp(),
                getDonVi(ct.getNlId()),
                ct.getSoLuong(),
                ct.getDonGiaMoiDonVi(),
                ct.getTongTien()
            });
        }
    }

    private String getDonVi(int nlId) {
        List<NguyenLieuDTO> list = nguyenLieuBUS.getAllNguyenLieu();
        if (list != null) {
            for (NguyenLieuDTO nl : list) {
                if (nl.getNlId() == nlId) {
                    return nl.getDonVi();
                }
            }
        }
        return "";
    }

    private void showChiTietPhieuNhap(int pnId) {
        chiTietModel.setRowCount(0);
        List<ChiTietPhieuNhapDTO> list = phieuNhapBUS.getChiTietByPhieuNhap(pnId);
        if (list != null) {
            for (ChiTietPhieuNhapDTO ct : list) {
                chiTietModel.addRow(new Object[]{
                    ct.getTenSp(),
                    getDonVi(ct.getNlId()),
                    ct.getSoLuong(),
                    ct.getDonGiaMoiDonVi(),
                    ct.getTongTien()
                });
            }
        }
    }

    private void createPhieuNhap() {
        if (chiTietList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng thêm ít nhất một nguyên liệu");
            return;
        }
        
        if (cbNhaCungCap.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp");
            return;
        }
        
        // In a real application, you would get the actual employee ID from login session
        int nguoiNhapId = 2; // Default employee ID
        
        NhaCungCapDTO selectedNCC = (NhaCungCapDTO) cbNhaCungCap.getSelectedItem();
        
        PhieuNhapDTO phieuNhap = new PhieuNhapDTO();
        phieuNhap.setNgayNhap(new Date());
        phieuNhap.setNccId(selectedNCC.getNcc_id());
        phieuNhap.setNguoiNhapId(nguoiNhapId);
        
        int pnId = phieuNhapBUS.createPhieuNhap(phieuNhap, chiTietList);
        if (pnId > 0) {
            JOptionPane.showMessageDialog(this, "Tạo phiếu nhập thành công với mã: " + pnId);
            refreshForm();
            loadDataToTable();
        } else {
            JOptionPane.showMessageDialog(this, "Tạo phiếu nhập thất bại");
        }
    }

    private void refreshForm() {
        chiTietList.clear();
        chiTietModel.setRowCount(0);
        txtGhiChu.setText("");
    }
}
