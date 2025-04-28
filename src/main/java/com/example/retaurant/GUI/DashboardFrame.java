package com.example.retaurant.GUI;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */


import javax.swing.*;
import com.example.retaurant.DTO.UserDTO;
import com.example.retaurant.GUI.CongThuc.CongThucPannel;
import com.example.retaurant.GUI.DatBan.DatBanPN;
import com.example.retaurant.GUI.HoaDon.HoaDonPN;
import com.example.retaurant.GUI.KhachHang.KhachHangPanel;
import com.example.retaurant.GUI.KhuyenMai.KhuyenMaiPN;
import com.example.retaurant.GUI.MonAn.MonAnPannel;
import com.example.retaurant.GUI.NguyenLieu.NguyenLieuGUI;
import com.example.retaurant.GUI.NhaCungCap.NhaCungCapPanel;
import com.example.retaurant.GUI.NhanVien.QuanLyNhanVienGUI;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author truon
 */
public class DashboardFrame extends javax.swing.JFrame {

    private UserDTO user;

    //khai bao cai panel se tao
    private DatBanPN datBanPN = new DatBanPN();
    private KhuyenMaiPN khuyenMaiPN = new KhuyenMaiPN();
    private KhachHangPanel khachHangPanel = new KhachHangPanel();
    private MonAnPannel monAnPN = new MonAnPannel();
    private HoaDonPN hoaDonPN = new HoaDonPN();
    private NguyenLieuGUI nguyenLieuGUI = new NguyenLieuGUI();
    private CongThucPannel congThucPannel= new CongThucPannel();
    private NhaCungCapPanel nhaCungCapPanel = new NhaCungCapPanel();
    private QuanLyNhanVienGUI nhanvienPanel = new QuanLyNhanVienGUI();
    public DashboardFrame() {
        initComponents();
        currentPanel.removeAll();

        cardLayout = new CardLayout();
        currentPanel.setLayout(cardLayout);

        // add panel moi cho phần chuyển trang
        currentPanel.add(datBanPN, btnDatBan.getActionCommand());
        currentPanel.add(khuyenMaiPN, btnKhuyenMai.getActionCommand());
        currentPanel.add(khachHangPanel, btnKhachHang.getActionCommand());
        currentPanel.add(monAnPN, btnMonAn.getActionCommand());
        currentPanel.add(hoaDonPN, btnHoaDon.getActionCommand());
        currentPanel.add(nguyenLieuGUI, btnNguyenLieu.getActionCommand());
        currentPanel.add(congThucPannel, "Cong thuc");
        currentPanel.add(nhaCungCapPanel, btnCungCap.getActionCommand());
        currentPanel.add(nhanvienPanel, btnNhanVien.getActionCommand());
        
        buttonGroup.add(btnCungCap);
        buttonGroup.add(btnDangXuat);
        buttonGroup.add(btnDatBan);
        buttonGroup.add(btnHoaDon);
        buttonGroup.add(btnKhachHang);
        buttonGroup.add(btnKhuyenMai);
        buttonGroup.add(btnMonAn);
        buttonGroup.add(btnNguyenLieu);
        buttonGroup.add(btnNhanVien);
        buttonGroup.add(btnTaiKhoan);
        buttonGroup.add(btnThongKe);

        //them sự kiện chuyển trang
        btnDatBan.setSelected(true);
        btnDatBan.addActionListener(e -> switchPanel(btnDatBan.getActionCommand()));
        btnKhachHang.addActionListener(e -> switchPanel(btnKhachHang.getActionCommand()));
        btnMonAn.addActionListener(e -> switchPanel(btnMonAn.getActionCommand()));
        btnNguyenLieu.addActionListener(e -> switchPanel(btnNguyenLieu.getActionCommand()));
        btnCungCap.addActionListener(e -> switchPanel(btnCungCap.getActionCommand()));
        btnHoaDon.addActionListener(e ->{ 
         hoaDonPN.reloadDataPanel();
         switchPanel(btnHoaDon.getActionCommand());
        });
//         btnCungCap.addActionListener(e -> switchPanel(btnCungCap.getActionCommand()));
        btnNhanVien.addActionListener(e -> switchPanel(btnNhanVien.getActionCommand()));
        btnDangXuat.addActionListener(e -> handleLogout());
        navBtnUserInfo.addActionListener(e -> switchPanel("InfoPanel"));

        cardLayout.show(currentPanel, btnDatBan.getActionCommand());

        btnKhuyenMai.addActionListener(e -> switchPanel(btnKhuyenMai.getActionCommand()));   
        monAnPN.getjButton5().addActionListener(e ->{
            cardLayout.show(currentPanel, "Cong thuc");

        });
        updateNavigateButton();
    }

