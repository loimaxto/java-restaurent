/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.example.retaurant.GUI.DatBan;

import com.example.retaurant.GUI.KhachHang.*;
import com.example.retaurant.BUS.CustomerBUS;
import com.example.retaurant.DTO.CustomerDTO;
import com.example.retaurant.GUI.DatBan.DatBanPN;
import com.example.retaurant.MyCustom.MyDialog;
import java.awt.Dialog;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author light
 */
public class KhachHangModal extends javax.swing.JFrame {

    DefaultTableModel model = new DefaultTableModel();
    CustomerBUS busCustomer = new CustomerBUS();
    MyDialog dialog;
    DatBanPN datBanPN;

    public KhachHangModal(DatBanPN datBanPN) {
        this.datBanPN = datBanPN;
        initComponents();
        loadDataTable();

        tableKh.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tableKh.rowAtPoint(e.getPoint());
                if (row >= 0) {
                    System.out.println("Clicked Row Data (Row Index: " + row + "):");
                    for (int i = 0; i < model.getColumnCount(); i++) {
                        Object value = model.getValueAt(row, i);
                        System.out.println(model.getColumnName(i) + ": " + value);
                    }
                    datBanPN.updateNewInsertKhachHanhForHoaDon((int) model.getValueAt(row, 0));
                    closeModal();
                }

            }
        });

        this.pack();
    }

    public void closeModal() {
        this.dispose();
    }

    public void loadSearchDataTable() {
        String keyword = txtFieldSearch.getText();
        List<CustomerDTO> list = busCustomer.getSearchCustomersByPhone(keyword);
        model.setRowCount(0);
        Vector header = new Vector();
        header.add("Id");
        header.add("Họ");
        header.add("Tên");
        header.add("Điện thoại");
        if (model.getRowCount() == 0) {
            model = new DefaultTableModel(header, 0);
        }

        for (CustomerDTO cust : list) {
            Vector row = new Vector();
            row.add(cust.getKhId());
            row.add(cust.getHoKh());
            row.add(cust.getTenKh());
            row.add(cust.getSdt());
            model.addRow(row);
        }
        tableKh.setModel(model);
    }

    public void loadDataTable() {
        model.setRowCount(0);
        Vector header = new Vector();
        header.add("Id");
        header.add("Họ");
        header.add("Tên");
        header.add("Điện thoại");
        if (model.getRowCount() == 0) {
            model = new DefaultTableModel(header, 0);
        }
        List<CustomerDTO> list = busCustomer.getAllCustomer();
        for (CustomerDTO cust : list) {
            Vector row = new Vector();
            row.add(cust.getKhId());
            row.add(cust.getHoKh());
            row.add(cust.getTenKh());
            row.add(cust.getSdt());
            model.addRow(row);
        }
        tableKh.setModel(model);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtFieldSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableKh = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Thêm khách hàng cho hóa đơn");
        setAlwaysOnTop(true);
        setLocationByPlatform(true);

        jPanel2.setMaximumSize(new java.awt.Dimension(100, 350));
        jPanel2.setPreferredSize(new java.awt.Dimension(200, 258));
        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.Y_AXIS));

        jPanel1.setMinimumSize(new java.awt.Dimension(100, 150));
        jPanel1.setPreferredSize(new java.awt.Dimension(100, 150));

        jLabel1.setText("Nhập số điện thoại");
        jPanel1.add(jLabel1);

        txtFieldSearch.setMinimumSize(new java.awt.Dimension(100, 26));
        txtFieldSearch.setPreferredSize(new java.awt.Dimension(150, 26));
        jPanel1.add(txtFieldSearch);

        btnSearch.setText("Tìm");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });
        jPanel1.add(btnSearch);

        jPanel2.add(jPanel1);

        jScrollPane1.setMinimumSize(new java.awt.Dimension(22, 200));

        tableKh.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Họ", "Tên", "Điện thoại"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableKh);
        if (tableKh.getColumnModel().getColumnCount() > 0) {
            tableKh.getColumnModel().getColumn(0).setResizable(false);
            tableKh.getColumnModel().getColumn(1).setResizable(false);
            tableKh.getColumnModel().getColumn(2).setResizable(false);
            tableKh.getColumnModel().getColumn(3).setResizable(false);
        }

        jPanel2.add(jScrollPane1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        loadSearchDataTable();
    }//GEN-LAST:event_btnSearchActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSearch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableKh;
    private javax.swing.JTextField txtFieldSearch;
    // End of variables declaration//GEN-END:variables
}
