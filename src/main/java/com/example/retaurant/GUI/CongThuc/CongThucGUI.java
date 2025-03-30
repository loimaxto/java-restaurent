package com.example.retaurant.GUI.CongThuc;

import com.example.retaurant.BUS.CongThucBUS;
import com.example.retaurant.BUS.MonAnBUS;
import com.example.retaurant.BUS.NguyenLieuBUS;
import com.example.retaurant.DTO.CongThucDTO;
import com.example.retaurant.DTO.MonAnDTO;
import com.example.retaurant.DTO.NguyenLieuDTO;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

public class CongThucGUI extends JFrame {
    private final CongThucBUS congThucBUS = new CongThucBUS();
    private final MonAnBUS monAnBUS = new MonAnBUS();
    private final NguyenLieuBUS nguyenLieuBUS = new NguyenLieuBUS();
    
    private final DefaultTableModel tableModel = new DefaultTableModel(
        new Object[]{"Món ăn", "Nguyên liệu", "Số lượng", "Đơn vị"}, 0);
    private final JTable table = new JTable(tableModel);
    
    private final JComboBox<MonAnDTO> cboMonAn = new JComboBox<>();
    private final JComboBox<NguyenLieuDTO> cboNguyenLieu = new JComboBox<>();
    private final JTextField txtSoLuong = new JTextField();
    private final JTextField txtSearch = new JTextField();
    
    private Image backgroundImage;

    public CongThucGUI() {
        initComponents();
        loadDataToTable();
    }

