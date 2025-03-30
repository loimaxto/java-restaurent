package com.example.retaurant.GUI.NguyenLieu;

import com.example.retaurant.BUS.NguyenLieuBUS;
import com.example.retaurant.DTO.NguyenLieuDTO;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class NguyenLieuGUI extends JFrame {
    private final NguyenLieuBUS nguyenLieuBUS = new NguyenLieuBUS();
    private final DefaultTableModel tableModel = new DefaultTableModel(
        new Object[]{"ID", "Tên nguyên liệu", "Đơn vị", "Số lượng"}, 0);
    private final JTable table = new JTable(tableModel);
    private final JTextField txtId = new JTextField();
    private final JTextField txtTen = new JTextField();
    private final JTextField txtDonVi = new JTextField();
    private final JTextField txtSoLuong = new JTextField();
    private final JTextField txtSearch = new JTextField();
    private Image backgroundImage;

    public NguyenLieuGUI() {
        initComponents();
        loadDataToTable();
    }

    private void initComponents() {
        this.setTitle("Quản lý nguyên liệu");
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
        btnSearch.addActionListener(e -> searchNguyenLieu());
        
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
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setOpaque(false);
        inputPanel.setBorder(new EmptyBorder(10, 0, 10, 0));

        addInputField(inputPanel, "ID:", txtId);
        txtId.setEditable(false);
        addInputField(inputPanel, "Tên nguyên liệu:", txtTen);
        addInputField(inputPanel, "Đơn vị:", txtDonVi);
        addInputField(inputPanel, "Số lượng:", txtSoLuong);

        return inputPanel;
    }

    private void addInputField(JPanel panel, String labelText, JTextField field) {
        JLabel label = new JLabel(labelText);
        styleLabel(label);
        panel.add(label);
        
        styleTextField(field);
        panel.add(field);
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setOpaque(false);
        
        buttonPanel.add(createButton("Thêm", "image/add-icon.png", e -> addNguyenLieu()));
        buttonPanel.add(createButton("Cập nhật", "image/check-icon.png", e -> updateNguyenLieu()));
        buttonPanel.add(createButton("Xóa", "image/delete-icon.png", e -> deleteNguyenLieu()));
        buttonPanel.add(createButton("Làm mới", "image/Refresh-icon.png", e -> clearForm()));
        buttonPanel.add(createButton("Xuất Excel", "image/excel-icon.png", e -> exportToExcel()));
        
        return buttonPanel;
    }

    private JButton createButton(String text, String iconPath) {
        return createButton(text, iconPath, null);
    }

    private JButton createButton(String text, String iconPath, ActionListener listener) {
        JButton button = new JButton(text);
        
        try {
            java.net.URL imgURL = getClass().getResource("/" + iconPath);
            if (imgURL != null) {
                button.setIcon(new ImageIcon(imgURL));
            } else {
                System.err.println("Couldn't find icon: " + iconPath);
            }
        } catch (Exception e) {
            System.err.println("Error loading icon: " + e.getMessage());
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
        button.addActionListener(listener);
        
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

    private void loadDataToTable() {
        List<NguyenLieuDTO> list = nguyenLieuBUS.getAllNguyenLieu();
        tableModel.setRowCount(0);
        
        if (list != null) {
            for (NguyenLieuDTO nl : list) {
                tableModel.addRow(new Object[]{
                    nl.getNlId(),
                    nl.getTenNl(),
                    nl.getDonVi(),
                    nl.getSoLuong()
                });
            }
        }
    }

    private void addNguyenLieu() {
        try {
            String ten = txtTen.getText().trim();
            String donVi = txtDonVi.getText().trim();
            int soLuong = Integer.parseInt(txtSoLuong.getText().trim());
            
            if (ten.isEmpty() || donVi.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin");
                return;
            }
            
            NguyenLieuDTO nl = new NguyenLieuDTO(0, ten, donVi, soLuong);
            if (nguyenLieuBUS.addNguyenLieu(nl)) {
                JOptionPane.showMessageDialog(this, "Thêm thành công");
                loadDataToTable();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên");
        }
    }

    private void updateNguyenLieu() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            String ten = txtTen.getText().trim();
            String donVi = txtDonVi.getText().trim();
            int soLuong = Integer.parseInt(txtSoLuong.getText().trim());
            
            if (ten.isEmpty() || donVi.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin");
                return;
            }
            
            NguyenLieuDTO nl = new NguyenLieuDTO(id, ten, donVi, soLuong);
            if (nguyenLieuBUS.updateNguyenLieu(nl)) {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công");
                loadDataToTable();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID và số lượng phải là số nguyên");
        }
    }

    private void deleteNguyenLieu() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Bạn có chắc chắn muốn xóa nguyên liệu này?", "Xác nhận xóa", 
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                if (nguyenLieuBUS.deleteNguyenLieu(id)) {
                    JOptionPane.showMessageDialog(this, "Xóa thành công");
                    loadDataToTable();
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại");
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một nguyên liệu để xóa");
        }
    }

    private void searchNguyenLieu() {
        String name = txtSearch.getText().trim();
        List<NguyenLieuDTO> list = nguyenLieuBUS.searchNguyenLieuByName(name);
        tableModel.setRowCount(0);
        
        if (list != null) {
            for (NguyenLieuDTO nl : list) {
                tableModel.addRow(new Object[]{
                    nl.getNlId(),
                    nl.getTenNl(),
                    nl.getDonVi(),
                    nl.getSoLuong()
                });
            }
        }
    }

    private void clearForm() {
        txtId.setText("");
        txtTen.setText("");
        txtDonVi.setText("");
        txtSoLuong.setText("");
    }

    private void exportToExcel() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lưu file Excel");
        fileChooser.setSelectedFile(new File("NguyenLieu_Export.xlsx"));
        
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection != JFileChooser.APPROVE_OPTION) return;
        
        File outputFile = fileChooser.getSelectedFile();
        if (!outputFile.getName().toLowerCase().endsWith(".xlsx")) {
            outputFile = new File(outputFile.getAbsolutePath() + ".xlsx");
        }

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Nguyên Liệu");
            
            // Create header row with style
            CellStyle headerStyle = workbook.createCellStyle();
            org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            
            Row headerRow = sheet.createRow(0);
            String[] headers = {"ID", "Tên Nguyên Liệu", "Đơn Vị", "Số Lượng"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }
            
            // Export data from table
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                Row row = sheet.createRow(i + 1);
                for (int j = 0; j < tableModel.getColumnCount(); j++) {
                    Object value = tableModel.getValueAt(i, j);
                    row.createCell(j).setCellValue(value != null ? value.toString() : "");
                }
            }
            
            // Auto-size columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
            
            // Write to file
            try (FileOutputStream out = new FileOutputStream(outputFile)) {
                workbook.write(out);
                JOptionPane.showMessageDialog(this, 
                    "Xuất Excel thành công!\nĐã lưu tại: " + outputFile.getAbsolutePath(),
                    "Thành công", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi xuất Excel:\n" + ex.getMessage(),
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            NguyenLieuGUI gui = new NguyenLieuGUI();
            gui.setVisible(true);
        });
    }
}