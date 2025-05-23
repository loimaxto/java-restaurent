/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.example.retaurant.GUI.CongThuc;

import com.example.retaurant.BUS.CongThucBUS;
import com.example.retaurant.BUS.MonAnBUS;
import com.example.retaurant.BUS.NguyenLieuBUS;
import com.example.retaurant.DTO.CongThucDTO;
import com.example.retaurant.DTO.NguyenLieuDTO;
import static com.sun.java.accessibility.util.SwingEventMonitor.addDocumentListener;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Administrator
 */
public class AddCongThuc extends javax.swing.JFrame {

    /**
     * Creates new form AddCongThuc
     */
    private CongThucBUS ctbus;
    public AddCongThuc(String idMonAn, String tenMonAn ) {
        initComponents();
        txt_Id_Mon.setText(idMonAn);
        txt_Id_Mon.setEditable(false);
        txt_Ten_Mon.setText(tenMonAn);
        txt_Ten_Mon.setEditable(false);
        txt_Ten_Nl.setEditable(false);
        txt_donvi.setEditable(false);
        
        NguyenLieuBUS nguyenLieuBUS = new NguyenLieuBUS();
        List<NguyenLieuDTO> dsnl =nguyenLieuBUS.getAllNguyenLieu();
        
        txt_Id_Nl.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { timTheoTen(); }
            public void removeUpdate(DocumentEvent e) { timTheoTen(); }
            public void changedUpdate(DocumentEvent e) { timTheoTen(); }

            private void timTheoTen() {
                int nl_id= Integer.parseInt(txt_Id_Nl.getText());
                for (NguyenLieuDTO nguyenLieu : dsnl) {
                    if (nl_id==nguyenLieu.getNlId()) {
                        txt_Ten_Nl.setText(nguyenLieu.getTenNl());
                        txt_donvi.setText(nguyenLieu.getDonVi());
                        break;
                       
                }
            }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txt_Id_Mon = new javax.swing.JTextField();
        txt_Id_Nl = new javax.swing.JTextField();
        txt_soluong = new javax.swing.JTextField();
        btn_XacNhan = new javax.swing.JButton();
        btn_Dong = new javax.swing.JButton();
        txt_Ten_Mon = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txt_Ten_Nl = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txt_donvi = new javax.swing.JTextField();

        jLabel6.setText("jLabel6");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Thêm Công Thức");

        jLabel2.setText("ID Món Ăn");

        jLabel3.setText("ID Nguyên Liệu");

        jLabel4.setText("Số lượng");

        txt_Id_Mon.setName(""); // NOI18N
        txt_Id_Mon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_Id_MonActionPerformed(evt);
            }
        });

        txt_Id_Nl.setName(""); // NOI18N

        txt_soluong.setName(""); // NOI18N

        btn_XacNhan.setText("Xác Nhận");
        btn_XacNhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_XacNhanActionPerformed(evt);
            }
        });

        btn_Dong.setText("Đóng");
        btn_Dong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_DongActionPerformed(evt);
            }
        });

        txt_Ten_Mon.setName(""); // NOI18N

        jLabel5.setText("Tên Món Ăn");

        jLabel7.setText("Tên Nguyên Liệu");

        txt_Ten_Nl.setName(""); // NOI18N

        jLabel8.setText("Đơn vị");

        txt_donvi.setName(""); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 18, Short.MAX_VALUE)
                        .addComponent(btn_XacNhan)
                        .addGap(18, 18, 18)
                        .addComponent(btn_Dong)
                        .addGap(17, 17, 17))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(10, 10, 10))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(22, 22, 22))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_donvi)
                            .addComponent(txt_soluong)
                            .addComponent(txt_Ten_Nl)
                            .addComponent(txt_Id_Nl, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txt_Ten_Mon, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txt_Id_Mon, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE))))
                .addGap(103, 103, 103))
            .addGroup(layout.createSequentialGroup()
                .addGap(128, 128, 128)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel1)
                .addGap(47, 47, 47)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_Id_Mon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_Ten_Mon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_Id_Nl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txt_Ten_Nl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txt_donvi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txt_soluong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_XacNhan)
                    .addComponent(btn_Dong))
                .addContainerGap(42, Short.MAX_VALUE))
        );

        jLabel2.getAccessibleContext().setAccessibleName("Tên Món Ăn");
        txt_Id_Mon.getAccessibleContext().setAccessibleName("");
        txt_Ten_Mon.getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_XacNhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_XacNhanActionPerformed

        // TODO add your handling code here:
        int spid = Integer.parseInt(txt_Id_Mon.getText());
        int nlid = Integer.parseInt(txt_Id_Nl.getText());
        float soluong = Float.parseFloat(txt_soluong.getText());
        CongThucDTO ct =new CongThucDTO(spid,nlid,soluong);
        CongThucBUS ctbus = new CongThucBUS();
        MonAnBUS mabus = new MonAnBUS();
        if(!mabus.ktraIdMonAn(spid)){
            JOptionPane.showMessageDialog(null, "id món ăn chưa có", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(!ctbus.checkSoluong(ct)){
            JOptionPane.showMessageDialog(null, "id món ăn chưa có", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            if(ctbus.checkCongThuc(ct)){
                JOptionPane.showMessageDialog(null, "id món ăn và id nguyên liệu đã tồn tại", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddCongThuc.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            if (ctbus.addCongThuc(ct)>0){
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                this.setVisible(false);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddCongThuc.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            this.dispose();
        }
        
        
        
    }//GEN-LAST:event_btn_XacNhanActionPerformed

    
    private void btn_DongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_DongActionPerformed
        // TODO add your handling code her
        this.dispose();
    }//GEN-LAST:event_btn_DongActionPerformed

    private void txt_Id_MonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_Id_MonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_Id_MonActionPerformed

    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AddCongThuc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddCongThuc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddCongThuc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddCongThuc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Dong;
    private javax.swing.JButton btn_XacNhan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JTextField txt_Id_Mon;
    private javax.swing.JTextField txt_Id_Nl;
    private javax.swing.JTextField txt_Ten_Mon;
    private javax.swing.JTextField txt_Ten_Nl;
    private javax.swing.JTextField txt_donvi;
    private javax.swing.JTextField txt_soluong;
    // End of variables declaration//GEN-END:variables
}
