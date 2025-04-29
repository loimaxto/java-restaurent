package com.example.retaurant.GUI.NhaCungCap;

import com.example.retaurant.BUS.NhaCungCapBUS;
import com.example.retaurant.DTO.NhaCungCapDTO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.List;

public class NhaCungCapPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtId, txtTenNCC, txtSDT, txtDiaChi, txtTimKiem;
    private JButton btnThem, btnSua, btnXoa, btnTimKiem, btnLamMoi, btnTimKiemNangCao;
    private NhaCungCapBUS nhaCungCapBUS;
    private int selectedNCCId = -1;

    public NhaCungCapPanel() {
        this.nhaCungCapBUS = new NhaCungCapBUS();
        initComponents();
        loadDataToTable();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Thông tin Nhà Cung Cấp"));
        
        // ID field - always enabled for manual input
        inputPanel.add(new JLabel("ID*:"));
        txtId = new JTextField();
        inputPanel.add(txtId);
        
        // Other fields
        inputPanel.add(new JLabel("Tên Nhà Cung Cấp*:"));
        txtTenNCC = new JTextField();
        inputPanel.add(txtTenNCC);
        
        inputPanel.add(new JLabel("Số Điện Thoại*:"));
        txtSDT = new JTextField();
        inputPanel.add(txtSDT);
        
        inputPanel.add(new JLabel("Địa Chỉ*:"));
        txtDiaChi = new JTextField();
        inputPanel.add(txtDiaChi);

        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.add(new JLabel("Tìm kiếm:"));
        txtTimKiem = new JTextField(20);
        searchPanel.add(txtTimKiem);
        btnTimKiem = new JButton("Tìm cơ bản");
        btnTimKiemNangCao = new JButton("Tìm nâng cao");
        searchPanel.add(btnTimKiem);
        searchPanel.add(btnTimKiemNangCao);

        // Combine input and search panels
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(inputPanel, BorderLayout.CENTER);
        topPanel.add(searchPanel, BorderLayout.SOUTH);

        // Table
        String[] columnNames = {"ID", "Tên Nhà Cung Cấp", "Số Điện Thoại", "Địa Chỉ"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnLamMoi = new JButton("Làm Mới");
        
        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnLamMoi);

        // Add components to main panel
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add event listeners
        addEventListeners();
    }

    private void addEventListeners() {
        // Add button
        btnThem.addActionListener(e -> handleAddSupplier());
        
        // Edit button
        btnSua.addActionListener(e -> handleUpdateSupplier());
        
        // Delete button
        btnXoa.addActionListener(e -> handleDeleteSupplier());
        
        // Basic search button
        btnTimKiem.addActionListener(e -> handleBasicSearch());
        
        // Advanced search button
        btnTimKiemNangCao.addActionListener(e -> showAdvancedSearchDialog());
        
        // Refresh button
        btnLamMoi.addActionListener(e -> {
            clearFields();
            loadDataToTable();
            selectedNCCId = -1;
        });
        
        // Table selection listener
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int selectedRow = table.getSelectedRow();
                selectedNCCId = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                txtId.setText(tableModel.getValueAt(selectedRow, 0).toString());
                txtTenNCC.setText(tableModel.getValueAt(selectedRow, 1).toString());
                txtSDT.setText(tableModel.getValueAt(selectedRow, 2).toString());
                txtDiaChi.setText(tableModel.getValueAt(selectedRow, 3).toString());
            }
        });

        // Clear selection when clicking on empty table area
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                if (row == -1) {
                    table.clearSelection();
                    clearFields();
                }
            }
        });
    }

    private void handleAddSupplier() {
        try {
            // Validate ID
            if (txtId.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập ID", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int id;
            try {
                id = Integer.parseInt(txtId.getText().trim());
                if (id <= 0) {
                    JOptionPane.showMessageDialog(this, "ID phải là số nguyên dương", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "ID phải là số nguyên", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Check if ID already exists
            if (nhaCungCapBUS.getNhaCungCapById(id) != null) {
                JOptionPane.showMessageDialog(this, "ID đã tồn tại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            NhaCungCapDTO ncc = new NhaCungCapDTO();
            ncc.setNcc_id(id);
            ncc.setTen_ncc(txtTenNCC.getText());
            ncc.setSdt(txtSDT.getText());
            ncc.setDchi(txtDiaChi.getText());
            
            String error = nhaCungCapBUS.validateNhaCungCap(ncc);
            if (error != null) {
                JOptionPane.showMessageDialog(this, error, "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (nhaCungCapBUS.addNhaCungCap(ncc)) {
                JOptionPane.showMessageDialog(this, "Thêm nhà cung cấp thành công!");
                loadDataToTable();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm nhà cung cấp thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi kết nối cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleUpdateSupplier() {
        if (selectedNCCId == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp cần sửa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            NhaCungCapDTO ncc = new NhaCungCapDTO();
            ncc.setNcc_id(selectedNCCId);
            ncc.setTen_ncc(txtTenNCC.getText());
            ncc.setSdt(txtSDT.getText());
            ncc.setDchi(txtDiaChi.getText());
            
            String error = nhaCungCapBUS.validateNhaCungCap(ncc);
            if (error != null) {
                JOptionPane.showMessageDialog(this, error, "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (nhaCungCapBUS.updateNhaCungCap(ncc)) {
                JOptionPane.showMessageDialog(this, "Cập nhật nhà cung cấp thành công!");
                loadDataToTable();
                clearFields();
                selectedNCCId = -1;
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật nhà cung cấp thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi kết nối cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleDeleteSupplier() {
        if (selectedNCCId == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp cần xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(
            this, 
            "Bạn có chắc chắn muốn xóa nhà cung cấp này?", 
            "Xác nhận xóa", 
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                if (nhaCungCapBUS.deleteNhaCungCap(selectedNCCId)) {
                    JOptionPane.showMessageDialog(this, "Xóa nhà cung cấp thành công!");
                    loadDataToTable();
                    clearFields();
                    selectedNCCId = -1;
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa nhà cung cấp thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi kết nối cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void handleBasicSearch() {
        String keyword = txtTimKiem.getText().trim();
        if (keyword.isEmpty()) {
            loadDataToTable();
            return;
        }
        
        try {
            List<NhaCungCapDTO> searchResult = nhaCungCapBUS.searchByAll(keyword);
            updateTable(searchResult);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi kết nối cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showAdvancedSearchDialog() {
        // ... (keep the same advanced search dialog implementation)
    }

    private void loadDataToTable() {
        try {
            List<NhaCungCapDTO> list = nhaCungCapBUS.getAllNhaCungCap();
            updateTable(list);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi kết nối cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTable(List<NhaCungCapDTO> list) {
        tableModel.setRowCount(0);
        for (NhaCungCapDTO ncc : list) {
            Object[] row = {
                ncc.getNcc_id(),
                ncc.getTen_ncc(),
                ncc.getSdt(),
                ncc.getDchi()
            };
            tableModel.addRow(row);
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtTenNCC.setText("");
        txtSDT.setText("");
        txtDiaChi.setText("");
        txtTimKiem.setText("");
        selectedNCCId = -1;
    }
}