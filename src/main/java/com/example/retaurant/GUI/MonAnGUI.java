/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.retaurant.GUI;

/**
 *
 * @author Administrator
 */
import java.util.List;
import com.example.retaurant.BUS.MonAnBUS;
import com.example.retaurant.DTO.MonAnDTO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MonAnGUI extends JPanel {
    private final JTable table;
    private final DefaultTableModel tableModel;
    private final JTextField txtTimKiem;
    private final JTextField txtID;
    private final JTextField txtTen;
    private final JTextField txtGia;
    private final JTextField txtTrangThai;
    private final JButton btnThem;
    private final JButton btnXoa;
    private final JButton btnSua;
    private MonAnBUS monAnBUS;

    public MonAnGUI() {
          setPreferredSize(new Dimension(800, 450));
        setLayout(new BorderLayout());

        JPanel panelTable = new JPanel(new BorderLayout());
        String[] columnNames = {"STT", "ID món ăn", "Tên món ăn", "Giá", "Trạng thái"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        panelTable.add(scrollPane, BorderLayout.CENTER);
        add(panelTable, BorderLayout.CENTER);

        JPanel panelForm = new JPanel();
        panelForm.setLayout(null);
        panelForm.setPreferredSize(new Dimension(250, 400));
        add(panelForm, BorderLayout.EAST);

        JLabel lblTimKiem = new JLabel("Tìm Kiếm Món Ăn");
        lblTimKiem.setBounds(10, 10, 120, 20);
        panelForm.add(lblTimKiem);
        txtTimKiem = new JTextField();
        txtTimKiem.setBounds(130, 10, 100, 25);
        panelForm.add(txtTimKiem);

        JLabel lblID = new JLabel("ID Món Ăn");
        lblID.setBounds(10, 50, 100, 20);
        panelForm.add(lblID);
        txtID = new JTextField();
        txtID.setBounds(130, 50, 100, 25);
        panelForm.add(txtID);

        JLabel lblTen = new JLabel("Tên Món Ăn");
        lblTen.setBounds(10, 90, 100, 20);
        panelForm.add(lblTen);
        txtTen = new JTextField();
        txtTen.setBounds(130, 90, 100, 25);
        panelForm.add(txtTen);

        JLabel lblGia = new JLabel("Giá");
        lblGia.setBounds(10, 130, 100, 20);
        panelForm.add(lblGia);
        txtGia = new JTextField();
        txtGia.setBounds(130, 130, 100, 25);
        panelForm.add(txtGia);

        JLabel lblTrangThai = new JLabel("Trạng Thái");
        lblTrangThai.setBounds(10, 170, 100, 20);
        panelForm.add(lblTrangThai);
        txtTrangThai = new JTextField();
        txtTrangThai.setBounds(130, 170, 100, 25);
        panelForm.add(txtTrangThai);

        // Nút Thêm, Xóa, Sửa
        btnThem = new JButton("Thêm");
        btnThem.setBounds(10, 220, 80, 30);
        panelForm.add(btnThem);

        btnXoa = new JButton("Xóa");
        btnXoa.setBounds(90, 220, 80, 30);
        panelForm.add(btnXoa);

        btnSua = new JButton("Sửa");
        btnSua.setBounds(170, 220, 80, 30);
        panelForm.add(btnSua);

        btnThem.addActionListener((ActionEvent e) -> {
            themMonAn();
        });
        
         btnXoa.addActionListener((ActionEvent e) -> {
             xoaMonAn();
        });

        btnSua.addActionListener((ActionEvent e) -> {
            suaMonAn();
        });
        
        loadMonAn();
        setVisible(true);
    }
    
    private void loadMonAn() {
        tableModel.setRowCount(0); 
        List<MonAnDTO> dsMonAn = monAnBUS.getAllMonAn();
        int stt = 1;
        for (MonAnDTO monAn : dsMonAn) {
            tableModel.addRow(new Object[]{stt++, monAn.getSpId(), monAn.getTenSp(), monAn.getGiaSp(), monAn.getTrangThai()});
        }
    }
    
    private void themMonAn() {
        try {
            int id = Integer.parseInt(txtID.getText());
            String ten = txtTen.getText();
            int gia = Integer.parseInt(txtGia.getText());
            int trangThai = Integer.parseInt(txtTrangThai.getText());

            MonAnDTO monAn = new MonAnDTO(id, ten, gia, trangThai);
            if (monAnBUS.addMonAn(monAn)) {
                JOptionPane.showMessageDialog(this, "Thêm món ăn thành công!");
                loadMonAn();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm món ăn thất bại!");
            }
        } catch (HeadlessException | NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Lỗi nhập liệu!");
        }
    }

    
    private void xoaMonAn() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn món ăn để xóa!");
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 1);
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa món ăn này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (monAnBUS.deleteMonAn(id)) {
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
                loadMonAn();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại!");
            }
        }
    }
    
    private void suaMonAn() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn món ăn để sửa!");
            return;
        }

        try {
            int id = (int) tableModel.getValueAt(selectedRow, 1);
            String ten = txtTen.getText();
            int gia = Integer.parseInt(txtGia.getText());
            int trangThai = Integer.parseInt(txtTrangThai.getText());

            MonAnDTO monAn = new MonAnDTO(id, ten, gia, trangThai);
            if (monAnBUS.updateMonAn(monAn)) {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                loadMonAn();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
            }
        } catch (HeadlessException | NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Lỗi nhập liệu!");
        }
    }
        
    public static void main(String[] args) {
        
    JFrame frame = new JFrame("Quản Lý Món Ăn");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(800, 450);
    frame.setLocationRelativeTo(null);
    frame.setContentPane(new MonAnGUI());
    frame.setVisible(true);
    }
}

    
