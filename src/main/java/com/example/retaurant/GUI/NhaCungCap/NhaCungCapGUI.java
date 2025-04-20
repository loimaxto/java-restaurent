package com.example.retaurant.GUI.NhaCungCap;

import com.example.retaurant.BUS.NhaCungCapBUS;
import com.example.retaurant.DTO.NhaCungCapDTO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class NhaCungCapGUI extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtTenNCC, txtSDT, txtDiaChi, txtTimKiem;
    private JButton btnThem, btnSua, btnXoa, btnTimKiem, btnLamMoi;
    private NhaCungCapBUS nhaCungCapBUS;
    private int selectedNCCId = -1;
    private Connection connection;

    public NhaCungCapGUI() {
        // Initialize database connection
        initializeDatabaseConnection();
        
        setTitle("Quản lý Nhà Cung Cấp");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        initComponents();
        loadDataToTable();
    }

    private void initializeDatabaseConnection() {
        try {
            // Database connection parameters
            String url = "jdbc:mysql://localhost:3306/java";
            String username = "root"; // Change to your username
            String password = ""; // Change to your password
            
            // Establish connection
            connection = DriverManager.getConnection(url, username, password);
            this.nhaCungCapBUS = new NhaCungCapBUS(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Không thể kết nối đến cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Input panel
        
        
        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.add(new JLabel("Tìm kiếm:"));
        txtTimKiem = new JTextField(20);
        searchPanel.add(txtTimKiem);
        btnTimKiem = new JButton("Tìm");
        searchPanel.add(btnTimKiem);
        
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
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(searchPanel, BorderLayout.SOUTH);
        
        add(mainPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        // Add event listeners
        addEventListeners();
    }

    private void addEventListeners() {
        // Add button
        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    NhaCungCapDTO ncc = new NhaCungCapDTO();
                    ncc.setTen_ncc(txtTenNCC.getText());
                    ncc.setSdt(txtSDT.getText());
                    ncc.setDchi(txtDiaChi.getText());
                    
                    String error = nhaCungCapBUS.validateNhaCungCap(ncc);
                    if (error != null) {
                        JOptionPane.showMessageDialog(NhaCungCapGUI.this, error, "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    if (nhaCungCapBUS.addNhaCungCap(ncc)) {
                        JOptionPane.showMessageDialog(NhaCungCapGUI.this, "Thêm nhà cung cấp thành công!");
                        loadDataToTable();
                        clearFields();
                    } else {
                        JOptionPane.showMessageDialog(NhaCungCapGUI.this, "Thêm nhà cung cấp thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(NhaCungCapGUI.this, "Lỗi kết nối cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        // Edit button
        btnSua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedNCCId == -1) {
                    JOptionPane.showMessageDialog(NhaCungCapGUI.this, "Vui lòng chọn nhà cung cấp cần sửa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
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
                        JOptionPane.showMessageDialog(NhaCungCapGUI.this, error, "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    if (nhaCungCapBUS.updateNhaCungCap(ncc)) {
                        JOptionPane.showMessageDialog(NhaCungCapGUI.this, "Cập nhật nhà cung cấp thành công!");
                        loadDataToTable();
                        clearFields();
                        selectedNCCId = -1;
                    } else {
                        JOptionPane.showMessageDialog(NhaCungCapGUI.this, "Cập nhật nhà cung cấp thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(NhaCungCapGUI.this, "Lỗi kết nối cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        // Delete button
        btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedNCCId == -1) {
                    JOptionPane.showMessageDialog(NhaCungCapGUI.this, "Vui lòng chọn nhà cung cấp cần xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                int confirm = JOptionPane.showConfirmDialog(
                    NhaCungCapGUI.this, 
                    "Bạn có chắc chắn muốn xóa nhà cung cấp này?", 
                    "Xác nhận xóa", 
                    JOptionPane.YES_NO_OPTION
                );
                
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        if (nhaCungCapBUS.deleteNhaCungCap(selectedNCCId)) {
                            JOptionPane.showMessageDialog(NhaCungCapGUI.this, "Xóa nhà cung cấp thành công!");
                            loadDataToTable();
                            clearFields();
                            selectedNCCId = -1;
                        } else {
                            JOptionPane.showMessageDialog(NhaCungCapGUI.this, "Xóa nhà cung cấp thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(NhaCungCapGUI.this, "Lỗi kết nối cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        
        // Search button
        btnTimKiem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyword = txtTimKiem.getText().trim();
                if (keyword.isEmpty()) {
                    loadDataToTable();
                    return;
                }
                
                try {
                    List<NhaCungCapDTO> searchResult = nhaCungCapBUS.searchNhaCungCapByName(keyword);
                    updateTable(searchResult);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(NhaCungCapGUI.this, "Lỗi kết nối cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        // Refresh button
        btnLamMoi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
                loadDataToTable();
                selectedNCCId = -1;
            }
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

    public static void main(String[] args) {
        // Run the GUI
        SwingUtilities.invokeLater(() -> {
            NhaCungCapGUI gui = new NhaCungCapGUI();
            gui.setVisible(true);
        });
    }
}