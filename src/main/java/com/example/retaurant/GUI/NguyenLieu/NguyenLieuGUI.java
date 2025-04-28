package com.example.retaurant.GUI.NguyenLieu;

import com.example.retaurant.BUS.NguyenLieuBUS;
import com.example.retaurant.DTO.NguyenLieuDTO;
import com.example.retaurant.GUI.PhieuNhap.PhieuNhapGUI;
import com.example.retaurant.GUI.PhieuXuat.PhieuXuatGUI;

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
import java.sql.Connection;
import java.util.List;

public class NguyenLieuGUI extends JPanel { // Changed class to JPanel
    private final NguyenLieuBUS nguyenLieuBUS = new NguyenLieuBUS();
    private final DefaultTableModel tableModel = new DefaultTableModel(
            new Object[]{"ID", "Tên nguyên liệu", "Đơn vị", "Số lượng"}, 0);
    private final JTable table = new JTable(tableModel);
    private final JTextField txtId = new JTextField();
    private final JTextField txtTen = new JTextField();
    private final JTextField txtDonVi = new JTextField();
    private final JTextField txtSearch = new JTextField();

    public NguyenLieuGUI() {
        initComponents();
        loadDataToTable();
    }

    private void initComponents() {
        this.setLayout(new BorderLayout(10, 10)); // Set layout for the JPanel
        this.setBorder(new EmptyBorder(15, 15, 15, 15));
        this.setBackground(Color.WHITE);

        JPanel searchPanel = createSearchPanel();
        configureTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JPanel inputPanel = createInputPanel();
        JPanel buttonPanel = createButtonPanel();

        this.add(searchPanel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(inputPanel, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new BorderLayout(10, 10));
        searchPanel.setBackground(Color.WHITE);

        JLabel searchLabel = new JLabel("Tìm kiếm:");
        searchLabel.setFont(new Font("Arial", Font.BOLD, 14));

        txtSearch.setFont(new Font("Arial", Font.PLAIN, 14));
        txtSearch.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JButton btnSearch = new JButton("Tìm");
        btnSearch.setFont(new Font("Arial", Font.BOLD, 14));
        btnSearch.setBackground(new Color(240, 240, 240));
        btnSearch.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        btnSearch.addActionListener(e -> searchNguyenLieu());

        searchPanel.add(searchLabel, BorderLayout.WEST);
        searchPanel.add(txtSearch, BorderLayout.CENTER);
        searchPanel.add(btnSearch, BorderLayout.EAST);

        return searchPanel;
    }

    private void configureTable() {
        table.setFillsViewportHeight(true);
        table.setShowGrid(true);
        table.setGridColor(Color.LIGHT_GRAY);
        table.setRowHeight(30);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setBackground(Color.LIGHT_GRAY);

        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setBackground(isSelected ? new Color(200, 200, 255) : Color.WHITE);
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
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(new EmptyBorder(10, 0, 10, 0));

        // Changed: Make ID field editable but disable it visually
        addInputField(inputPanel, "ID:", txtId);
        txtId.setEditable(true);  // Changed from false to true
        txtId.setEnabled(false);  // Visual indication it can't be edited directly
        txtId.setBackground(new Color(240, 240, 240)); // Gray background to show it's disabled

        addInputField(inputPanel, "Tên nguyên liệu:", txtTen);
        addInputField(inputPanel, "Đơn vị:", txtDonVi);

        // Add row selection listener to populate fields when a row is selected
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int selectedRow = table.getSelectedRow();
                txtId.setText(tableModel.getValueAt(selectedRow, 0).toString());
                txtTen.setText(tableModel.getValueAt(selectedRow, 1).toString());
                txtDonVi.setText(tableModel.getValueAt(selectedRow, 2).toString());
            }
        });

        return inputPanel;
    }

    private void addInputField(JPanel panel, String labelText, JTextField field) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(label);

        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panel.add(field);
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(Color.WHITE);

        buttonPanel.add(createButton("Thêm", e -> addNguyenLieu()));
        buttonPanel.add(createButton("Cập nhật", e -> updateNguyenLieu()));
        buttonPanel.add(createButton("Xóa", e -> deleteNguyenLieu()));
        buttonPanel.add(createButton("Làm mới", e -> clearForm()));
        buttonPanel.add(createButton("Excel", e -> exportToExcel()));
        buttonPanel.add(createButton("Nhập", e -> showPhieuNhapGUI()));
        buttonPanel.add(createButton("Xuất", e -> showPhieuXuatGUI()));

        return buttonPanel;
    }

    private JButton createButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(240, 240, 240));
        button.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        button.addActionListener(listener);
        return button;
    }

    public void loadDataToTable() {
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

            if (ten.isEmpty() || donVi.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin");
                return;
            }

            NguyenLieuDTO nl = new NguyenLieuDTO(0, ten, donVi, (float) 0);
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

            if (ten.isEmpty() || donVi.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin");
                return;
            }
            
            Float currentQuantity = nguyenLieuBUS.getNguyenLieuById(id).getSoLuong();

            NguyenLieuDTO nl = new NguyenLieuDTO(id, ten, donVi, currentQuantity);
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
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một nguyên liệu để xóa", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

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
            JOptionPane.showMessageDialog(this, "Lỗi: ID không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
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
            JFrame frame = new JFrame("Panel Runner");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            NguyenLieuGUI panel = new NguyenLieuGUI();
            frame.getContentPane().add(panel);

            frame.setSize(1200, 800);
            frame.setLocationRelativeTo(null); // Center the frame
            frame.setVisible(true);
        });
    }
    private void showPhieuNhapGUI() {
    
    JFrame frame = new JFrame("Quản lý Phiếu Nhập");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.getContentPane().add(new PhieuNhapGUI()); // Replace with your actual connection
    frame.pack();
    frame.setSize(1000, 600);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
}
    private void showPhieuXuatGUI() {
    
    JFrame frame = new JFrame("Quản lý Phiếu Xuất");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.getContentPane().add(new PhieuXuatGUI()); // Replace with your actual connection
    frame.pack();
    frame.setSize(1000, 600);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
}
    // Removed the main method as this is now a JPanel
}
