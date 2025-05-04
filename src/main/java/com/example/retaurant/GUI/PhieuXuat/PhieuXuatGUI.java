package com.example.retaurant.GUI.PhieuXuat;

import com.example.retaurant.BUS.*;
import com.example.retaurant.DTO.*;
import com.example.retaurant.DTO.NhanVien;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

public class PhieuXuatGUI extends JPanel {
    private PhieuXuatBUS phieuXuatBUS;
    private NguyenLieuBUS nguyenLieuBUS;
    private NhanVienBUS nhanVienBUS;
    
    private DefaultTableModel tableModel;
    private DefaultTableModel chiTietModel;
    
    private JTable table;
    private JTable chiTietTable;
    private JComboBox<NguyenLieuDTO> cbNguyenLieu;
    private JComboBox<String> cbNhanVien;
    private JTextField txtSoLuong;
    private JTextArea txtGhiChu;
    private List<CTPhieuXuatDTO> chiTietList;
    private JTextField txtPhieuXuatId;
    
    // Search components
    private JTextField txtSearchMaPX;
    private JComboBox<String> cbSearchOpMaPX;
    private JTextField txtSearchNguoiXuat;
    private JComboBox<String> cbSearchLogicOp;
    private JButton btnSearch;
    private JButton btnClearSearch;

    public PhieuXuatGUI() {
        this.phieuXuatBUS = new PhieuXuatBUS();
        this.nguyenLieuBUS = new NguyenLieuBUS();
        this.nhanVienBUS = new NhanVienBUS();
        this.chiTietList = new ArrayList<>();
        initComponents();
        loadDataToTable();
        loadNguyenLieuComboBox();
        loadNhanVienComboBox();
    }

