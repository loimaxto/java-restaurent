package com.example.retaurant.GUI.NhaCungCap;

import com.example.retaurant.BUS.NhaCungCapBUS;
import com.example.retaurant.DTO.NhaCungCapDTO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.List;

public class NhaCungCapPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtTenNCC, txtSDT, txtDiaChi, txtTimKiem;
    private JButton btnThem, btnSua, btnXoa, btnTimKiem, btnLamMoi, btnTimKiemNangCao;
    private NhaCungCapBUS nhaCungCapBUS;
    private int selectedNCCId = -1;
    private Connection connection;

    public NhaCungCapPanel() {
        initializeDatabaseConnection();
        initComponents();
        loadDataToTable();
    }

    private void initializeDatabaseConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/java";
            String username = "root";
            String password = "";
            connection = DriverManager.getConnection(url, username, password);
            this.nhaCungCapBUS = new NhaCungCapBUS(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Không thể kết nối đến cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Thông tin Nhà Cung Cấp"));
        
        inputPanel.add(new JLabel("Tên Nhà Cung Cấp:"));
        txtTenNCC = new JTextField();
        inputPanel.add(txtTenNCC);
        
        inputPanel.add(new JLabel("Số Điện Thoại:"));
        txtSDT = new JTextField();
        inputPanel.add(txtSDT);
        
        inputPanel.add(new JLabel("Địa Chỉ:"));
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
                txtTenNCC.setText(tableModel.getValueAt(selectedRow, 1).toString());
                txtSDT.setText(tableModel.getValueAt(selectedRow, 2).toString());
                txtDiaChi.setText(tableModel.getValueAt(selectedRow, 3).toString());
            }
        });
    }

    private void handleAddSupplier() {
        try {
            NhaCungCapDTO ncc = new NhaCungCapDTO();
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
        JDialog searchDialog = new JDialog();
        searchDialog.setTitle("Tìm kiếm nâng cao");
        searchDialog.setSize(400, 300);
        searchDialog.setLayout(new GridLayout(5, 2, 10, 10));
        
        // ID components
        JLabel lblId = new JLabel("ID:");
        JTextField txtId = new JTextField();
        JComboBox<String> cbIdOperator = new JComboBox<>(new String[]{"=", "<>", ">", ">=", "<", "<="});
        
        // Name components
        JLabel lblName = new JLabel("Tên NCC:");
        JTextField txtName = new JTextField();
        JComboBox<String> cbNameOperator = new JComboBox<>(new String[]{"LIKE", "NOT LIKE", "=", "<>"});
        
        // Phone components
        JLabel lblPhone = new JLabel("Số điện thoại:");
        JTextField txtPhone = new JTextField();
        JComboBox<String> cbPhoneOperator = new JComboBox<>(new String[]{"LIKE", "NOT LIKE", "=", "<>"});
        
        // Address components
        JLabel lblAddress = new JLabel("Địa chỉ:");
        JTextField txtAddress = new JTextField();
        JComboBox<String> cbAddressOperator = new JComboBox<>(new String[]{"LIKE", "NOT LIKE", "=", "<>"});
        
        // Buttons
        JButton btnSearch = new JButton("Tìm kiếm");
        JButton btnCancel = new JButton("Hủy");
        
        // Add components to dialog
        searchDialog.add(lblId);
        searchDialog.add(txtId);
        searchDialog.add(cbIdOperator);
        searchDialog.add(new JLabel()); // empty cell
        
        searchDialog.add(lblName);
        searchDialog.add(txtName);
        searchDialog.add(cbNameOperator);
        searchDialog.add(new JLabel());
        
        searchDialog.add(lblPhone);
        searchDialog.add(txtPhone);
        searchDialog.add(cbPhoneOperator);
        searchDialog.add(new JLabel());
        
        searchDialog.add(lblAddress);
        searchDialog.add(txtAddress);
        searchDialog.add(cbAddressOperator);
        searchDialog.add(new JLabel());
        
        searchDialog.add(btnSearch);
        searchDialog.add(btnCancel);
        
        // Search button action
        btnSearch.addActionListener(e -> {
            try {
                Integer id = txtId.getText().isEmpty() ? null : Integer.parseInt(txtId.getText());
                String name = txtName.getText().isEmpty() ? null : txtName.getText();
                String phone = txtPhone.getText().isEmpty() ? null : txtPhone.getText();
                String address = txtAddress.getText().isEmpty() ? null : txtAddress.getText();
                
                List<NhaCungCapDTO> results = nhaCungCapBUS.advancedSearch(
                    id, cbIdOperator.getSelectedItem().toString(),
                    name, cbNameOperator.getSelectedItem().toString(),
                    phone, cbPhoneOperator.getSelectedItem().toString(),
                    address, cbAddressOperator.getSelectedItem().toString()
                );
                
                updateTable(results);
                searchDialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(searchDialog, "ID phải là số nguyên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(searchDialog, "Lỗi kết nối cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        // Cancel button action
        btnCancel.addActionListener(e -> searchDialog.dispose());
        
        searchDialog.setModal(true);
        searchDialog.setLocationRelativeTo(this);
        searchDialog.setVisible(true);
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
        txtTenNCC.setText("");
        txtSDT.setText("");
        txtDiaChi.setText("");
        txtTimKiem.setText("");
    }
}