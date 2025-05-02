/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.retaurant.GUI.ThongKe;

import com.example.retaurant.BUS.ThongKeBUS;
import com.example.retaurant.DAO.ThongKeDAO;
import com.example.retaurant.DTO.MonAnDTO;
import com.example.retaurant.DTO.ThongKe;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import javax.imageio.ImageIO;
import javax.swing.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;
import javax.swing.table.DefaultTableCellRenderer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class QuanLyThongKeGUI extends JPanel {

    private DecimalFormat dcf = new DecimalFormat("###,###");
    ThongKeBUS thongKeBus = new ThongKeBUS();
    JMenuBar menuBar;
    JMenu thongKeMenu;
    JMenuItem thongKeChungItem, thongKeTheoQuyItem, topChuyenBayItem, bieuDoDoanhThuItem, thongKeTheoNgayItem, thongKeKhoangNgayItem;
    JPanel pnThongKeChiTiet, pnChart;
    JPanel pnThongKeChung, pnThongKeTheoQuy, pnTopMonAn, pnBieuDoDoanhThu;
    //label Thong ke chung
    JLabel lblThongKeMonAn, lblThongKeKhachHang, lblThongKeDoanhThu;
    //label Thong ke theo quy
    JLabel lblThongKeQuy1, lblThongKeQuy2, lblThongKeQuy3, lblThongKeQuy4, lblThongKeQuyTong;
    //label Thong ke theo quy
    JLabel lblTopMonAn1, lblTopMonAn2, lblTopMonAn3, lblTopMonAn4, lblTopSoLuongMA1, lblTopSoLuongMA2, lblTopSoLuongMA3, lblTopSoLuongMA4;
    private JPanel pnThongKeTheoNgay;
    private JLabel lblDoanhThuNgay;
    private JComboBox<Integer> cmbNgay, cmbThang, cmbNamNgay;
    private ChartPanel chartPanel;
    private JComboBox<Integer> cmbNamBieuDo;
    private JPanel pnThongKeKhoangNgay;
    private JLabel lblDoanhThuKhoangNgay;
    private JComboBox<Integer> cmbTuNgay, cmbTuThang, cmbTuNam;
    private JComboBox<Integer> cmbDenNgay, cmbDenThang, cmbDenNam;
    final Color colorPanel = new Color(56, 56, 56);
    int w = 1030;
    int h = 844;

    public QuanLyThongKeGUI() {
        addControls();
        addEvents();
    }

    private void addControls() {
        setLayout(new BorderLayout());
        setBackground(colorPanel);
        createMenuBar();
        createThongKeTheoQuyPanel();
//        createTopChuyenBayPanel();
        createBieuDoDoanhThuPanel();
        createThongKeChungPanel();
    }

    private void createMenuBar() {
        menuBar = new JMenuBar();
        menuBar.setBackground(new Color(63, 74, 89));
        thongKeMenu = new JMenu("THỐNG KÊ TỔNG QUÁT");
        thongKeMenu.setForeground(Color.WHITE);
        thongKeChungItem = new JMenuItem("Thống kê chung");
        thongKeTheoNgayItem = new JMenuItem("Thống kê theo ngày");
        thongKeTheoQuyItem = new JMenuItem("Thống kê theo quý");
        topChuyenBayItem = new JMenuItem("Top món ăn bán chạy nhất");
        bieuDoDoanhThuItem = new JMenuItem("Biểu đồ doanh thu");
        thongKeKhoangNgayItem = new JMenuItem("Thống kê từ ngày đến ngày");
        thongKeMenu.setFont(new Font("Tahoma", Font.BOLD, 20));
        thongKeChungItem.setFont(new Font("Tahoma", Font.PLAIN, 16));
        thongKeTheoQuyItem.setFont(new Font("Tahoma", Font.PLAIN, 16));
        topChuyenBayItem.setFont(new Font("Tahoma", Font.PLAIN, 16));
        bieuDoDoanhThuItem.setFont(new Font("Tahoma", Font.PLAIN, 16));
        thongKeTheoNgayItem.setFont(new Font("Tahoma", Font.PLAIN, 16));
        thongKeKhoangNgayItem.setFont(new Font("Tahoma", Font.PLAIN, 16));
        Dimension menuSize = thongKeMenu.getPreferredSize();
        menuSize.height = 44;
        thongKeMenu.setPreferredSize(menuSize);

        thongKeMenu.add(thongKeChungItem);
        thongKeMenu.add(thongKeTheoNgayItem);
        thongKeMenu.add(thongKeKhoangNgayItem);
        thongKeMenu.add(thongKeTheoQuyItem);
        thongKeMenu.add(topChuyenBayItem);
        thongKeMenu.add(bieuDoDoanhThuItem);
        menuBar.add(thongKeMenu);
        add(menuBar, BorderLayout.NORTH);
    }

    private void createThongKeChungPanel() {
        pnThongKeChung = new JPanel();
        pnThongKeChung.setLayout(new GridBagLayout());
        pnThongKeChung.setBackground(colorPanel);

        // Tạo nhãn và đặt tỷ lệ chiều cao
        JLabel lblTileThongKeTong = new JLabel("THỐNG KÊ", JLabel.CENTER);
        lblTileThongKeTong.setFont(new Font("Tahoma", Font.BOLD, 28));
        lblTileThongKeTong.setForeground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.4;
        gbc.fill = GridBagConstraints.BOTH;

        pnThongKeChung.add(lblTileThongKeTong, gbc);

        JPanel pnThongKeContent = new JPanel();
        pnThongKeContent.setBackground(colorPanel);
        pnThongKeContent.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        lblThongKeMonAn = new JLabel("");
        lblThongKeKhachHang = new JLabel("");
        lblThongKeDoanhThu = new JLabel("");
        JPanel pnMonAn = createThongKePanel("MonAn", lblThongKeMonAn);
        JPanel pnKhachHang = createThongKePanel("KhachHang", lblThongKeKhachHang);
        JPanel pnDoanhThu = createThongKePanel("DoanhThu", lblThongKeDoanhThu);

        pnThongKeContent.add(pnMonAn);
        pnThongKeContent.add(pnKhachHang);
        pnThongKeContent.add(pnDoanhThu);

        gbc.gridy = 1;
        gbc.weighty = 0.6;
        pnThongKeChung.add(pnThongKeContent, gbc);

        add(pnThongKeChung);
        thongKeChung(pnThongKeChung);
    }

    private void createThongKeTheoNgayPanel() {
        pnThongKeTheoNgay = new JPanel();
        pnThongKeTheoNgay.setLayout(new BoxLayout(pnThongKeTheoNgay, BoxLayout.Y_AXIS));
        pnThongKeTheoNgay.setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel("THỐNG KÊ DOANH THU THEO NGÀY", JLabel.CENTER);
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 28));
        lblTitle.setForeground(Color.BLACK);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel selectorPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        selectorPanel.setOpaque(false);

        cmbNgay = new JComboBox<>();
        cmbThang = new JComboBox<>();
        cmbNamNgay = new JComboBox<>();

        for (int i = 1; i <= 31; i++) {
            cmbNgay.addItem(i);
        }
        for (int i = 1; i <= 12; i++) {
            cmbThang.addItem(i);
        }
        int year = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = year; i >= year - 10; i--) {
            cmbNamNgay.addItem(i);
        }

        selectorPanel.add(new JLabel("Ngày:"));
        selectorPanel.add(cmbNgay);
        selectorPanel.add(new JLabel("Tháng:"));
        selectorPanel.add(cmbThang);
        selectorPanel.add(new JLabel("Năm:"));
        selectorPanel.add(cmbNamNgay);

        lblDoanhThuNgay = new JLabel("", JLabel.CENTER);
        lblDoanhThuNgay.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblDoanhThuNgay.setForeground(Color.BLACK);
        lblDoanhThuNgay.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblDoanhThuNgay.setPreferredSize(new Dimension(900, 200));
        lblDoanhThuNgay.setMaximumSize(new Dimension(900, 200));

        pnThongKeTheoNgay.add(Box.createRigidArea(new Dimension(0, 40)));
        pnThongKeTheoNgay.add(lblTitle);
        pnThongKeTheoNgay.add(Box.createRigidArea(new Dimension(0, 30)));
        pnThongKeTheoNgay.add(selectorPanel);
        pnThongKeTheoNgay.add(Box.createRigidArea(new Dimension(0, 20)));
        pnThongKeTheoNgay.add(lblDoanhThuNgay);

        ActionListener thongKeNgayListener = e -> loadThongKeTheoNgay();
        cmbNgay.addActionListener(thongKeNgayListener);
        cmbThang.addActionListener(thongKeNgayListener);
        cmbNamNgay.addActionListener(thongKeNgayListener);

        cmbNgay.setSelectedIndex(0);
        cmbThang.setSelectedIndex(0);
        cmbNamNgay.setSelectedIndex(0);
    }

    private void createThongKeKhoangNgayPanel() {
        pnThongKeKhoangNgay = new JPanel();
        pnThongKeKhoangNgay.setLayout(new BoxLayout(pnThongKeKhoangNgay, BoxLayout.Y_AXIS));
        pnThongKeKhoangNgay.setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel("THỐNG KÊ DOANH THU TỪ NGÀY ĐẾN NGÀY", JLabel.CENTER);
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 28));
        lblTitle.setForeground(Color.BLACK);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel selectorPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        selectorPanel.setOpaque(false);

        cmbTuNgay = new JComboBox<>();
        cmbTuThang = new JComboBox<>();
        cmbTuNam = new JComboBox<>();
        cmbDenNgay = new JComboBox<>();
        cmbDenThang = new JComboBox<>();
        cmbDenNam = new JComboBox<>();

        for (int i = 1; i <= 31; i++) {
            cmbTuNgay.addItem(i);
            cmbDenNgay.addItem(i);
        }
        for (int i = 1; i <= 12; i++) {
            cmbTuThang.addItem(i);
            cmbDenThang.addItem(i);
        }
        int year = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = year; i >= year - 10; i--) {
            cmbTuNam.addItem(i);
            cmbDenNam.addItem(i);
        }

        selectorPanel.add(new JLabel("Từ ngày:"));
        selectorPanel.add(cmbTuNgay);
        selectorPanel.add(new JLabel("Tháng:"));
        selectorPanel.add(cmbTuThang);
        selectorPanel.add(new JLabel("Năm:"));
        selectorPanel.add(cmbTuNam);

        selectorPanel.add(new JLabel("→ Đến ngày:"));
        selectorPanel.add(cmbDenNgay);
        selectorPanel.add(new JLabel("Tháng:"));
        selectorPanel.add(cmbDenThang);
        selectorPanel.add(new JLabel("Năm:"));
        selectorPanel.add(cmbDenNam);

        lblDoanhThuKhoangNgay = new JLabel("", JLabel.CENTER);
        lblDoanhThuKhoangNgay.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblDoanhThuKhoangNgay.setForeground(Color.BLACK);
        lblDoanhThuKhoangNgay.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblDoanhThuKhoangNgay.setPreferredSize(new Dimension(900, 200));

        pnThongKeKhoangNgay.add(Box.createRigidArea(new Dimension(0, 40)));
        pnThongKeKhoangNgay.add(lblTitle);
        pnThongKeKhoangNgay.add(Box.createRigidArea(new Dimension(0, 30)));
        pnThongKeKhoangNgay.add(selectorPanel);
        pnThongKeKhoangNgay.add(Box.createRigidArea(new Dimension(0, 20)));
        pnThongKeKhoangNgay.add(lblDoanhThuKhoangNgay);

        ActionListener thongKeKhoangNgayListener = e -> loadThongKeKhoangNgay();
        cmbTuNgay.addActionListener(thongKeKhoangNgayListener);
        cmbTuThang.addActionListener(thongKeKhoangNgayListener);
        cmbTuNam.addActionListener(thongKeKhoangNgayListener);
        cmbDenNgay.addActionListener(thongKeKhoangNgayListener);
        cmbDenThang.addActionListener(thongKeKhoangNgayListener);
        cmbDenNam.addActionListener(thongKeKhoangNgayListener);

        cmbTuNgay.setSelectedIndex(0);
        cmbTuThang.setSelectedIndex(0);
        cmbTuNam.setSelectedIndex(0);
        cmbDenNgay.setSelectedIndex(0);
        cmbDenThang.setSelectedIndex(0);
        cmbDenNam.setSelectedIndex(0);
    }

    private void loadThongKeKhoangNgay() {
        int ngay1 = (int) cmbTuNgay.getSelectedItem();
        int thang1 = (int) cmbTuThang.getSelectedItem();
        int nam1 = (int) cmbTuNam.getSelectedItem();
        int ngay2 = (int) cmbDenNgay.getSelectedItem();
        int thang2 = (int) cmbDenThang.getSelectedItem();
        int nam2 = (int) cmbDenNam.getSelectedItem();

        String tuNgay = String.format("%04d-%02d-%02d", nam1, thang1, ngay1);
        String denNgay = String.format("%04d-%02d-%02d", nam2, thang2, ngay2);

        double doanhThu = thongKeBus.getDoanhThuKhoangNgay(tuNgay, denNgay);
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        lblDoanhThuKhoangNgay.setText("Doanh thu từ ngày " + ngay1 + "/" + thang1 + "/" + nam1 + " đến " + ngay2 + "/" + thang2 + "/" + nam2 + ": " + format.format(doanhThu));
    }

    private void loadThongKeTheoNgay() {
        int ngay = (int) cmbNgay.getSelectedItem();
        int thang = (int) cmbThang.getSelectedItem();
        int nam = (int) cmbNamNgay.getSelectedItem();
        double doanhThu = thongKeBus.getDoanhThuNgay(ngay, thang, nam);
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        lblDoanhThuNgay.setText("Doanh thu ngày " + ngay + "/" + thang + "/" + nam + ": " + format.format(doanhThu));
    }

    private JPanel createThongKePanel(String backgroundName, JLabel lbl) {
        JPanel panel = new JPanel();
        panel.setLayout(new OverlayLayout(panel));
        panel.setBackground(colorPanel);

        JLabel lblBackground = createImageLabel("image/ManagerUI/thongKe" + backgroundName + ".png", 200, 500);
        Font font = new Font("Tahoma", Font.BOLD, 30);
        lbl.setFont(font);

        panel.add(lbl);
        panel.add(lblBackground);

        lblBackground.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        return panel;
    }

    private void createThongKeTheoQuyPanel() {
        pnThongKeTheoQuy = new JPanel();
        pnThongKeTheoQuy.setLayout(new BoxLayout(pnThongKeTheoQuy, BoxLayout.Y_AXIS));
        pnThongKeTheoQuy.setBackground(colorPanel);

        JLabel lblTitle = new JLabel("THỐNG KÊ DOANH THU THEO QUÝ", JLabel.CENTER);
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 28));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel spacer = new JPanel(); // Spacer 200px
        spacer.setPreferredSize(new Dimension(1, 200));
        spacer.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
        spacer.setOpaque(false);

        JPanel topPanel = new JPanel();
        topPanel.setOpaque(false);
        topPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel lblNam = new JLabel("Năm:");
        lblNam.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblNam.setForeground(Color.WHITE);

        JComboBox<Integer> cmbNam = new JComboBox<>();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = year; i >= year - 10; i--) {
            cmbNam.addItem(i);
        }
        cmbNam.setFont(new Font("Tahoma", Font.PLAIN, 18));

        topPanel.add(lblNam);
        topPanel.add(cmbNam);

        String[] colNames = {" ", "Quý 1", "Quý 2", "Quý 3", "Quý 4"};
        Object[][] data = new Object[2][5];
        JTable table = new JTable(data, colNames);
        table.setRowHeight(40);
        table.setFont(new Font("Tahoma", Font.PLAIN, 18));
        table.setEnabled(false);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(colorPanel);

        cmbNam.addActionListener(e -> {
            int nam = (int) cmbNam.getSelectedItem();
            double[] doanhThu = new double[4];
            double sum = 0;
            for (int i = 1; i <= 12; ++i) {
                double val = thongKeBus.getDoanhThuThang(i, nam);
                sum += val;
                doanhThu[(i - 1) / 3] += val;
            }
            NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            data[0][0] = "Doanh thu";
            for (int i = 0; i < 4; i++) {
                data[0][i + 1] = format.format(doanhThu[i]);
            }
            data[1][0] = "Tổng cộng";
            for (int i = 1; i <= 3; i++) {
                data[1][i] = "";
            }
            data[1][4] = format.format(sum);
            table.repaint();
        });

        cmbNam.setSelectedIndex(0); // trigger initial

        // Add to main panel
        pnThongKeTheoQuy.add(lblTitle);
        pnThongKeTheoQuy.add(spacer);
        pnThongKeTheoQuy.add(topPanel);
        pnThongKeTheoQuy.add(Box.createRigidArea(new Dimension(1, 30))); // small spacing
        pnThongKeTheoQuy.add(scrollPane);
    }

    private void loadThongKeTheoQuy(int nam) {
        double[] doanhThuTheoQuy = new double[4];
        double sum = 0;
        for (int i = 1; i <= 12; ++i) {
            double val = thongKeBus.getDoanhThuThang(i, nam);
            sum += val;
            doanhThuTheoQuy[(i - 1) / 3] += val;
        }

        Locale locale = new Locale.Builder().setLanguage("vi").setRegion("VN").build();
        NumberFormat format = NumberFormat.getCurrencyInstance(locale);

        lblThongKeQuy1.setText(format.format(doanhThuTheoQuy[0]));
        lblThongKeQuy2.setText(format.format(doanhThuTheoQuy[1]));
        lblThongKeQuy3.setText(format.format(doanhThuTheoQuy[2]));
        lblThongKeQuy4.setText(format.format(doanhThuTheoQuy[3]));
        lblThongKeQuyTong.setText(format.format(sum));
    }

    private void createTopMonAnPanel() {
        pnTopMonAn = new JPanel(new BorderLayout());
        pnTopMonAn.setBackground(colorPanel);

        JLabel lblTitle = new JLabel("TOP MÓN ĂN BÁN CHẠY NHẤT", JLabel.CENTER);
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 28));
        lblTitle.setForeground(Color.WHITE);
        pnTopMonAn.add(lblTitle, BorderLayout.NORTH);

        ThongKeDAO thongKeDAO = new ThongKeDAO();
        ArrayList<MonAnDTO> topMonAnDTOs = thongKeDAO.getTopBanChay();

        String[] colNames = {"Top", "Sản phẩm", "Đã bán"};
        Object[][] data = new Object[topMonAnDTOs.size()][3];

        for (int i = 0; i < topMonAnDTOs.size(); i++) {
            data[i][0] = i + 1;
            data[i][1] = topMonAnDTOs.get(i).getTenSp();
            data[i][2] = topMonAnDTOs.get(i).getGiaSp();
        }

        JTable table = new JTable(data, colNames);
        table.setFont(new Font("Tahoma", Font.PLAIN, 18));
        table.setRowHeight(40);
        table.setEnabled(false);
        table.setForeground(Color.WHITE);
        table.setBackground(colorPanel);
        table.setGridColor(Color.GRAY);

        // Center all columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Header style
        table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 20));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setBackground(new Color(63, 63, 63));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(colorPanel);

        pnTopMonAn.add(scrollPane, BorderLayout.CENTER);
    }

    private void updateTopMonAnPanel() {
        ArrayList<MonAnDTO> topMonAnDTOs = thongKeBus.getTopMonAn();

        if (topMonAnDTOs != null && topMonAnDTOs.size() >= 4) {
            lblTopMonAn1.setText(topMonAnDTOs.get(0).getTenSp());
            lblTopMonAn2.setText(topMonAnDTOs.get(1).getTenSp());
            lblTopMonAn3.setText(topMonAnDTOs.get(2).getTenSp());
            lblTopMonAn4.setText(topMonAnDTOs.get(3).getTenSp());

            lblTopSoLuongMA1.setText(String.valueOf(topMonAnDTOs.get(0).getGiaSp()));
            lblTopSoLuongMA2.setText(String.valueOf(topMonAnDTOs.get(1).getGiaSp()));
            lblTopSoLuongMA3.setText(String.valueOf(topMonAnDTOs.get(2).getGiaSp()));
            lblTopSoLuongMA4.setText(String.valueOf(topMonAnDTOs.get(3).getGiaSp()));
        }

    }

    private void createBieuDoDoanhThuPanel() {
        pnBieuDoDoanhThu = new JPanel(new GridBagLayout()); // Sử dụng GridBagLayout để linh hoạt trong việc đặt các thành phần
        pnBieuDoDoanhThu.setBackground(colorPanel);

        JLabel lblTileBieuDo = new JLabel("BIỂU ĐỒ DOANH THU", JLabel.CENTER);
        lblTileBieuDo.setFont(new Font("Tahoma", Font.BOLD, 28));
        lblTileBieuDo.setForeground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.2;
        gbc.fill = GridBagConstraints.BOTH;

        pnBieuDoDoanhThu.add(lblTileBieuDo, gbc);

        GridBagConstraints gb = new GridBagConstraints();
        gb.gridx = 0;
        gb.gridy = 1;
        gb.weightx = 0.5;
        gb.weighty = 0.5;
        gb.fill = GridBagConstraints.CENTER;

        cmbNamBieuDo = new JComboBox<>();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = year; i >= year - 10; i--) {
            cmbNamBieuDo.addItem(i);
        }
        cmbNamBieuDo.setFont(new Font("Tahoma", Font.PLAIN, 18));
        cmbNamBieuDo.setSize(120, 35);

        pnBieuDoDoanhThu.add(cmbNamBieuDo, gb);

        pnChart = new JPanel();
        chartPanel = new ChartPanel(createChart(cmbNamBieuDo.getSelectedItem().toString()));
        chartPanel.setPreferredSize(new Dimension(900, 280)); // Chiếm 70% chiều cao của panel chính
        pnChart.add(chartPanel);
        GridBagConstraints gbcChart = new GridBagConstraints();
        gbcChart.gridx = 0;
        gbcChart.gridy = 2;
        gbcChart.weightx = 1;
        gbcChart.weighty = 4;
        gbcChart.fill = GridBagConstraints.CENTER;

        pnBieuDoDoanhThu.add(pnChart, gbcChart);

        add(pnBieuDoDoanhThu, BorderLayout.CENTER);

    }

    private void addEvents() {
        thongKeChungItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thongKeChung(pnThongKeChung);
            }
        });
        thongKeTheoNgayItem.addActionListener(e -> {
            if (pnThongKeTheoNgay == null) {
                createThongKeTheoNgayPanel();
            }
            showPanel(pnThongKeTheoNgay);
        });
        thongKeKhoangNgayItem.addActionListener(e -> {
            if (pnThongKeKhoangNgay == null) {
                createThongKeKhoangNgayPanel();
            }
            showPanel(pnThongKeKhoangNgay);
        });
        thongKeTheoQuyItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pnThongKeTheoQuy == null) {
                    updateTopMonAnPanel();
                }
                showPanel(pnThongKeTheoQuy);
            }
        });
        topChuyenBayItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pnTopMonAn == null) {
                    createTopMonAnPanel(); // đảm bảo panel được tạo
                }
                showPanel(pnTopMonAn); // lúc này pnTopChuyenBay không null nữa
            }
        });

        bieuDoDoanhThuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPanel(pnBieuDoDoanhThu);
            }
        });
        cmbNamBieuDo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nam = cmbNamBieuDo.getSelectedItem().toString();
                veLaiChart(nam);
            }
        });

    }

    private void showPanel(JPanel panel) {
        removeAll();
        add(menuBar, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private void thongKeChung(JPanel panel) {
        removeAll();
        add(menuBar, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        ThongKe tke = thongKeBus.thongKe();
        lblThongKeMonAn.setText(dcf.format(tke.getSoLuongMonAn()));
        lblThongKeKhachHang.setText(dcf.format(tke.getSoLuongKH()));
        lblThongKeDoanhThu.setText(dcf.format(tke.getTongDoanhThu()));
        revalidate();
        repaint();
    }

    private JLabel createImageLabel(String imagePath, int x, int y) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(imagePath));
            Image scaledImage = image.getScaledInstance(x, y, Image.SCALE_SMOOTH);
            return new JLabel(new ImageIcon(scaledImage));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new JLabel();
    }

    private void veLaiChart(String nam) {
        pnChart.remove(chartPanel);
        chartPanel = new ChartPanel(createChart(nam));
        chartPanel.setPreferredSize(new Dimension(900, 280));

        pnChart.add(chartPanel);
        pnChart.revalidate();
        pnChart.repaint();
    }

    private JFreeChart createChart(String nam) {
        JFreeChart barChart = ChartFactory.createBarChart(
                "Doanh thu năm " + nam,
                "", "Doanh thu",
                createDataset(nam), PlotOrientation.VERTICAL, false, false, false);
        return barChart;
    }

    private CategoryDataset createDataset(String nam) {
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 1; i <= 12; i++) {
            double value = thongKeBus.getDoanhThuThang(i, Integer.parseInt(nam));
            dataset.addValue(value, "Doanh thu", i + "");
        }
        return dataset;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Quản Lý Thống Kê");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1030, 844); // Kích thước như được sử dụng trong GUI
            frame.setLocationRelativeTo(null); // Căn giữa màn hình
            frame.setResizable(false);

            QuanLyThongKeGUI thongKeGUI = new QuanLyThongKeGUI();
            frame.setContentPane(thongKeGUI);
            frame.setVisible(true);
        });
    }

}