    private void initComponents() {
        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        mainSplitPane.setDividerLocation(600);
        mainSplitPane.setResizeWeight(0.6);

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
        panel.setBorder(BorderFactory.createTitledBorder("Tìm kiếm phiếu xuất"));
        panel.setPreferredSize(new Dimension(600, 150));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Row 1: Mã PX
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Mã PX:"), gbc);
        
        gbc.gridx = 1;
        txtSearchMaPX = new JTextField(10);
        panel.add(txtSearchMaPX, gbc);
        
        gbc.gridx = 2;
        cbSearchOpMaPX = new JComboBox<>(new String[]{"=", ">", ">=", "<", "<=", "<>"});
        panel.add(cbSearchOpMaPX, gbc);
        
        // Row 2: Người xuất (name search)
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Tên người xuất:"), gbc);
        
        gbc.gridx = 1;
        txtSearchNguoiXuat = new JTextField(10);
        panel.add(txtSearchNguoiXuat, gbc);
        
        // Row 3: Logical operator and buttons
        gbc.gridx = 0; gbc.gridy = 2;
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
        
        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        inputPanel.add(new JLabel("Mã Phiếu Xuất:"));
        txtPhieuXuatId = new JTextField();
        inputPanel.add(txtPhieuXuatId);
        
        inputPanel.add(new JLabel("Người xuất:"));
        cbNhanVien = new JComboBox<>();
        inputPanel.add(cbNhanVien);
        
        inputPanel.add(new JLabel("Nguyên liệu:"));
        cbNguyenLieu = new JComboBox<>();
        cbNguyenLieu.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, 
                    boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof NguyenLieuDTO) {
                    NguyenLieuDTO nl = (NguyenLieuDTO) value;
                    setText(nl.getTenNl() + " (" + nl.getDonVi() + ")");
                }
                return this;
            }
        });
        inputPanel.add(cbNguyenLieu);
        
        inputPanel.add(new JLabel("Số lượng:"));
        txtSoLuong = new JTextField();
        inputPanel.add(txtSoLuong);
        
        JButton btnAdd = new JButton("Thêm vào phiếu");
        btnAdd.addActionListener(e -> addToPhieuXuat());
        inputPanel.add(btnAdd);
        
        panel.add(inputPanel, BorderLayout.NORTH);
        
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

    private void performAdvancedSearch() {
        Map<String, String> filters = new HashMap<>();
        
        if (!txtSearchMaPX.getText().trim().isEmpty()) {
            filters.put("px_id", cbSearchOpMaPX.getSelectedItem() + " " + txtSearchMaPX.getText().trim());
        }
        
        if (!txtSearchNguoiXuat.getText().trim().isEmpty()) {
            filters.put("nguoi_xuat_name", "LIKE " + txtSearchNguoiXuat.getText().trim());
        }
        
        if (!filters.isEmpty()) {
            filters.put("logic", (String) cbSearchLogicOp.getSelectedItem());
        }
        
        try {
            List<PhieuXuatDTO> searchResults = phieuXuatBUS.advancedSearch(filters);
            updateTableWithResults(searchResults);
            
            if (searchResults.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy phiếu xuất phù hợp");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void clearSearchFields() {
        txtSearchMaPX.setText("");
        txtSearchNguoiXuat.setText("");
        loadDataToTable();
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

    private void loadNguyenLieuComboBox() {
        cbNguyenLieu.removeAllItems();
        List<NguyenLieuDTO> list = nguyenLieuBUS.getAllNguyenLieu();
        if (list != null) {
            for (NguyenLieuDTO nl : list) {
                cbNguyenLieu.addItem(new NguyenLieuDTO(nl.getNlId(), nl.getTenNl(), nl.getDonVi(), nl.getSoLuong()));
            }
        }
    }

    private void loadNhanVienComboBox() {
        cbNhanVien.removeAllItems();
        List<NhanVien> list = nhanVienBUS.getDanhSachNhanVien();
        if (list != null) {
            for (NhanVien nv : list) {
                cbNhanVien.addItem(nv.getHoTen() + " (ID: " + nv.getMaNhanvien() + ")");
            }
        }
    }

    private String getNhanVienName(int nvId) {
        NhanVien nv = nhanVienBUS.getNhanVienById(nvId);
        return nv != null ? nv.getHoTen() : "NV " + nvId;
    }

    private void addToPhieuXuat() {
        try {
            NguyenLieuDTO selectedNL = (NguyenLieuDTO) cbNguyenLieu.getSelectedItem();
            float soLuong = Float.parseFloat(txtSoLuong.getText());
            
            if (selectedNL == null || soLuong <= 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
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
        
        try {
            int pxId = Integer.parseInt(txtPhieuXuatId.getText().trim());
            
            if (pxId <= 0) {
                JOptionPane.showMessageDialog(this, "ID phải là số dương", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (phieuXuatBUS.isIdExists(pxId)) {
                JOptionPane.showMessageDialog(this, "ID đã tồn tại, vui lòng chọn ID khác", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String selectedNVString = (String) cbNhanVien.getSelectedItem();
            if (selectedNVString == null || selectedNVString.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn người xuất", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int nguoiXuatId = extractEmployeeId(selectedNVString);
            
            PhieuXuatDTO phieuXuat = new PhieuXuatDTO();
            phieuXuat.setPxId(pxId);
            phieuXuat.setNgayXuat(new Date());
            phieuXuat.setNguoiXuatId(nguoiXuatId);
            
            int createdId = phieuXuatBUS.createPhieuXuat(phieuXuat, chiTietList);
            if (createdId > 0) {
                for (CTPhieuXuatDTO ct : chiTietList) {
                    nguyenLieuBUS.updateNguyenLieuQuantityXuat(ct.getNlId(), (long) ct.getSoLuong());
                }
                
                JOptionPane.showMessageDialog(this, "Tạo phiếu xuất thành công với mã: " + createdId, 
                    "Thành công", JOptionPane.INFORMATION_MESSAGE);
                refreshForm();
                loadDataToTable();
                loadNguyenLieuComboBox();
            } else {
                JOptionPane.showMessageDialog(this, "Tạo phiếu xuất thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID phải là số nguyên", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int extractEmployeeId(String employeeString) {
        try {
            int start = employeeString.indexOf("(ID: ") + 5;
            int end = employeeString.indexOf(")");
            String idStr = employeeString.substring(start, end).trim();
            return Integer.parseInt(idStr);
        } catch (Exception e) {
            return -1;
        }
    }

    private void refreshForm() {
        chiTietList.clear();
        chiTietModel.setRowCount(0);
        txtGhiChu.setText("");
        txtSoLuong.setText("");
        txtPhieuXuatId.setText("");
        if (cbNhanVien.getItemCount() > 0) {
            cbNhanVien.setSelectedIndex(0);
        }
        if (cbNguyenLieu.getItemCount() > 0) {
            cbNguyenLieu.setSelectedIndex(0);
        }
    }
}