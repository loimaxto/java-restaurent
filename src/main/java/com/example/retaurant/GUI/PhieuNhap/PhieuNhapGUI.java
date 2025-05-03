package com.example.retaurant.GUI.PhieuNhap;

import com.example.retaurant.BUS.*;
import com.example.retaurant.DTO.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class PhieuNhapGUI extends JPanel {
    private PhieuNhapBUS phieuNhapBUS;
    private NguyenLieuBUS nguyenLieuBUS;
    
    private DefaultTableModel tableModel;
    private DefaultTableModel chiTietModel;
    
    private JTable table;
    private JTable chiTietTable;
    private JComboBox<String> cbNguyenLieu;
    private JComboBox<String> cbNhaCungCap;
    private JTextField txtSoLuong;
    private JTextField txtDonGia;
    private JTextField txtPhieuNhapId;
    private JTextArea txtGhiChu;
    private List<ChiTietPhieuNhapDTO> chiTietList;
    
    // Search components
    private JTextField txtSearchMaPN;
    private JComboBox<String> cbSearchOpMaPN;
    private JTextField txtSearchNhaCungCap;
    private JComboBox<String> cbSearchOpNhaCungCap;
    private JTextField txtSearchNguoiNhap;
    private JComboBox<String> cbSearchOpNguoiNhap;
    private JTextField txtSearchTongTien;
    private JComboBox<String> cbSearchOpTongTien;
    private JComboBox<String> cbSearchLogicOp;
    private JButton btnSearch;
    private JButton btnClearSearch;

    public PhieuNhapGUI() {
        this.phieuNhapBUS = new PhieuNhapBUS();
        this.nguyenLieuBUS = new NguyenLieuBUS();
        this.chiTietList = new ArrayList<>();
        initComponents();
        loadDataToTable();
        loadNguyenLieuComboBox();
        loadNhaCungCapComboBox();
    }

    private void initComponents() {
        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        mainSplitPane.setDividerLocation(600);
        mainSplitPane.setResizeWeight(0.6);
        mainSplitPane.setOneTouchExpandable(true);

        JPanel leftPanel = new JPanel(new BorderLayout(10, 10));
        JPanel searchPanel = createSearchPanel();
        leftPanel.add(searchPanel, BorderLayout.NORTH);
        
        JScrollPane tableScrollPane = createMainTable();
        leftPanel.add(tableScrollPane, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new BorderLayout(10, 10));
        rightPanel.add(createDetailPanel(), BorderLayout.CENTER);

        mainSplitPane.setLeftComponent(leftPanel);
        mainSplitPane.setRightComponent(rightPanel);

        JPanel bottomPanel = createBottomPanel();
        
        this.add(mainSplitPane, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Tìm kiếm nâng cao"));
        panel.setPreferredSize(new Dimension(600, 250)); // Reduced height
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Row 1: Mã PN
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Mã PN:"), gbc);
        
        gbc.gridx = 1;
        txtSearchMaPN = new JTextField(10);
        panel.add(txtSearchMaPN, gbc);
        
        gbc.gridx = 2;
        cbSearchOpMaPN = new JComboBox<>(new String[]{"=", ">", ">=", "<", "<=", "<>"});
        panel.add(cbSearchOpMaPN, gbc);
        
        // Row 2: Nhà cung cấp
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Nhà cung cấp:"), gbc);
        
        gbc.gridx = 1;
        txtSearchNhaCungCap = new JTextField(10);
        panel.add(txtSearchNhaCungCap, gbc);
        
        gbc.gridx = 2;
        cbSearchOpNhaCungCap = new JComboBox<>(new String[]{"=", "LIKE"});
        panel.add(cbSearchOpNhaCungCap, gbc);
        
        // Row 3: Người nhập
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Người nhập:"), gbc);
        
        gbc.gridx = 1;
        txtSearchNguoiNhap = new JTextField(10);
        panel.add(txtSearchNguoiNhap, gbc);
        
        gbc.gridx = 2;
        cbSearchOpNguoiNhap = new JComboBox<>(new String[]{"=", "LIKE"});
        panel.add(cbSearchOpNguoiNhap, gbc);
        
        // Row 4: Tổng tiền
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Tổng tiền:"), gbc);
        
        gbc.gridx = 1;
        txtSearchTongTien = new JTextField(10);
        panel.add(txtSearchTongTien, gbc);
        
        gbc.gridx = 2;
        cbSearchOpTongTien = new JComboBox<>(new String[]{"=", ">", ">=", "<", "<=", "<>"});
        panel.add(cbSearchOpTongTien, gbc);
        
        // Row 5: Logical operator and buttons
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("Toán tử logic:"), gbc);
        
        gbc.gridx = 1;
        cbSearchLogicOp = new JComboBox<>(new String[]{"AND", "OR"});
        panel.add(cbSearchLogicOp, gbc);
        
        gbc.gridx = 2;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        
        btnSearch = new JButton("Tìm kiếm");
        btnSearch.addActionListener(e -> performAdvancedSearch());
        buttonPanel.add(btnSearch);
        
        btnClearSearch = new JButton("Xóa tìm kiếm");
        btnClearSearch.addActionListener(e -> clearSearchFields());
        buttonPanel.add(btnClearSearch);
        
        panel.add(buttonPanel, gbc);
        
        return panel;
    }

    private JScrollPane createMainTable() {
        tableModel = new DefaultTableModel(
            new Object[]{"Mã PN", "Ngày nhập", "Nhà cung cấp", "Người nhập", "Tổng tiền"}, 0);
        table = new JTable(tableModel);
        
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int pnId = (int) tableModel.getValueAt(table.getSelectedRow(), 0);
                showChiTietPhieuNhap(pnId);
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(600, 300));
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách phiếu nhập"));
        return scrollPane;
    }

    private JPanel createDetailPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Thông tin phiếu nhập"));
        
        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        inputPanel.add(new JLabel("Mã Phiếu Nhập:"));
        txtPhieuNhapId = new JTextField();
        inputPanel.add(txtPhieuNhapId);
        
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
        
        panel.add(inputPanel, BorderLayout.NORTH);
        
        chiTietModel = new DefaultTableModel(
            new Object[]{"Tên NL", "Đơn vị", "Số lượng", "Đơn giá", "Thành tiền"}, 0);
        chiTietTable = new JTable(chiTietModel);
        JScrollPane chiTietScroll = new JScrollPane(chiTietTable);
        chiTietScroll.setPreferredSize(new Dimension(280, 200));
        panel.add(chiTietScroll, BorderLayout.CENTER);
        
        JPanel ghiChuPanel = new JPanel(new BorderLayout());
        ghiChuPanel.setBorder(BorderFactory.createTitledBorder("Ghi chú"));
        txtGhiChu = new JTextArea(3, 20);
        ghiChuPanel.add(new JScrollPane(txtGhiChu));
        panel.add(ghiChuPanel, BorderLayout.SOUTH);
        
        return panel;
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton btnCreate = new JButton("Tạo phiếu nhập");
        btnCreate.addActionListener(e -> createPhieuNhap());
        panel.add(btnCreate);
        
        JButton btnRefresh = new JButton("Làm mới");
        btnRefresh.addActionListener(e -> refreshForm());
        panel.add(btnRefresh);
        
        return panel;
    }

    private void performAdvancedSearch() {
    Map<String, String> filters = new HashMap<>();
    
    if (!txtSearchMaPN.getText().trim().isEmpty()) {
        filters.put("pn_id", cbSearchOpMaPN.getSelectedItem() + " " + txtSearchMaPN.getText().trim());
    }
    
    if (!txtSearchNhaCungCap.getText().trim().isEmpty()) {
        // Always use LIKE for supplier name search
        filters.put("ncc_id", "LIKE " + txtSearchNhaCungCap.getText().trim());
    }
    
    if (!txtSearchNguoiNhap.getText().trim().isEmpty()) {
        filters.put("nguoi_nhap_id", cbSearchOpNguoiNhap.getSelectedItem() + " " + txtSearchNguoiNhap.getText().trim());
    }
    
    if (!txtSearchTongTien.getText().trim().isEmpty()) {
        filters.put("tong_tien", cbSearchOpTongTien.getSelectedItem() + " " + txtSearchTongTien.getText().trim());
    }
    
    if (!filters.isEmpty()) {
        filters.put("logic", (String) cbSearchLogicOp.getSelectedItem());
    }
    
    try {
        List<PhieuNhapDTO> searchResults = phieuNhapBUS.advancedSearch(filters);
        updateTableWithResults(searchResults);
        
        if (searchResults.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy phiếu nhập nào phù hợp");
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm: " + e.getMessage());
        e.printStackTrace();
    }
}

    private void clearSearchFields() {
        txtSearchMaPN.setText("");
        txtSearchNhaCungCap.setText("");
        txtSearchNguoiNhap.setText("");
        txtSearchTongTien.setText("");
        loadDataToTable();
    }

    private int getNccIdFromName(String name) {
        List<NhaCungCapDTO> list = phieuNhapBUS.getAllNhaCungCap();
        for (NhaCungCapDTO ncc : list) {
            if (ncc.getTen_ncc().equalsIgnoreCase(name)) {
                return ncc.getNcc_id();
            }
        }
        return -1;
    }

    private void updateTableWithResults(List<PhieuNhapDTO> results) {
        tableModel.setRowCount(0);
        if (results != null) {
            for (PhieuNhapDTO pn : results) {
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
        return "NV " + nvId;
    }

    private void loadNguyenLieuComboBox() {
        List<NguyenLieuDTO> list = nguyenLieuBUS.getAllNguyenLieu();
        if (list != null) {
            for (NguyenLieuDTO nl : list) {
                cbNguyenLieu.addItem(nl.getTenNl());
            }
        }
    }

    private void loadNhaCungCapComboBox() {
        List<NhaCungCapDTO> list = phieuNhapBUS.getAllNhaCungCap();
        if (list != null) {
            for (NhaCungCapDTO ncc : list) {
                cbNhaCungCap.addItem(ncc.getTen_ncc());
            }
        }
    }

    private void addToPhieuNhap() {
        try {
            String selectedNLName = (String) cbNguyenLieu.getSelectedItem();
            long soLuong = Long.parseLong(txtSoLuong.getText());
            long donGia = Long.parseLong(txtDonGia.getText());
            
            if (selectedNLName == null || soLuong <= 0 || donGia <= 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin hợp lệ");
                return;
            }
            
            // Get the full NguyenLieuDTO object from the name
            NguyenLieuDTO selectedNL = getNguyenLieuByName(selectedNLName);
            if (selectedNL == null) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy nguyên liệu");
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
            
            txtSoLuong.setText("");
            txtDonGia.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số lượng và đơn giá phải là số");
        }
    }

    private NguyenLieuDTO getNguyenLieuByName(String name) {
        List<NguyenLieuDTO> list = nguyenLieuBUS.getAllNguyenLieu();
        if (list != null) {
            for (NguyenLieuDTO nl : list) {
                if (nl.getTenNl().equals(name)) {
                    return nl;
                }
            }
        }
        return null;
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
        
        String selectedNCCName = (String) cbNhaCungCap.getSelectedItem();
        if (selectedNCCName == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp");
            return;
        }
        
        try {
            int pnId = Integer.parseInt(txtPhieuNhapId.getText().trim());
            if (pnId <= 0) {
                JOptionPane.showMessageDialog(this, "ID phải là số dương");
                return;
            }
            
            if (phieuNhapBUS.isIdExists(pnId)) {
                JOptionPane.showMessageDialog(this, "ID đã tồn tại, vui lòng chọn ID khác");
                return;
            }
            
            int nguoiNhapId = 2; // Default employee ID
            
            // Get the full NhaCungCapDTO object from the name
            NhaCungCapDTO selectedNCC = getNhaCungCapByName(selectedNCCName);
            if (selectedNCC == null) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy nhà cung cấp");
                return;
            }
            
            PhieuNhapDTO phieuNhap = new PhieuNhapDTO();
            phieuNhap.setPnId(pnId);
            phieuNhap.setNgayNhap(new Date());
            phieuNhap.setNccId(selectedNCC.getNcc_id());
            phieuNhap.setNguoiNhapId(nguoiNhapId);
            
            int createdId = phieuNhapBUS.createPhieuNhap(phieuNhap, chiTietList);
            if (createdId > 0) {
                for (ChiTietPhieuNhapDTO ct : chiTietList) {
                    nguyenLieuBUS.updateNguyenLieuQuantityNhap(ct.getNlId(), ct.getSoLuong());
                }
                
                JOptionPane.showMessageDialog(this, "Tạo phiếu nhập thành công với mã: " + createdId);
                refreshForm();
                loadDataToTable();
            } else {
                JOptionPane.showMessageDialog(this, "Tạo phiếu nhập thất bại");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID phải là số nguyên");
        }
    }

    private NhaCungCapDTO getNhaCungCapByName(String name) {
        List<NhaCungCapDTO> list = phieuNhapBUS.getAllNhaCungCap();
        if (list != null) {
            for (NhaCungCapDTO ncc : list) {
                if (ncc.getTen_ncc().equals(name)) {
                    return ncc;
                }
            }
        }
        return null;
    }

    private void refreshForm() {
        chiTietList.clear();
        chiTietModel.setRowCount(0);
        txtGhiChu.setText("");
        txtPhieuNhapId.setText("");
    }
}