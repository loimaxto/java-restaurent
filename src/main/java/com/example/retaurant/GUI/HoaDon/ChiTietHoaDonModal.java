/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.example.retaurant.GUI.HoaDon;

import com.example.retaurant.BUS.BanBUS;
import com.example.retaurant.BUS.CtHoaDonBUS;
import com.example.retaurant.BUS.HoaDonBUS;
import com.example.retaurant.DTO.BanDTO;
import com.example.retaurant.DTO.CtSanPhamThanhToanDTO;
import com.example.retaurant.DTO.HoaDonDTO2;
import com.example.retaurant.GUI.DatBan.DatBanPN;
import com.example.retaurant.MyCustom.MyDialog;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author light
 */
public class ChiTietHoaDonModal extends javax.swing.JFrame {

    /**
     * Creates new form AddKhachHangPanel
     */
    private DatBanPN datBanPN;
    private HoaDonDTO2 hdDto;
    private CtHoaDonBUS busCtHoaDonBUS;
    private ArrayList<CtSanPhamThanhToanDTO> ctSpList;
    private boolean isThanhToan ;
    private int tongTienHoaDon = 0;
    
    public ChiTietHoaDonModal(HoaDonDTO2 hddto, boolean isThanhToan) {
        busCtHoaDonBUS = new CtHoaDonBUS();
        this.ctSpList = (ArrayList<CtSanPhamThanhToanDTO>) busCtHoaDonBUS.getCtSanPhanThanhToanByHdId(hddto.getHdId());
        this.hdDto = hddto;
        this.isThanhToan = isThanhToan;
        initComponents();
        labelHoTenKh.setText(hdDto.getHoKh() + hdDto.getTenKh());
        labelNvTen.setText(hdDto.getHoTenNv());
        labelSdtKh.setText(hdDto.getSdt());
        labelTongTien.setText(hdDto.getTongGia() == null ? "Chưa thanh toán" : String.valueOf(hdDto.getTongGia()));
        renderChiTietSpHoaDon();

        btnXacNhan.setVisible(isThanhToan);
        if (isThanhToan == true) {
            tongTienHoaDon = 0;
            for (CtSanPhamThanhToanDTO item : ctSpList) {
                tongTienHoaDon += item.getTongTienCt();
            }
            labelTongTien.setText(String.valueOf(tongTienHoaDon));
        }
    }
    public void setDatBanPn(DatBanPN dbpn) {
        this.datBanPN = dbpn;
    }
    
    private void renderChiTietSpHoaDon() {
        String[] columnNames = {"Tên sản phẩm", "Số lượng", "Giá", "Tổng"};

        List<Object[]> data = new ArrayList<>();
        for (CtSanPhamThanhToanDTO item : ctSpList) {
            data.add(new Object[]{item.getTenSp(), item.getSoLuong(), item.getGiaTaiLucDat(), item.getTongTienCt()});
        }
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < tableSanPhamItem.getColumnCount(); i++) {
            tableSanPhamItem.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        DefaultTableModel model = new DefaultTableModel(convertListToArray(data), columnNames);
        tableSanPhamItem.setModel(model);
    }

    private static Object[][] convertListToArray(List<Object[]> list) {
        Object[][] array = new Object[list.size()][];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelForm = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnHuy = new javax.swing.JButton();
        btnXacNhan = new javax.swing.JButton();
        labelHoTenKh = new javax.swing.JLabel();
        labelSdtKh = new javax.swing.JLabel();
        labelTongTien = new javax.swing.JLabel();
        labelNvTen = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableSanPhamItem = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        labelForm.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        labelForm.setText("Chi tiết hóa đơn");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Số điện thoại:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Người lập");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Họ tên khách hàng:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Tổng tiền");

        btnHuy.setText("Hủy");
        btnHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyActionPerformed(evt);
            }
        });

        btnXacNhan.setText("Xác  nhận");
        btnXacNhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXacNhanActionPerformed(evt);
            }
        });

        labelHoTenKh.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        labelHoTenKh.setText("adsf");
        labelHoTenKh.setToolTipText("");

        labelSdtKh.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        labelSdtKh.setText(" ");

        labelTongTien.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        labelTongTien.setText("adsf");
        labelTongTien.setToolTipText("");

        labelNvTen.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        labelNvTen.setText(" ");

        tableSanPhamItem.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tableSanPhamItem);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelNvTen, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelSdtKh, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelHoTenKh, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(93, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnXacNhan)
                        .addGap(39, 39, 39)
                        .addComponent(btnHuy)
                        .addGap(102, 102, 102))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(labelForm, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(142, 142, 142))))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(labelForm)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(labelHoTenKh))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(labelSdtKh))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(labelTongTien))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelNvTen)
                    .addComponent(jLabel3))
                .addGap(26, 26, 26)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHuy)
                    .addComponent(btnXacNhan))
                .addGap(35, 35, 35))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        dispose();
    }//GEN-LAST:event_btnHuyActionPerformed

    private void btnXacNhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXacNhanActionPerformed
        HoaDonBUS busHoaDon = new HoaDonBUS();
        hdDto.setTongGia(tongTienHoaDon);
        if (busHoaDon.updateBill(hdDto)) {
            new MyDialog("Thanh toán thành công !", 0);
            BanBUS busBan = new BanBUS();
            BanDTO currentBanDTO = datBanPN.getCurrentBanDTO();
            busBan.updateBanDangDuocDat(currentBanDTO, currentBanDTO.getIdHoaDonHienTai());
            datBanPN.resetCurrentHoaDonAndBanAndTable();
            dispose();
        }
        
    }//GEN-LAST:event_btnXacNhanActionPerformed
    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnXacNhan;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelForm;
    private javax.swing.JLabel labelHoTenKh;
    private javax.swing.JLabel labelNvTen;
    private javax.swing.JLabel labelSdtKh;
    private javax.swing.JLabel labelTongTien;
    private javax.swing.JTable tableSanPhamItem;
    // End of variables declaration//GEN-END:variables
}
