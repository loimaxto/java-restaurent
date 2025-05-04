/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.example.retaurant.GUI.HoaDon;

import com.example.retaurant.BUS.HoaDonBUS;
import com.example.retaurant.DTO.HoaDonDTO2;
import com.example.retaurant.DTO.SearchCriteria;
import javax.swing.*;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Locale;
import javax.swing.table.TableColumn;

/**
 *
 * @author light
 */public class HoaDonPN extends javax.swing.JPanel {

    /**
     * Creates new form HoaDonPN
     */
    private HoaDonTableModel tableModel;
     public static final String AND = "Thoải tất cả điều kiện";
     public static final String OR = "Thoải một trong các điều kiện";
    static private HoaDonBUS busHoaDon;

    public HoaDonPN() {
        busHoaDon = new HoaDonBUS();
        initComponents();

        tableModel = new HoaDonTableModel();
        tableHoaDon.setModel(tableModel);
        tableHoaDon.setRowHeight(40);
        tableHoaDon.setCellSelectionEnabled(false);
        tableHoaDon.setRowSelectionAllowed(false);
        tableHoaDon.setShowVerticalLines(false);
        TableColumn column = tableHoaDon.getColumnModel().getColumn(4);
        column.setCellEditor(new HoaDonTableCellEditor(tableHoaDon, tableModel, this));
        column.setCellRenderer(new HoaDonTableCellRender());
        reloadDataPanel();
        dateStart.setLocale(new Locale("vi","VN"));
        dateEnd.setLocale(new Locale("vi","VN"));
    }
    public void reloadDataPanel() {
        renderDataForTable(getHoaDonData());
        String[] logicOptions = {AND, OR};
        comboboxDieuKien.removeAllItems();
        for (String option : logicOptions) {
        comboboxDieuKien.addItem(option);
        }
        
    }
    public List<HoaDonDTO2> getHoaDonData() {
        return busHoaDon.getBills();
    }
//    private List<HoaDonDTO2> getHoaDonData(String test) {
//        
//    }
    public void renderDataForTable(List<HoaDonDTO2> data) {
        tableModel.resetData();
        for (HoaDonDTO2 item : data) {
            tableModel.addRow(item);
        }
    }

     public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Panel Runner");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            HoaDonPN panel = new HoaDonPN();
            frame.getContentPane().add(panel);

            frame.setSize(1200, 800);
            frame.setLocationRelativeTo(null); // Center the frame
            frame.setVisible(true);
        });
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        textCreator = new javax.swing.JTextField();
        textCustomer = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        btnSearch = new javax.swing.JButton();
        comboboxDieuKien = new javax.swing.JComboBox<>();
        dateStart = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        dateEnd = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableHoaDon = new javax.swing.JTable();

        jLabel1.setText("Danh sách hóa đơn");

        jLabel2.setText("Người Tạo");

        jLabel3.setText("Khách Hàng");

        textCreator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textCreatorActionPerformed(evt);
            }
        });

        jLabel5.setText("Đến Ngày");

        btnSearch.setText("Tìm");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        comboboxDieuKien.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Thỏa tất  cả điều kiện", "Thỏa một điều kiện" }));
        comboboxDieuKien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboboxDieuKienActionPerformed(evt);
            }
        });

        dateStart.setDateFormatString("dd/M/yyyy");

        jLabel4.setText("Từ Ngày");

        dateEnd.setDateFormatString("dd/M/yyyy");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(textCustomer, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                            .addComponent(textCreator)))
                    .addComponent(comboboxDieuKien, 0, 222, Short.MAX_VALUE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4))
                        .addGap(48, 48, 48)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dateStart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dateEnd, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(btnSearch)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(textCreator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4))
                    .addComponent(dateStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(textCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel5)
                    .addComponent(dateEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboboxDieuKien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch)))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(573, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(74, 74, 74))
        );

        tableHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tableHoaDon);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 741, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void textCreatorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textCreatorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textCreatorActionPerformed

    private void comboboxDieuKienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboboxDieuKienActionPerformed
        // TODO add your handling code here:
      

    }//GEN-LAST:event_comboboxDieuKienActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        String logicChon = (String) comboboxDieuKien.getSelectedItem();
        String textCreator = this.textCreator.getText();
        String textCustomer = this.textCustomer.getText();
        Date startDate = null;
        Date endDate = null;
        if ( this.dateStart.getDate() !=null) {
            startDate =new java.sql.Date(this.dateStart.getDate().getTime());
        }
        if ( this.dateEnd.getDate() !=null) {
            endDate =new java.sql.Date(this.dateEnd.getDate().getTime());
        }
        SearchCriteria searchCriteriaHoaDon = new SearchCriteria(logicChon,textCreator,textCustomer,startDate,endDate);
        List<HoaDonDTO2> listHoaDonDTO2 = new ArrayList();
        
        switch (logicChon) {
            case AND:
                searchCriteriaHoaDon.setLogicChon("AND");
                break;
            case OR:
                searchCriteriaHoaDon.setLogicChon("OR");
                break;
        }
        listHoaDonDTO2 = busHoaDon.searchHoaDon(searchCriteriaHoaDon);
        if (listHoaDonDTO2 == null ) {
            return;
        }
        renderDataForTable(listHoaDonDTO2);
    }//GEN-LAST:event_btnSearchActionPerformed
    /*public List<HoaDonDTO2> timKiemAND() {
        List<HoaDon> ketQua = new ArrayList<>();
        for (HoaDon hoaDon : danhSachHoaDon) {
            if () {
                ketQua.add(hoaDon);
            }
        }
        return ketQua;
    }
    public List<HoaDonDTO2> timKiemOR() {
        List<HoaDon> ketQua = new ArrayList<>();
        for (HoaDon hoaDon : danhSachHoaDon) {
            if () {
                ketQua.add(hoaDon);
            }
        }
        return ketQua;
    }
    public List<HoaDonDTO2> timKiemORAND() {
        
        return ketQua;
    }
    */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox<String> comboboxDieuKien;
    private com.toedter.calendar.JDateChooser dateEnd;
    private com.toedter.calendar.JDateChooser dateStart;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableHoaDon;
    private javax.swing.JTextField textCreator;
    private javax.swing.JTextField textCustomer;
    // End of variables declaration//GEN-END:variables
}
