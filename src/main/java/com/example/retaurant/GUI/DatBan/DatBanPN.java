/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.example.retaurant.GUI.DatBan;

import com.example.retaurant.BUS.BanBUS;
import com.example.retaurant.BUS.CtHoaDonBUS;
import com.example.retaurant.BUS.HoaDonBUS;
import com.example.retaurant.BUS.MonAnBUS;
import com.example.retaurant.DTO.BanDTO;
import com.example.retaurant.DTO.CtHoaDonDTO;
import com.example.retaurant.DTO.HoaDonDTO;
import com.example.retaurant.DTO.MonAnDTO;
import com.example.retaurant.utils.RemoveVn;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.table.TableColumn;

/**
 *
 * @author light
 */
public class DatBanPN extends javax.swing.JPanel {

    private BanBUS busBan;
    private HoaDonBUS busHoaDon;
    private CtHoaDonBUS busCtHoaDon;
    static private MonAnBUS busMonAn;
    MyTableModel model;
    private ScrollableRowPanel scrollableRowPanel;
    private Timer searchTimer;

    private List<MonAnDTO> searchResults;

    public DatBanPN() {
        busBan = new BanBUS();
        busHoaDon = new HoaDonBUS();
        busCtHoaDon = new CtHoaDonBUS();
        busMonAn = new MonAnBUS();
        initComponents();
        intStyle();

    }

    public void intStyle() {
        model = new MyTableModel();
        table.setModel(model);
        table.setRowHeight(40);
        table.setCellSelectionEnabled(false);
        table.setRowSelectionAllowed(false);
        table.setShowVerticalLines(false);

        TableColumn column = table.getColumnModel().getColumn(2);
        column.setCellRenderer(new ButtonCellRenderer());
        column.setCellEditor(new ButtonCellEditor(table, model, this));
        loadModelData();

        scrollableRowPanel = new ScrollableRowPanel();
        bodyPN.add(scrollableRowPanel);

        searchTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (searchTimer != null) {
                    searchTimer.stop();
                }
                searchTimer = new Timer(500, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        performSearch(searchTextField.getText());
                        searchTimer.stop();
                        searchTimer = null;
                    }
                });
                searchTimer.setRepeats(false);
                searchTimer.start();
            }

        });