    public DashboardFrame(UserDTO user) {
        this();
        this.user = user;
        if (user != null) {
            navBtnUserInfo.setText(user.getUserName());
        }
    }

    private void switchPanel(String panelName) {
        updateNavigateButton();
        cardLayout.show(currentPanel, panelName);
    }

    private void handleLogout() {
        this.dispose();
        //        new AuthFrame().setVisible(true);
    }

    private void updateNavigateButton() {
        ArrayList<Component> components = new ArrayList<>(Arrays.asList(navigationPanel.getComponents()));
        components.add(navBtnUserInfo);

        for (Component component : components) {
            if (component instanceof JToggleButton) {
                JToggleButton button = (JToggleButton) component;
                if (button.isSelected()) {
                    button.setBackground(Color.BLUE);
                    button.setForeground(Color.WHITE);
                } else {
                    button.setBackground(Color.WHITE);
                    button.setForeground(Color.BLACK);
                }
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")

    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup = new javax.swing.ButtonGroup();
        cardLayout = new java.awt.CardLayout();
        sidePanel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        navBtnUserInfo = new javax.swing.JToggleButton();
        navigationPanel = new javax.swing.JPanel();
        btnDatBan = new javax.swing.JToggleButton();
        btnKhachHang = new javax.swing.JToggleButton();
        btnMonAn = new javax.swing.JToggleButton();
        btnNguyenLieu = new javax.swing.JToggleButton();
        btnCungCap = new javax.swing.JToggleButton();
        btnNhanVien = new javax.swing.JToggleButton();
        btnHoaDon = new javax.swing.JToggleButton();
        btnThongKe = new javax.swing.JToggleButton();
        btnKhuyenMai = new javax.swing.JToggleButton();
        btnTaiKhoan = new javax.swing.JToggleButton();
        btnDangXuat = new javax.swing.JToggleButton();
        currentPanel = new javax.swing.JPanel();
        examplePanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        sidePanel.setBackground(new java.awt.Color(255, 102, 255));
        sidePanel.setPreferredSize(new java.awt.Dimension(160, 700));
        sidePanel.setLayout(new java.awt.BorderLayout());

        jPanel4.setMaximumSize(new java.awt.Dimension(160, 180));
        jPanel4.setPreferredSize(new java.awt.Dimension(160, 180));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("SGU");

        navBtnUserInfo.setBackground(new java.awt.Color(60, 63, 65));
        navBtnUserInfo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        navBtnUserInfo.setText("Your User Name");
        navBtnUserInfo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        navBtnUserInfo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(navBtnUserInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(navBtnUserInfo, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                .addContainerGap())
        );

        sidePanel.add(jPanel4, java.awt.BorderLayout.NORTH);

        navigationPanel.setPreferredSize(new java.awt.Dimension(160, 600));
        navigationPanel.setLayout(new java.awt.GridLayout(11, 1));

        btnDatBan.setText("Đặt bàn");
        navigationPanel.add(btnDatBan);

        btnKhachHang.setText("Khách hàng");
        navigationPanel.add(btnKhachHang);

        btnMonAn.setText("Món ăn");
        navigationPanel.add(btnMonAn);

        btnNguyenLieu.setText("Nguyên liệu");
        navigationPanel.add(btnNguyenLieu);

        btnCungCap.setText("Nhà cung cấp");
        navigationPanel.add(btnCungCap);

        btnNhanVien.setText("Nhân viên");
        navigationPanel.add(btnNhanVien);

        btnHoaDon.setText("Hóa đơn");
        btnHoaDon.setToolTipText("");
        navigationPanel.add(btnHoaDon);

        btnThongKe.setText("Thống kê");
        btnThongKe.setToolTipText("");
        navigationPanel.add(btnThongKe);

        btnKhuyenMai.setText("Khuyến mãi");
        btnKhuyenMai.setToolTipText("");
        navigationPanel.add(btnKhuyenMai);

        btnTaiKhoan.setText("Tài Khoản");
        btnTaiKhoan.setToolTipText("");
        navigationPanel.add(btnTaiKhoan);

        btnDangXuat.setText("Đăng xuất");
        btnDangXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangXuatActionPerformed(evt);
            }
        });
        navigationPanel.add(btnDangXuat);

