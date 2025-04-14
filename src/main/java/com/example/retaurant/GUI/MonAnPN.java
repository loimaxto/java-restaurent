package com.example.retaurant.GUI;

import com.example.retaurant.BUS.MonAnBUS;
import com.example.retaurant.DTO.MonAnDTO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

public class MonAnPN extends JPanel {

    private final JTable table;
    private final DefaultTableModel tableModel;
    private final JTextField txtTimKiem;
    private final JButton btnThem;
    private final JButton btnXoa;
    private final JButton btnSua;
    private MonAnBUS monAnBUS;
    private final JTextField txtTenMon;
    private final JTextField txtGia;
    private final JTextField txtXoa;

    private final JTextField txtIDSua;
    private final JTextField txtTenSua;
    private final JTextField txtGiaSua;

    public MonAnPN() {
        setPreferredSize(new Dimension(800, 450));
        setLayout(new BorderLayout());

        monAnBUS = new MonAnBUS();

        // Bảng món ăn
        JPanel panelTable = new JPanel(new BorderLayout());
        String[] columnNames = {"STT", "ID món ăn", "Tên món ăn", "Giá"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setDefaultEditor(Object.class, null);

        JScrollPane scrollPane = new JScrollPane(table);
        panelTable.add(scrollPane, BorderLayout.CENTER);
        add(panelTable, BorderLayout.CENTER);

        // Panel bên phải
        JPanel panelForm = new JPanel();
        panelForm.setLayout(new BoxLayout(panelForm, BoxLayout.Y_AXIS));
        panelForm.setPreferredSize(new Dimension(250, 400));
        add(panelForm, BorderLayout.EAST);

        // Tìm kiếm theo tên
        JPanel panelSearch = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblTimKiem = new JLabel("Tìm theo tên:");
        txtTimKiem = new JTextField(12);
        panelSearch.add(lblTimKiem);
        panelSearch.add(txtTimKiem);
        panelForm.add(panelSearch);

        txtTimKiem.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { timTheoTen(); }
            public void removeUpdate(DocumentEvent e) { timTheoTen(); }
            public void changedUpdate(DocumentEvent e) { timTheoTen(); }
        });

        // Tìm kiếm nâng cao
        JPanel panelTimKiemNangCao = new JPanel();
        panelTimKiemNangCao.setLayout(new BoxLayout(panelTimKiemNangCao, BoxLayout.Y_AXIS));
        panelTimKiemNangCao.setBorder(BorderFactory.createTitledBorder("Tìm kiếm nâng cao"));

        JPanel panelID = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblID = new JLabel("ID:");
        JTextField txtIDSearch = new JTextField(10);
        panelID.add(lblID);
        panelID.add(txtIDSearch);

        JPanel panelGia = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblGiaTu = new JLabel("Giá từ:");
        JTextField txtGiaTu = new JTextField(7);
        JLabel lblGiaDen = new JLabel("đến:");
        JTextField txtGiaDen = new JTextField(7);
        panelGia.add(lblGiaTu);
        panelGia.add(txtGiaTu);
        panelGia.add(lblGiaDen);
        panelGia.add(txtGiaDen);

        JButton btnTimNangCao = new JButton("Tìm nâng cao");

        panelTimKiemNangCao.add(panelID);
        panelTimKiemNangCao.add(panelGia);
        panelTimKiemNangCao.add(btnTimNangCao);
        panelForm.add(panelTimKiemNangCao);

        // Các nút chức năng
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        btnThem = new JButton("Thêm");
        btnThem.setPreferredSize(new Dimension(70, 30));
        btnXoa = new JButton("Xóa");
        btnXoa.setPreferredSize(new Dimension(70, 30));
        btnSua = new JButton("Sửa");
        btnSua.setPreferredSize(new Dimension(70, 30));
        panelButtons.add(btnThem);
        panelButtons.add(btnXoa);
        panelButtons.add(btnSua);
        panelForm.add(panelButtons);

        // Card layout cho form Thêm/Xóa/Sửa
        CardLayout cardLayout = new CardLayout();
        JPanel panelContainer = new JPanel(cardLayout);
        panelContainer.setPreferredSize(new Dimension(250, 200));
        panelForm.add(panelContainer);

        JPanel panelThem = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelThem.setBackground(Color.LIGHT_GRAY);
        JLabel lblTenMon = new JLabel("Tên món:");
        txtTenMon = new JTextField(20);
        JLabel lblGia = new JLabel("Giá:");
        txtGia = new JTextField(20);
        JButton btnLuu = new JButton("Lưu");
        panelThem.add(lblTenMon);
        panelThem.add(txtTenMon);
        panelThem.add(lblGia);
        panelThem.add(txtGia);
        panelThem.add(btnLuu);

        JPanel panelXoa = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelXoa.setBackground(Color.PINK);
        JLabel lblXoa = new JLabel("Nhập ID món ăn cần xóa:");
        txtXoa = new JTextField(10);
        JButton btnXacNhanXoa = new JButton("Xóa");
        panelXoa.add(lblXoa);
        panelXoa.add(txtXoa);
        panelXoa.add(btnXacNhanXoa);