//        renderTest();
    }

    private void performSearch(String query) {
        searchResults = new ArrayList<>();
        List<MonAnDTO> listMonAn = busMonAn.getAllMonAn();
        for (MonAnDTO item : listMonAn) {
            String itemNoVn = RemoveVn.removeDiacritics(item.getTenSp());
            String searchString = RemoveVn.removeDiacritics(query);
            if (itemNoVn.toLowerCase().contains(searchString.toLowerCase())) {
                searchResults.add(item);
            }
        }

        JPopupMenu popupMenu = new JPopupMenu();

        if (searchResults.isEmpty()) {
            JMenuItem noResultsItem = new JMenuItem("Không thấy sản phẩm");
            noResultsItem.setEnabled(false); // Disable the item so it can't be clicked
            popupMenu.add(noResultsItem);

        } else {
            // voi moi san pham trong ket qua tim kiem, them vao hoa don hien tai
            for (MonAnDTO result : searchResults) {
                JMenuItem menuItem = new JMenuItem(result.getTenSp());
                menuItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println(result);
                        themSanPhamVaoHoaDonHienTai(result);
                        popupMenu.setVisible(false);
                        renderMonAnTrongHoaDon();
                    }
                });
                popupMenu.add(menuItem);
            }
        }

        // Show the popup menu below the search bar
        Point location = searchTextField.getLocationOnScreen();
        location.y += searchTextField.getHeight();
        popupMenu.show(searchTextField, 0, searchTextField.getHeight());
        popupMenu.setPreferredSize(new Dimension(searchTextField.getWidth(), popupMenu.getPreferredSize().height));
        popupMenu.revalidate();

    }

    public void loadModelData() {
        List<BanDTO> listBan = busBan.getAllBans();

        for (BanDTO item : listBan) {
            Vector row = new Vector();
            row.add(item.getTenBan());
            row.add(item.getTinhTrangSuDung());
            model.addRow(row);
        }
    }

    public void themSanPhamVaoHoaDonHienTai(MonAnDTO monAn) {
        HoaDonDTO currentHoaDonDTO = scrollableRowPanel.getHoaDonDTO();

        if (currentHoaDonDTO == null) {
            System.out.println("bàn hiện tại không có hóa đơn");
            return;
        }
        CtHoaDonDTO newCtHoaDon = new CtHoaDonDTO(currentHoaDonDTO.getHdId(), monAn.getSpId(), 1, monAn.getGiaSp());
        busCtHoaDon.addCtHoaDon(newCtHoaDon);
        System.out.println("them xong");
    }

    public void renderMonAnTrongHoaDon() {
        scrollableRowPanel.removeAllChildPanels();
        int hoadonId = scrollableRowPanel.getHoaDonDTO().getHdId();
        System.out.println("hoa don hien tai: " + hoadonId);
        ArrayList<CtHoaDonDTO> listMonAn = (ArrayList) busCtHoaDon.getAllCtHoaDonsByHoaDonId(hoadonId);
        if (listMonAn == null) {
            System.out.println("khong co san pham" + hoadonId);
            return;
        };
        System.out.println("co san pham" + hoadonId);
        System.out.println(listMonAn.size());
        for (CtHoaDonDTO item : listMonAn) {
            MonAnDTO monAnItem = busMonAn.getMonAnById(item.getSpdId());
            OrderItemPn rowPanel = new OrderItemPn(monAnItem.getTenSp(), 1);
            rowPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

            scrollableRowPanel.addRowPanel(rowPanel);
        }

    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        leftPanel = new javax.swing.JPanel();
        tableNameLb = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        searchTextField = new javax.swing.JTextField();
        headerPN = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        bodyPN = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        btnPay = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();

        jMenu1.setText("jMenu1");

        jMenuItem1.setText("jMenuItem1");
        jMenu1.add(jMenuItem1);

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel2.setPreferredSize(new java.awt.Dimension(708, 100));

        jLabel1.setText("Đặt bàn");

        jLabel3.setText("jLabel3");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 184, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(56, 56, 56))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel3)))
                .addContainerGap(56, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2);

        jScrollPane1.setPreferredSize(new java.awt.Dimension(300, 406));

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Tên bàn", "Trạng thái", "Hành động"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setResizable(false);
            table.getColumnModel().getColumn(0).setPreferredWidth(70);
            table.getColumnModel().getColumn(1).setResizable(false);
            table.getColumnModel().getColumn(1).setPreferredWidth(40);
            table.getColumnModel().getColumn(2).setResizable(false);
        }

        jPanel1.add(jScrollPane1);

        add(jPanel1);

        leftPanel.setMaximumSize(new java.awt.Dimension(450, 2147483647));
        leftPanel.setMinimumSize(new java.awt.Dimension(400, 0));
        leftPanel.setPreferredSize(new java.awt.Dimension(400, 524));
        leftPanel.setLayout(new javax.swing.BoxLayout(leftPanel, javax.swing.BoxLayout.Y_AXIS));

        tableNameLb.setText("ten ba");
        tableNameLb.setMaximumSize(new java.awt.Dimension(100, 16));
        tableNameLb.setPreferredSize(new java.awt.Dimension(50, 16));
        leftPanel.add(tableNameLb);
        tableNameLb.getAccessibleContext().setAccessibleName("ten ban");

        jPanel4.setMaximumSize(new java.awt.Dimension(32767, 40));
        jPanel4.setMinimumSize(new java.awt.Dimension(88, 25));
        jPanel4.setPreferredSize(new java.awt.Dimension(332, 60));

        jLabel2.setText("Tìm kiếm");
        jPanel4.add(jLabel2);

        searchTextField.setMargin(new java.awt.Insets(2, 6, 2, 10));
        searchTextField.setMaximumSize(new java.awt.Dimension(2147483647, 26));
        searchTextField.setPreferredSize(new java.awt.Dimension(150, 25));
        jPanel4.add(searchTextField);

        leftPanel.add(jPanel4);

        headerPN.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 15, 1, 1));
        headerPN.setLayout(new java.awt.GridLayout(1, 0));

        jLabel4.setText("Tên");
        headerPN.add(jLabel4);

        jLabel5.setText("Số lượng");
        headerPN.add(jLabel5);

        leftPanel.add(headerPN);

        bodyPN.setLayout(new java.awt.BorderLayout());
        leftPanel.add(bodyPN);

        jPanel6.setMinimumSize(new java.awt.Dimension(186, 100));
        jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 1));

        btnPay.setText("Thanh toán");
        btnPay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPayActionPerformed(evt);
            }
        });
        jPanel6.add(btnPay);

        btnSave.setText("Lưu");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        jPanel6.add(btnSave);

        leftPanel.add(jPanel6);

        add(leftPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void btnPayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPayActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSaveActionPerformed
    public JPanel getListItemJPanel() {
        return bodyPN;
    }

    public JLabel getTenBanLabel() {
        return tableNameLb;
    }

    public void setTenBanXemChiTietHienTai(String name) {
        tableNameLb.setText(name);
    }

    public void setBanForListScrollItemPN(BanDTO banDTO) {
        this.scrollableRowPanel.setDtoBan(banDTO);
    }

    public void setHoaDonForListScrollItemPN(HoaDonDTO dtoHoaDonDTO) {
        this.scrollableRowPanel.setDtoHoaDon(dtoHoaDonDTO);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Panel Runner");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            DatBanPN panel = new DatBanPN();
            frame.getContentPane().add(panel);

            frame.setSize(1200, 800);
            frame.setLocationRelativeTo(null); // Center the frame
            frame.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bodyPN;
    private javax.swing.JButton btnPay;
    private javax.swing.JButton btnSave;
    private javax.swing.JPanel headerPN;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel leftPanel;
    private javax.swing.JTextField searchTextField;
    private javax.swing.JTable table;
    private javax.swing.JLabel tableNameLb;
    // End of variables declaration//GEN-END:variables
}