    private void initComponents() {
        this.setTitle("Quản lý Công thức món ăn");
        this.setSize(1000, 600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);

        try {
            backgroundImage = new ImageIcon("image/LoginUI/background-image.jpg").getImage();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Could not load background image");
            backgroundImage = null;
        }

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setOpaque(false);

        JPanel searchPanel = createSearchPanel();
        configureTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));

        JPanel inputPanel = createInputPanel();
        JPanel buttonPanel = createButtonPanel();

        mainPanel.add(searchPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setOpaque(false);
        bottomPanel.add(inputPanel, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        this.setContentPane(mainPanel);
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new BorderLayout(10, 10));
        searchPanel.setOpaque(false);
        
        JLabel searchLabel = new JLabel("Tìm kiếm:");
        styleLabel(searchLabel);
        
        txtSearch.setOpaque(false);
        styleTextField(txtSearch);
        
        JButton btnSearch = createButton("Tìm", "image/Search-icon.png");
        btnSearch.addActionListener(e -> searchCongThuc());
        
        searchPanel.add(searchLabel, BorderLayout.WEST);
        searchPanel.add(txtSearch, BorderLayout.CENTER);
        searchPanel.add(btnSearch, BorderLayout.EAST);
        
        return searchPanel;
    }

    private void configureTable() {
        table.setOpaque(false);
        table.setFillsViewportHeight(true);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setRowHeight(30);
        
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(70, 130, 180, 200));
        header.setForeground(Color.WHITE);
        header.setOpaque(false);
        
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setOpaque(false);
                setBackground(isSelected ? new Color(100, 150, 200, 150) : new Color(255, 255, 255, 150));
                setForeground(isSelected ? Color.WHITE : Color.BLACK);
                setBorder(new EmptyBorder(0, 5, 0, 5));
                return this;
            }
        };
        
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setOpaque(false);
        inputPanel.setBorder(new EmptyBorder(10, 0, 10, 0));

        // Dish selection
        JLabel lblMonAn = new JLabel("Món ăn:");
        styleLabel(lblMonAn);
        inputPanel.add(lblMonAn);
        
        cboMonAn.setOpaque(false);
        styleComboBox(cboMonAn);
        monAnBUS.getAllMonAn().forEach(cboMonAn::addItem);
        inputPanel.add(cboMonAn);

        // Ingredient selection
        JLabel lblNguyenLieu = new JLabel("Nguyên liệu:");
        styleLabel(lblNguyenLieu);
        inputPanel.add(lblNguyenLieu);
        
        cboNguyenLieu.setOpaque(false);
        styleComboBox(cboNguyenLieu);
        nguyenLieuBUS.getAllNguyenLieu().forEach(cboNguyenLieu::addItem);
        inputPanel.add(cboNguyenLieu);

        // Quantity
        JLabel lblSoLuong = new JLabel("Số lượng:");
        styleLabel(lblSoLuong);
        inputPanel.add(lblSoLuong);
        
        txtSoLuong.setOpaque(false);
        styleTextField(txtSoLuong);
        inputPanel.add(txtSoLuong);

        return inputPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setOpaque(false);
        
        buttonPanel.add(createButton("Thêm", "image/add-icon.png", e -> addCongThuc()));
        buttonPanel.add(createButton("Cập nhật", "image/check-icon.png", e -> updateCongThuc()));
        buttonPanel.add(createButton("Xóa", "image/delete-icon.png", e -> deleteCongThuc()));
        buttonPanel.add(createButton("Làm mới", "image/Refresh-icon.png", e -> clearForm()));
        
        return buttonPanel;
    }

    private JButton createButton(String text, String iconPath) {
        return createButton(text, iconPath, null);
    }

    private JButton createButton(String text, String iconPath, ActionListener listener) {
        JButton button = new JButton(text);
        
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
            if (icon.getImageLoadStatus() == MediaTracker.COMPLETE) {
                button.setIcon(new ImageIcon(icon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH)));
            }
        } catch (Exception e) {
            System.out.println("Could not load icon: " + iconPath);
        }
        
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(70, 130, 180, 200));
        button.setOpaque(true);
        button.setBorder(new CompoundBorder(
            new LineBorder(new Color(255, 255, 255, 100)), 
            new EmptyBorder(5, 15, 5, 15)
        ));
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        if (listener != null) {
            button.addActionListener(listener);
        }
        
        return button;
    }

    private void styleLabel(JLabel label) {
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(Color.WHITE);
    }

    private void styleTextField(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setForeground(Color.BLACK);
        field.setOpaque(true);
        field.setBackground(new Color(255, 255, 255, 180));
        field.setBorder(new CompoundBorder(
            new LineBorder(new Color(200, 200, 200, 150), 1),
            new EmptyBorder(5, 10, 5, 10)
        ));
    }
    
    private void styleComboBox(JComboBox<?> comboBox) {
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboBox.setForeground(Color.BLACK);
        comboBox.setBackground(new Color(255, 255, 255, 180));
        comboBox.setBorder(new CompoundBorder(
            new LineBorder(new Color(200, 200, 200, 150), 1),
            new EmptyBorder(5, 10, 5, 10)
        ));
        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setOpaque(isSelected);
                if (value instanceof MonAnDTO) {
                    setText(((MonAnDTO)value).getTenSp());
                } else if (value instanceof NguyenLieuDTO) {
                    setText(((NguyenLieuDTO)value).getTenNl());
                }
                return this;
            }
        });
    }

    // Business Logic Methods
    private void loadDataToTable() {
        List<CongThucDTO> list = congThucBUS.getAllCongThuc();
        tableModel.setRowCount(0);
        
        if (list != null) {
            for (CongThucDTO ct : list) {
                String tenMon = monAnBUS.getMonAnById(ct.getSpId()) != null ? 
                    monAnBUS.getMonAnById(ct.getSpId()).getTenSp() : "Unknown";
                String tenNL = nguyenLieuBUS.getNguyenLieuById(ct.getNlId()) != null ? 
                    nguyenLieuBUS.getNguyenLieuById(ct.getNlId()).getTenNl() : "Unknown";
                String donVi = nguyenLieuBUS.getNguyenLieuById(ct.getNlId()) != null ? 
                    nguyenLieuBUS.getNguyenLieuById(ct.getNlId()).getDonVi() : "";
                
                tableModel.addRow(new Object[]{tenMon, tenNL, ct.getSoLuong(), donVi});
            }
        }
    }

    private void addCongThuc() {
        try {
            MonAnDTO selectedMon = (MonAnDTO)cboMonAn.getSelectedItem();
            NguyenLieuDTO selectedNL = (NguyenLieuDTO)cboNguyenLieu.getSelectedItem();
            int soLuong = Integer.parseInt(txtSoLuong.getText().trim());
            
            if (selectedMon == null || selectedNL == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn món ăn và nguyên liệu");
                return;
            }
            
            if (soLuong <= 0) {
                JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0");
                return;
            }
            
            CongThucDTO newCT = new CongThucDTO(selectedMon.getSpId(), selectedNL.getNlId(), soLuong);
            if (congThucBUS.addCongThuc(newCT)) {
                JOptionPane.showMessageDialog(this, "Thêm công thức thành công");
                loadDataToTable();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm công thức thất bại");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên");
        }
    }

    private void updateCongThuc() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn công thức cần sửa");
            return;
        }
        
        try {
            String tenMon = tableModel.getValueAt(selectedRow, 0).toString();
            String tenNL = tableModel.getValueAt(selectedRow, 1).toString();
            
            // Find original IDs
            int originalSpId = -1, originalNlId = -1;
            for (MonAnDTO mon : monAnBUS.getAllMonAn()) {
                if (mon.getTenSp().equals(tenMon)) {
                    originalSpId = mon.getSpId();
                    break;
                }
            }
            for (NguyenLieuDTO nl : nguyenLieuBUS.getAllNguyenLieu()) {
                if (nl.getTenNl().equals(tenNL)) {
                    originalNlId = nl.getNlId();
                    break;
                }
            }
            
            // Get new values
            MonAnDTO newMon = (MonAnDTO)cboMonAn.getSelectedItem();
            NguyenLieuDTO newNL = (NguyenLieuDTO)cboNguyenLieu.getSelectedItem();
            int newSoLuong = Integer.parseInt(txtSoLuong.getText().trim());
            
            if (newMon == null || newNL == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn món ăn và nguyên liệu");
                return;
            }
            
            // Delete old and add new if IDs changed
            if (originalSpId != newMon.getSpId() || originalNlId != newNL.getNlId()) {
                congThucBUS.deleteCongThuc(originalSpId, originalNlId);
                congThucBUS.addCongThuc(new CongThucDTO(
                    newMon.getSpId(), newNL.getNlId(), newSoLuong
                ));
            } else {
                // Just update quantity if IDs are same
                congThucBUS.updateCongThuc(new CongThucDTO(
                    originalSpId, originalNlId, newSoLuong
                ));
            }
            
            loadDataToTable();
            clearForm();
            JOptionPane.showMessageDialog(this, "Cập nhật công thức thành công");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên");
        }
    }

    private void deleteCongThuc() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn công thức cần xóa");
            return;
        }
        
        try {
            String tenMon = tableModel.getValueAt(selectedRow, 0).toString();
            String tenNL = tableModel.getValueAt(selectedRow, 1).toString();
            
            // Find IDs
            int spId = -1, nlId = -1;
            for (MonAnDTO mon : monAnBUS.getAllMonAn()) {
                if (mon.getTenSp().equals(tenMon)) {
                    spId = mon.getSpId();
                    break;
                }
            }
            for (NguyenLieuDTO nl : nguyenLieuBUS.getAllNguyenLieu()) {
                if (nl.getTenNl().equals(tenNL)) {
                    nlId = nl.getNlId();
                    break;
                }
            }
            
            if (spId != -1 && nlId != -1) {
                int confirm = JOptionPane.showConfirmDialog(this, 
                    "Bạn có chắc chắn muốn xóa công thức này?", "Xác nhận xóa", 
                    JOptionPane.YES_NO_OPTION);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    if (congThucBUS.deleteCongThuc(spId, nlId)) {
                        JOptionPane.showMessageDialog(this, "Xóa công thức thành công");
                        loadDataToTable();
                        clearForm();
                    } else {
                        JOptionPane.showMessageDialog(this, "Xóa công thức thất bại");
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi xóa công thức");
        }
    }

    private void searchCongThuc() {
        String keyword = txtSearch.getText().trim().toLowerCase();
        List<CongThucDTO> list = congThucBUS.getAllCongThuc();
        tableModel.setRowCount(0);
        
        if (list != null) {
            for (CongThucDTO ct : list) {
                String tenMon = monAnBUS.getMonAnById(ct.getSpId()) != null ? 
                    monAnBUS.getMonAnById(ct.getSpId()).getTenSp().toLowerCase() : "";
                String tenNL = nguyenLieuBUS.getNguyenLieuById(ct.getNlId()) != null ? 
                    nguyenLieuBUS.getNguyenLieuById(ct.getNlId()).getTenNl().toLowerCase() : "";
                
                if (tenMon.contains(keyword) || tenNL.contains(keyword)) {
                    String donVi = nguyenLieuBUS.getNguyenLieuById(ct.getNlId()) != null ? 
                        nguyenLieuBUS.getNguyenLieuById(ct.getNlId()).getDonVi() : "";
                    
                    tableModel.addRow(new Object[]{
                        monAnBUS.getMonAnById(ct.getSpId()).getTenSp(),
                        nguyenLieuBUS.getNguyenLieuById(ct.getNlId()).getTenNl(),
                        ct.getSoLuong(),
                        donVi
                    });
                }
            }
        }
    }

    private void clearForm() {
        cboMonAn.setSelectedIndex(0);
        cboNguyenLieu.setSelectedIndex(0);
        txtSoLuong.setText("");
        table.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            CongThucGUI gui = new CongThucGUI();
            gui.setVisible(true);
        });
    }
}