        JPanel panelSua = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelSua.setBackground(Color.PINK);
        JLabel label2 = new JLabel("Nhập ID để sửa:");
        txtIDSua = new JTextField(15);
        JLabel lblTenSua = new JLabel("Nhập Tên:");
        txtTenSua = new JTextField(18);
        JLabel lblGiaSua = new JLabel("Nhập Giá:");
        txtGiaSua = new JTextField(18);
        JButton btnXacNhanSua = new JButton("Sửa");
        panelSua.add(label2);
        panelSua.add(txtIDSua);
        panelSua.add(lblTenSua);
        panelSua.add(txtTenSua);
        panelSua.add(lblGiaSua);
        panelSua.add(txtGiaSua);
        panelSua.add(btnXacNhanSua);

        panelContainer.add(panelThem, "Them");
        panelContainer.add(panelXoa, "Xoa");
        panelContainer.add(panelSua, "Sua");

        cardLayout.show(panelContainer, "Them");

        btnThem.addActionListener(e -> cardLayout.show(panelContainer, "Them"));
        btnXoa.addActionListener(e -> cardLayout.show(panelContainer, "Xoa"));
        btnSua.addActionListener(e -> cardLayout.show(panelContainer, "Sua"));

        btnLuu.addActionListener((ActionEvent e) -> themMonAn());
        btnXacNhanXoa.addActionListener((ActionEvent e) -> xoaMonAn());
        btnXacNhanSua.addActionListener((ActionEvent e) -> suaMonAn());

        btnTimNangCao.addActionListener(e -> {
            String idText = txtIDSearch.getText().trim();
            String giaTuText = txtGiaTu.getText().trim();
            String giaDenText = txtGiaDen.getText().trim();

            Integer id = null, giaTu = null, giaDen = null;

            try {
                if (!idText.isEmpty()) id = Integer.parseInt(idText);
                if (!giaTuText.isEmpty()) giaTu = Integer.parseInt(giaTuText);
                if (!giaDenText.isEmpty()) giaDen = Integer.parseInt(giaDenText);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Giá trị không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            List<MonAnDTO> dsMonAn = monAnBUS.getAllMonAn();
            tableModel.setRowCount(0);
            int stt = 1;

            for (MonAnDTO monAn : dsMonAn) {
                if (monAn.getTrangThai() != 1) continue;
                boolean match = true;
                if (id != null && monAn.getSpId() != id) match = false;
                if (giaTu != null && monAn.getGiaSp() < giaTu) match = false;
                if (giaDen != null && monAn.getGiaSp() > giaDen) match = false;
                if (match) {
                    tableModel.addRow(new Object[]{stt++, monAn.getSpId(), monAn.getTenSp(), monAn.getGiaSp()});
                }
            }
        });

        loadMonAn();
    }

    private void timTheoTen() {
        String keyword = txtTimKiem.getText().trim().toLowerCase();
        tableModel.setRowCount(0);
        List<MonAnDTO> dsMonAn = monAnBUS.getAllMonAn();
        int stt = 1;
        for (MonAnDTO monAn : dsMonAn) {
            if (monAn.getTrangThai() == 1 && monAn.getTenSp().toLowerCase().contains(keyword)) {
                tableModel.addRow(new Object[]{stt++, monAn.getSpId(), monAn.getTenSp(), monAn.getGiaSp()});
            }
        }
    }

    private void themMonAn() {
        try {
            String ten = txtTenMon.getText();
            int gia = Integer.parseInt(txtGia.getText());
            int trangThai = 1;

            MonAnDTO monAn = new MonAnDTO(0, ten, gia, trangThai);
            if (monAnBUS.addMonAn(monAn)) {
                JOptionPane.showMessageDialog(this, "Thêm món ăn thành công!");
                txtTenMon.setText("");
                txtGia.setText("");
                loadMonAn();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm món ăn thất bại!");
            }
        } catch (HeadlessException | NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Lỗi nhập liệu!");
        }
    }

    private void xoaMonAn() {
        try {
            int id = Integer.parseInt(txtXoa.getText());
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa món ăn này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (monAnBUS.deleteMonAn(id)) {
                    JOptionPane.showMessageDialog(this, "Xóa thành công!");
                    txtXoa.setText("");
                    loadMonAn();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại!");
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID không hợp lệ!");
        }
    }

    private void suaMonAn() {
        try {
            int id = Integer.parseInt(txtIDSua.getText());
            String ten = txtTenSua.getText();
            int gia = Integer.parseInt(txtGiaSua.getText());

            MonAnDTO monAn = new MonAnDTO(id, ten, gia, 1);
            if (monAnBUS.updateMonAn(monAn)) {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                txtIDSua.setText("");
                txtTenSua.setText("");
                txtGiaSua.setText("");
                loadMonAn();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
            }
        } catch (HeadlessException | NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Lỗi nhập liệu!");
        }
    }

    private void loadMonAn() {
        tableModel.setRowCount(0);
        List<MonAnDTO> dsMonAn = monAnBUS.getAllMonAn();
        int stt = 1;
        for (MonAnDTO monAn : dsMonAn) {
            if (monAn.getTrangThai() == 1) {
                tableModel.addRow(new Object[]{stt++, monAn.getSpId(), monAn.getTenSp(), monAn.getGiaSp()});
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Quản Lý Món Ăn");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 450);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(new MonAnPN());
        frame.setVisible(true);
    }
}