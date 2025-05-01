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
    private JTextField txtPhieuNhapId;
    private JTextArea txtGhiChu;
    private List<ChiTietPhieuNhapDTO> chiTietList;
    
    private JComboBox<String> cbSearchColumn;
    private JComboBox<String> cbSearchOperator;
    private JTextField txtSearchValue;
    private JButton btnSearch;
    private JButton btnAdvancedSearch;
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
        panel.setBorder(BorderFactory.createTitledBorder("Tìm kiếm phiếu nhập"));
        panel.setPreferredSize(new Dimension(600, 180));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Trường dữ liệu:"), gbc);
        
        gbc.gridx = 1;
        cbSearchColumn = new JComboBox<>(new String[]{"Mã PN", "Ngày nhập", "Nhà cung cấp", "Người nhập", "Tổng tiền"});
        cbSearchColumn.setPreferredSize(new Dimension(200, 25));
        panel.add(cbSearchColumn, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Toán tử:"), gbc);
        
        gbc.gridx = 1;
        cbSearchOperator = new JComboBox<>(new String[]{"=", ">", ">=", "<", "<=", "<>", "LIKE"});
        cbSearchOperator.setPreferredSize(new Dimension(200, 25));
        panel.add(cbSearchOperator, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Giá trị:"), gbc);
        
        gbc.gridx = 1;
        txtSearchValue = new JTextField();
        txtSearchValue.setPreferredSize(new Dimension(200, 25));
        panel.add(txtSearchValue, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        
        btnSearch = new JButton("Tìm kiếm");
        btnSearch.addActionListener(e -> searchPhieuNhap());
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
        
        // Add ID field
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

    private void searchPhieuNhap() {
        String column = (String) cbSearchColumn.getSelectedItem();
        String operator = (String) cbSearchOperator.getSelectedItem();
        String value = txtSearchValue.getText().trim();
        
        if (value.isEmpty()) {
            loadDataToTable();
            return;
        }
        
        String fieldName = "";
        switch (column) {
            case "Mã PN": fieldName = "pn_id"; break;
            case "Ngày nhập": fieldName = "ngay_nhap"; break;
            case "Nhà cung cấp": fieldName = "ncc_id"; break;
            case "Người nhập": fieldName = "nguoi_nhap_id"; break;
            case "Tổng tiền": fieldName = "tong_tien"; break;
        }
        
        String condition = fieldName + " " + operator + " ?";
        List<PhieuNhapDTO> searchResults = phieuNhapBUS.searchPhieuNhap(condition, new String[]{value});
        
        tableModel.setRowCount(0);
        if (searchResults != null) {
            for (PhieuNhapDTO pn : searchResults) {
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

    private void showAdvancedSearchDialog() {
        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Tìm kiếm nâng cao", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(500, 300);
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Trường 1:"), gbc);
        
        gbc.gridx = 1;
        JComboBox<String> cbField1 = new JComboBox<>(new String[]{"Mã PN", "Ngày nhập", "Nhà cung cấp", "Người nhập", "Tổng tiền"});
        panel.add(cbField1, gbc);
        
        gbc.gridx = 2;
        JComboBox<String> cbOperator1 = new JComboBox<>(new String[]{"=", ">", ">=", "<", "<=", "<>", "LIKE"});
        panel.add(cbOperator1, gbc);
        
        gbc.gridx = 3;
        JTextField txtValue1 = new JTextField(10);
        panel.add(txtValue1, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Toán tử logic:"), gbc);
        
        gbc.gridx = 1;
        JComboBox<String> cbLogicOp = new JComboBox<>(new String[]{"AND", "OR", "NOT"});
        panel.add(cbLogicOp, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Trường 2:"), gbc);
        
        gbc.gridx = 1;
        JComboBox<String> cbField2 = new JComboBox<>(new String[]{"Mã PN", "Ngày nhập", "Nhà cung cấp", "Người nhập", "Tổng tiền"});
        panel.add(cbField2, gbc);
        
        gbc.gridx = 2;
        JComboBox<String> cbOperator2 = new JComboBox<>(new String[]{"=", ">", ">=", "<", "<=", "<>", "LIKE"});
        panel.add(cbOperator2, gbc);
        
        gbc.gridx = 3;
        JTextField txtValue2 = new JTextField(10);
        panel.add(txtValue2, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.CENTER;
        JButton btnSearch = new JButton("Tìm kiếm");
        btnSearch.addActionListener(e -> {
            String fieldName1 = getFieldName((String) cbField1.getSelectedItem());
            String operator1 = (String) cbOperator1.getSelectedItem();
            String value1 = txtValue1.getText().trim();
            
            String logicOp = (String) cbLogicOp.getSelectedItem();
            
            String fieldName2 = getFieldName((String) cbField2.getSelectedItem());
            String operator2 = (String) cbOperator2.getSelectedItem();
            String value2 = txtValue2.getText().trim();
            
            String condition = fieldName1 + " " + operator1 + " ? " + logicOp + " " + 
                             fieldName2 + " " + operator2 + " ?";
            
            List<PhieuNhapDTO> searchResults = phieuNhapBUS.searchPhieuNhap(condition, new String[]{value1, value2});
            
            tableModel.setRowCount(0);
            if (searchResults != null) {
                for (PhieuNhapDTO pn : searchResults) {
                    tableModel.addRow(new Object[]{
                        pn.getPnId(),
                        pn.getNgayNhap(),
                        getNhaCungCapName(pn.getNccId()),
                        getNhanVienName(pn.getNguoiNhapId()),
                        pn.getTongTien()
                    });
                }
            }
            dialog.dispose();
        });
        panel.add(btnSearch, gbc);
        
        dialog.add(panel, BorderLayout.CENTER);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private String getFieldName(String displayName) {
        switch (displayName) {
            case "Mã PN": return "pn_id";
            case "Ngày nhập": return "ngay_nhap";
            case "Nhà cung cấp": return "ncc_id";
            case "Người nhập": return "nguoi_nhap_id";
            case "Tổng tiền": return "tong_tien";
            default: return "";
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
            
            NhaCungCapDTO selectedNCC = (NhaCungCapDTO) cbNhaCungCap.getSelectedItem();
            
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

    private void refreshForm() {
        chiTietList.clear();
        chiTietModel.setRowCount(0);
        txtGhiChu.setText("");
        txtPhieuNhapId.setText("");
    }
}