        sidePanel.add(navigationPanel, java.awt.BorderLayout.WEST);

        getContentPane().add(sidePanel, java.awt.BorderLayout.WEST);

        currentPanel.setBackground(new java.awt.Color(255, 153, 153));
        currentPanel.setLayout(new java.awt.CardLayout());

        jLabel7.setText(" Example Panel");

        javax.swing.GroupLayout examplePanel1Layout = new javax.swing.GroupLayout(examplePanel1);
        examplePanel1.setLayout(examplePanel1Layout);
        examplePanel1Layout.setHorizontalGroup(
            examplePanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(examplePanel1Layout.createSequentialGroup()
                .addGap(297, 297, 297)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(649, Short.MAX_VALUE))
        );
        examplePanel1Layout.setVerticalGroup(
            examplePanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(examplePanel1Layout.createSequentialGroup()
                .addGap(296, 296, 296)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(320, Short.MAX_VALUE))
        );

        currentPanel.add(examplePanel1, "card3");

        getContentPane().add(currentPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

     private void btnDangXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangXuatActionPerformed
         // TODO add your handling code here:
     }//GEN-LAST:event_btnDangXuatActionPerformed

     private void navBtnThiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_navBtnThiActionPerformed
         // TODO add your handling code here:
     }// GEN-LAST:event_navBtnThiActionPerformed

     /**
      * @param args the command line arguments
      */
     public static void main(String args[]) {
         /* Set the Nimbus look and feel */
         // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
         // (optional) ">
         /*
          * If Nimbus (introduced in Java SE 6) is not available, stay with the default
          * look and feel.
          * For details see
          * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
          */
         try {
             for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
                     .getInstalledLookAndFeels()) {
                 if ("Nimbus".equals(info.getName())) {
                     javax.swing.UIManager.setLookAndFeel(info.getClassName());
                     break;
                 }
             }
         } catch (ClassNotFoundException ex) {
             java.util.logging.Logger.getLogger(DashboardFrame.class.getName()).log(
                     java.util.logging.Level.SEVERE,
                     null, ex);
         } catch (InstantiationException ex) {
             java.util.logging.Logger.getLogger(DashboardFrame.class.getName()).log(
                     java.util.logging.Level.SEVERE,
                     null, ex);
         } catch (IllegalAccessException ex) {
             java.util.logging.Logger.getLogger(DashboardFrame.class.getName()).log(
                     java.util.logging.Level.SEVERE,
                     null, ex);
         } catch (javax.swing.UnsupportedLookAndFeelException ex) {
             java.util.logging.Logger.getLogger(DashboardFrame.class.getName()).log(
                     java.util.logging.Level.SEVERE,
                     null, ex);
         }
         // </editor-fold>

         /* Create and display the form */
         java.awt.EventQueue.invokeLater(new Runnable() {
             public void run() {
                 new DashboardFrame().setVisible(true);
             }
         });
     }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnCungCap;
    private javax.swing.JToggleButton btnDangXuat;
    private javax.swing.JToggleButton btnDatBan;
    private javax.swing.JToggleButton btnHoaDon;
    private javax.swing.JToggleButton btnKhachHang;
    private javax.swing.JToggleButton btnKhuyenMai;
    private javax.swing.JToggleButton btnMonAn;
    private javax.swing.JToggleButton btnNguyenLieu;
    private javax.swing.JToggleButton btnNhanVien;
    private javax.swing.JToggleButton btnTaiKhoan;
    private javax.swing.JToggleButton btnThongKe;
    private javax.swing.ButtonGroup buttonGroup;
    private java.awt.CardLayout cardLayout;
    private javax.swing.JPanel currentPanel;
    private javax.swing.JPanel examplePanel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JToggleButton navBtnUserInfo;
    private javax.swing.JPanel navigationPanel;
    private javax.swing.JPanel sidePanel;
    // End of variables declaration//GEN-END:variables
}
