package com.example.retaurant.GUI.PhieuXuat;

import com.example.retaurant.BUS.PhieuXuatBUS;
import com.example.retaurant.BUS.NguyenLieuBUS;
import com.example.retaurant.DTO.CTPhieuXuatDTO;
import com.example.retaurant.DTO.NguyenLieuDTO;
import com.example.retaurant.DTO.PhieuXuatDTO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class PhieuXuatGUI extends JPanel {
    private PhieuXuatBUS phieuXuatBUS;
    private NguyenLieuBUS nguyenLieuBUS;
    
    private DefaultTableModel tableModel;
    private DefaultTableModel chiTietModel;
    
    private JTable table;
    private JTable chiTietTable;
    private JComboBox<NguyenLieuDTO> cbNguyenLieu;
    private JTextField txtSoLuong;
    private JTextArea txtGhiChu;
    private List<CTPhieuXuatDTO> chiTietList;
    
    // Search components
    private JComboBox<String> cbSearchColumn;
    private JComboBox<String> cbSearchOperator;
    private JTextField txtSearchValue;
    private JButton btnSearch;
    private JButton btnAdvancedSearch;
    private JButton btnClearSearch;

    public PhieuXuatGUI(Connection connection) {
        this.phieuXuatBUS = new PhieuXuatBUS(connection);
        this.nguyenLieuBUS = new NguyenLieuBUS();
        this.chiTietList = new ArrayList<>();
        initComponents();
        loadDataToTable();
        loadNguyenLieuComboBox();
    }

    private void initComponents() {
        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        mainSplitPane.setDividerLocation(600);
        mainSplitPane.setResizeWeight(0.6);

        // LEFT PANEL - Search and Main Table
        JPanel leftPanel = new JPanel(new BorderLayout(10, 10));
        
        // Search Panel
        JPanel searchPanel = createSearchPanel();
        leftPanel.add(searchPanel, BorderLayout.NORTH);
        
        // Main Table
        JScrollPane tableScrollPane = createMainTable();
        leftPanel.add(tableScrollPane, BorderLayout.CENTER);

        // RIGHT PANEL - Detail Information
        JPanel rightPanel = new JPanel(new BorderLayout(10, 10));
        rightPanel.add(createDetailPanel(), BorderLayout.CENTER);

        mainSplitPane.setLeftComponent(leftPanel);
        mainSplitPane.setRightComponent(rightPanel);

        // Bottom buttons panel
        JPanel bottomPanel = createBottomPanel();
        
        this.add(mainSplitPane, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Tìm kiếm phiếu xuất"));
        panel.setPreferredSize(new Dimension(600, 150));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Search field selection
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Trường dữ liệu:"), gbc);
        
        gbc.gridx = 1;
        cbSearchColumn = new JComboBox<>(new String[]{"Mã PX", "Ngày xuất", "Người xuất"});
        cbSearchColumn.setPreferredSize(new Dimension(150, 25));
        panel.add(cbSearchColumn, gbc);
        
        // Operator selection
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Toán tử:"), gbc);
        
        gbc.gridx = 1;
        cbSearchOperator = new JComboBox<>(new String[]{"=", ">", ">=", "<", "<=", "<>", "LIKE"});
        cbSearchOperator.setPreferredSize(new Dimension(150, 25));
        panel.add(cbSearchOperator, gbc);
        
        // Value input
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Giá trị:"), gbc);
        
        gbc.gridx = 1;
        txtSearchValue = new JTextField();
        txtSearchValue.setPreferredSize(new Dimension(150, 25));
        panel.add(txtSearchValue, gbc);
        
        // Search buttons
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        
        btnSearch = new JButton("Tìm kiếm");
        btnSearch.addActionListener(e -> searchPhieuXuat());
        buttonPanel.add(btnSearch);
        
        btnAdvancedSearch = new JButton("Tìm kiếm nâng cao");
        btnAdvancedSearch.addActionListener(e -> showAdvancedSearchDialog());
        buttonPanel.add(btnAdvancedSearch);
        
        btnClearSearch = new JButton("Xóa tìm kiếm");
        btnClearSearch.addActionListener(e -> {
            txtSearchValue.setText("");
            loadDataToTable();
        });
        buttonPanel.add(btnClearSearch);
        
        panel.add(buttonPanel, gbc);
        
        return panel;
    }

    private JScrollPane createMainTable() {
        tableModel = new DefaultTableModel(
            new Object[]{"Mã PX", "Ngày xuất", "Người xuất"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int pxId = (int) tableModel.getValueAt(table.getSelectedRow(), 0);
                showChiTietPhieuXuat(pxId);
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(600, 300));
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách phiếu xuất"));
        return scrollPane;
    }

    private JPanel createDetailPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Thông tin phiếu xuất"));
        
        // Input fields panel
        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        inputPanel.add(new JLabel("Nguyên liệu:"));
        cbNguyenLieu = new JComboBox<>();
        inputPanel.add(cbNguyenLieu);
        
        inputPanel.add(new JLabel("Số lượng:"));
        txtSoLuong = new JTextField();
        inputPanel.add(txtSoLuong);
        
        JButton btnAdd = new JButton("Thêm vào phiếu");
        btnAdd.addActionListener(e -> addToPhieuXuat());
        inputPanel.add(btnAdd);
        
        panel.add(inputPanel, BorderLayout.NORTH);
        
        // Detail table
        chiTietModel = new DefaultTableModel(
            new Object[]{"Tên NL", "Đơn vị", "Số lượng"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        chiTietTable = new JTable(chiTietModel);
        JScrollPane chiTietScroll = new JScrollPane(chiTietTable);
        chiTietScroll.setPreferredSize(new Dimension(280, 200));
        panel.add(chiTietScroll, BorderLayout.CENTER);
        
        // Notes panel
        JPanel ghiChuPanel = new JPanel(new BorderLayout());
        ghiChuPanel.setBorder(BorderFactory.createTitledBorder("Ghi chú"));
        txtGhiChu = new JTextArea(3, 20);
        txtGhiChu.setLineWrap(true);
        ghiChuPanel.add(new JScrollPane(txtGhiChu));
        panel.add(ghiChuPanel, BorderLayout.SOUTH);
        
        return panel;
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton btnCreate = new JButton("Tạo phiếu xuất");
        btnCreate.addActionListener(e -> createPhieuXuat());
        panel.add(btnCreate);
        
        JButton btnRefresh = new JButton("Làm mới");
        btnRefresh.addActionListener(e -> refreshForm());
        panel.add(btnRefresh);
        
        return panel;
    }

    private void searchPhieuXuat() {
        String column = (String) cbSearchColumn.getSelectedItem();
        String operator = (String) cbSearchOperator.getSelectedItem();
        String value = txtSearchValue.getText().trim();
        
        if (value.isEmpty()) {
            loadDataToTable();
            return;
        }
        
        String fieldName = "";
        switch (column) {
            case "Mã PX": fieldName = "px_id"; break;
            case "Ngày xuất": fieldName = "ngay_xuat"; break;
            case "Người xuất": fieldName = "nguoi_xuat_id"; break;
        }
        
        String condition = fieldName + " " + operator + " ?";
        List<PhieuXuatDTO> searchResults = phieuXuatBUS.searchPhieuXuat(condition, new String[]{value});
        
        updateTableWithResults(searchResults);
    }

    private void showAdvancedSearchDialog() {
        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Tìm kiếm nâng cao", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(500, 300);
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Field 1
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Trường 1:"), gbc);
        
        gbc.gridx = 1;
        JComboBox<String> cbField1 = new JComboBox<>(new String[]{"Mã PX", "Ngày xuất", "Người xuất"});
        panel.add(cbField1, gbc);
        
        gbc.gridx = 2;
        JComboBox<String> cbOperator1 = new JComboBox<>(new String[]{"=", ">", ">=", "<", "<=", "<>", "LIKE"});
        panel.add(cbOperator1, gbc);
        
        gbc.gridx = 3;
        JTextField txtValue1 = new JTextField(10);
        panel.add(txtValue1, gbc);
        
        // Logical operator
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Toán tử logic:"), gbc);
        
        gbc.gridx = 1;
        JComboBox<String> cbLogicOp = new JComboBox<>(new String[]{"AND", "OR", "NOT"});
        panel.add(cbLogicOp, gbc);
        
        // Field 2
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Trường 2:"), gbc);
        
        gbc.gridx = 1;
        JComboBox<String> cbField2 = new JComboBox<>(new String[]{"Mã PX", "Ngày xuất", "Người xuất"});
        panel.add(cbField2, gbc);
        
        gbc.gridx = 2;
        JComboBox<String> cbOperator2 = new JComboBox<>(new String[]{"=", ">", ">=", "<", "<=", "<>", "LIKE"});
        panel.add(cbOperator2, gbc);
        
        gbc.gridx = 3;
        JTextField txtValue2 = new JTextField(10);
        panel.add(txtValue2, gbc);
        
        // Search button
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.CENTER;
        JButton btnSearch = new JButton("Tìm kiếm");
        btnSearch.addActionListener(e -> {
            Map<String, String> filters = new HashMap<>();
            
            // Add first condition
            String field1 = getFieldName((String) cbField1.getSelectedItem());
            String operator1 = (String) cbOperator1.getSelectedItem();
            String value1 = txtValue1.getText().trim();
            if (!value1.isEmpty()) {
                filters.put(field1, operator1 + " " + value1);
            }
            
            // Add logical operator
            String logicOp = (String) cbLogicOp.getSelectedItem();
            filters.put("logic", logicOp);
            
            // Add second condition
            String field2 = getFieldName((String) cbField2.getSelectedItem());
            String operator2 = (String) cbOperator2.getSelectedItem();
            String value2 = txtValue2.getText().trim();
            if (!value2.isEmpty()) {
                filters.put(field2, operator2 + " " + value2);
            }
            
            try {
                List<PhieuXuatDTO> searchResults = phieuXuatBUS.advancedSearch(filters);
                updateTableWithResults(searchResults);
                dialog.dispose();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
        panel.add(btnSearch, gbc);
        
        dialog.add(panel, BorderLayout.CENTER);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void updateTableWithResults(List<PhieuXuatDTO> results) {
        tableModel.setRowCount(0);
        if (results != null) {
            for (PhieuXuatDTO px : results) {
                tableModel.addRow(new Object[]{
                    px.getPxId(),
                    px.getNgayXuat(),
                    getNhanVienName(px.getNguoiXuatId())
                });
            }
        }
    }

    private void loadDataToTable() {
        List<PhieuXuatDTO> list = phieuXuatBUS.getAllPhieuXuat();
        updateTableWithResults(list);
    }

    private String getNhanVienName(int nvId) {
        // In a real application, you would look this up from your database
        return "NV " + nvId;
    }

    private void loadNguyenLieuComboBox() {
        cbNguyenLieu.removeAllItems();
        List<NguyenLieuDTO> list = nguyenLieuBUS.getAllNguyenLieu();
        if (list != null) {
            for (NguyenLieuDTO nl : list) {
                cbNguyenLieu.addItem(nl);
            }
        }
    }

    private void addToPhieuXuat() {
        try {
            NguyenLieuDTO selectedNL = (NguyenLieuDTO) cbNguyenLieu.getSelectedItem();
            float soLuong = Float.parseFloat(txtSoLuong.getText());
            
            if (selectedNL == null || soLuong <= 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Check if enough quantity is available
            if (selectedNL.getSoLuong() < soLuong) {
                JOptionPane.showMessageDialog(this, 
                    "Không đủ nguyên liệu. Số lượng hiện có: " + selectedNL.getSoLuong(), 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            CTPhieuXuatDTO chiTiet = new CTPhieuXuatDTO();
            chiTiet.setNlId(selectedNL.getNlId());
            chiTiet.setTenSp(selectedNL.getTenNl());
            chiTiet.setSoLuong(soLuong);
            chiTiet.setDonVi(selectedNL.getDonVi());
            
            chiTietList.add(chiTiet);
            updateChiTietTable();
            
            txtSoLuong.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số lượng phải là số", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateChiTietTable() {
        chiTietModel.setRowCount(0);
        for (CTPhieuXuatDTO ct : chiTietList) {
            chiTietModel.addRow(new Object[]{
                ct.getTenSp(),
                ct.getDonVi(),
                ct.getSoLuong()
            });
        }
    }

    private void showChiTietPhieuXuat(int pxId) {
        chiTietModel.setRowCount(0);
        List<CTPhieuXuatDTO> list = phieuXuatBUS.getChiTietByPhieuXuat(pxId);
        if (list != null) {
            for (CTPhieuXuatDTO ct : list) {
                chiTietModel.addRow(new Object[]{
                    ct.getTenSp(),
                    ct.getDonVi(),
                    ct.getSoLuong()
                });
            }
        }
    }

    private void createPhieuXuat() {
        if (chiTietList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng thêm ít nhất một nguyên liệu", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int nguoiXuatId = 2; // Default employee ID - in real app, get from session
        
        PhieuXuatDTO phieuXuat = new PhieuXuatDTO();
        phieuXuat.setNgayXuat(new Date());
        phieuXuat.setNguoiXuatId(nguoiXuatId);
        
        int pxId = phieuXuatBUS.createPhieuXuat(phieuXuat, chiTietList);
        if (pxId > 0) {
            // Update inventory
            for (CTPhieuXuatDTO ct : chiTietList) {
                nguyenLieuBUS.updateNguyenLieuQuantityXuat(ct.getNlId(), (long) ct.getSoLuong());
            }
            
            JOptionPane.showMessageDialog(this, "Tạo phiếu xuất thành công với mã: " + pxId, 
                "Thành công", JOptionPane.INFORMATION_MESSAGE);
            refreshForm();
            loadDataToTable();
            loadNguyenLieuComboBox(); // Refresh inventory quantities
        } else {
            JOptionPane.showMessageDialog(this, "Tạo phiếu xuất thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshForm() {
        chiTietList.clear();
        chiTietModel.setRowCount(0);
        txtGhiChu.setText("");
        txtSoLuong.setText("");
    }

    private String getFieldName(String displayName) {
        switch (displayName) {
            case "Mã PX": return "px_id";
            case "Ngày xuất": return "ngay_xuat";
            case "Người xuất": return "nguoi_xuat_id";
            default: return "";
        }
    }
}