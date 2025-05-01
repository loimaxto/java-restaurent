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
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
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
    private boolean isThanhToan;
    private int tongTienHoaDon = 0;

    public ChiTietHoaDonModal(HoaDonDTO2 hddto, boolean isThanhToan) {
        busCtHoaDonBUS = new CtHoaDonBUS();
        //thong tin hoa don
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

    private void handleExportPDF() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file");
        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            PdfWriter writer = null;
            try {
                File fileToSave = fileChooser.getSelectedFile();
                String dest = fileToSave.getAbsolutePath() + ".pdf"; // Append .pdf extension
                writer = new PdfWriter(dest);
                PdfDocument pdfDoc = new PdfDocument(writer);
                pdfDoc.addNewPage();
                PdfFont vietnameseFont = PdfFontFactory.createFont(
                        "src/main/java/com/example/retaurant/resources/DejaVuSans.ttf", PdfEncodings.IDENTITY_H, true);

                Document document = new Document(pdfDoc).setFont(vietnameseFont);

                document.add(new Paragraph(" Hóa đơn").setBold());

                document.add(new Paragraph("Tên khách khàng: " + hdDto.getHoKh() + hdDto.getTenKh()));
                document.add(new Paragraph("Điện thoại: " + hdDto.getSdt()));
                document.add(new Paragraph("Nhân viên: " + hdDto.getHoTenNv()));
                document.add(new Paragraph("Thời gian: " + hdDto.getThoiGian()));
                String tongTien = hdDto.getTongGia() == null || hdDto.getTongGia() == 0
                        ? "Chưa thanh toán" : hdDto.getTongGia().toString();
                document.add(new Paragraph("Thành tiền: " + tongTien).setBold());
                Table table = new Table(4);
                table.addHeaderCell(new Cell().add(new Paragraph("Tên sản món").setBold()));
                table.addHeaderCell(new Cell().add(new Paragraph("Số lượng").setBold()));
                table.addHeaderCell(new Cell().add(new Paragraph("Giá").setBold()));
                table.addHeaderCell(new Cell().add(new Paragraph("Tổng").setBold()));
                for (CtSanPhamThanhToanDTO item : ctSpList) {
                    table.addCell(new Cell().add(new Paragraph(item.getTenSp())));
                    table.addCell(new Cell().add(new Paragraph(item.getSoLuong().toString())));
                    table.addCell(new Cell().add(new Paragraph(item.getGiaTaiLucDat().toString())));
                    table.addCell(new Cell().add(new Paragraph(item.getTongTienCt().toString())));
                }
                document.add(table);

                document.close();
                System.out.println("PDF lưu file thành công tại: " + dest);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ChiTietHoaDonModal.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
            } catch (IOException ex) {
                Logger.getLogger(ChiTietHoaDonModal.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    writer.close();
                } catch (IOException ex) {
                    Logger.getLogger(ChiTietHoaDonModal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            System.out.println("Save operation cancelled.");
        }
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
        btnXuatHoaDon = new javax.swing.JButton();

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

        btnXuatHoaDon.setText("Xuất hóa đơn");
        btnXuatHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatHoaDonActionPerformed(evt);
            }
        });

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
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnXuatHoaDon)
                        .addGap(11, 11, 11))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnXacNhan)
                        .addGap(52, 52, 52)
                        .addComponent(btnHuy)
                        .addGap(98, 98, 98))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(labelForm, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(142, 142, 142))))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnXuatHoaDon)
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHuy)
                    .addComponent(btnXacNhan))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        dispose();
    }//GEN-LAST:event_btnHuyActionPerformed

    private void btnXacNhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXacNhanActionPerformed
        HoaDonBUS busHoaDon = new HoaDonBUS();
        hdDto.setTongGia(tongTienHoaDon);
        ZoneId hoChiMinhZone = ZoneId.of("Asia/Ho_Chi_Minh");
        LocalDateTime now = LocalDateTime.now(hoChiMinhZone);
        Timestamp currentTimestamp = Timestamp.valueOf(now);
        hdDto.setThoiGian(currentTimestamp);
        busHoaDon.updateBill(hdDto);
        if (busHoaDon.updateBill(hdDto)) {
            new MyDialog("Thanh toán thành công !", 0);
            BanBUS busBan = new BanBUS();
            BanDTO currentBanDTO = datBanPN.getCurrentBanDTO();
            busBan.updateBanDangDuocDat(currentBanDTO, currentBanDTO.getIdHoaDonHienTai());
            datBanPN.resetCurrentHoaDonAndBanAndTable();
            dispose();
        }

    }//GEN-LAST:event_btnXacNhanActionPerformed

    private void btnXuatHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatHoaDonActionPerformed
        handleExportPDF();

    }//GEN-LAST:event_btnXuatHoaDonActionPerformed
    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnXacNhan;
    private javax.swing.JButton btnXuatHoaDon;
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
