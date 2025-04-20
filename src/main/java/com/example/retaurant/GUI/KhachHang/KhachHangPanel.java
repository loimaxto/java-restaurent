/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.example.retaurant.GUI.KhachHang;

import com.example.retaurant.BUS.CustomerBUS;
import com.example.retaurant.DTO.CustomerDTO;
import com.example.retaurant.GUI.DatBan.DatBanPN;
import com.example.retaurant.MyCustom.MyDialog;
import java.awt.Dialog;
import java.util.List;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author light
 */
public class KhachHangPanel extends javax.swing.JPanel {

    DefaultTableModel model = new DefaultTableModel();
    CustomerBUS busCustomer = new CustomerBUS();
    MyDialog dialog;

    public KhachHangPanel() {
        initComponents();

        loadDataTable();
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtFieldSearch = new javax.swing.JTextField();
        btnXoa = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableKh = new javax.swing.JTable();

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel1.setMinimumSize(new java.awt.Dimension(100, 150));
        jPanel1.setPreferredSize(new java.awt.Dimension(100, 150));

        jLabel1.setText("Tìm kiếm số điện thoại");

        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnSearch.setText("Tìm");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtFieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(btnSearch))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(414, 414, 414)
                        .addComponent(btnThem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSua)
                        .addGap(18, 18, 18)
                        .addComponent(btnXoa)))
                .addContainerGap(48, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtFieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSua)
                    .addComponent(btnThem)
                    .addComponent(btnXoa))
                .addGap(15, 15, 15))
        );

        add(jPanel1);

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

        add(jScrollPane1);
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        AddKhachHangPanel addPanel = new AddKhachHangPanel();
        addPanel.setLocationRelativeTo(this); // Center relative to the parent
        addPanel.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Crucial line
        addPanel.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        addPanel.setKhPanel(this);
        addPanel.setVisible(true);
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        int selectedRow = tableKh.getSelectedRow();
        if (selectedRow != -1) {
            System.out.println("Selected row: " + selectedRow);
            int id = (int) tableKh.getValueAt(selectedRow, 0);
            String hoKh = (String) tableKh.getValueAt(selectedRow, 1);
            String tenKh = (String) tableKh.getValueAt(selectedRow, 2);
            String sdt = (String) tableKh.getValueAt(selectedRow, 3);
            CustomerDTO cust = new CustomerDTO(id, sdt, tenKh, hoKh);
            SuaKhPanel suaPn = new SuaKhPanel(cust);
            suaPn.setLocationRelativeTo(this); // Center relative to the parent
            suaPn.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Crucial line
            suaPn.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);

            suaPn.setKhPanel(this);
            suaPn.setVisible(true);
            loadDataTable();
        } else {
            String errorMesage = "Chưa chọn hàng để sửa!";
            dialog = new MyDialog(errorMesage, 0);
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        int selectedRow = tableKh.getSelectedRow();
        if (selectedRow != -1) {
            System.out.println("Selected row: " + selectedRow);
            int id = (int) tableKh.getValueAt(selectedRow, 0);
            busCustomer.delateCustomer(id);
            loadDataTable();
        } else {
            String errorMesage = "Chưa chọn hàng để xóa!";
            dialog = new MyDialog(errorMesage, 0);
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        loadSearchDataTable();
    }//GEN-LAST:event_btnSearchActionPerformed

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Panel Runner");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            KhachHangPanel panel = new KhachHangPanel();
            frame.getContentPane().add(panel);

            frame.setSize(1200, 800);
            frame.setLocationRelativeTo(null); // Center the frame
            frame.setVisible(true);
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableKh;
    private javax.swing.JTextField txtFieldSearch;
    // End of variables declaration//GEN-END:variables
}
