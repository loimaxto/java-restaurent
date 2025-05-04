/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.example.retaurant.GUI.DatBan;

import com.example.retaurant.BUS.BanBUS;
import com.example.retaurant.BUS.CtHoaDonBUS;
import com.example.retaurant.BUS.CustomerBUS;
import com.example.retaurant.BUS.HoaDonBUS;
import com.example.retaurant.BUS.MonAnBUS;
import com.example.retaurant.DAO.BanDAO;
import com.example.retaurant.DTO.BanDTO;
import com.example.retaurant.DTO.CtHoaDonDTO;
import com.example.retaurant.DTO.CustomerDTO;
import com.example.retaurant.DTO.HoaDonDTO;
import com.example.retaurant.DTO.HoaDonDTO2;
import com.example.retaurant.DTO.MonAnDTO;
import com.example.retaurant.GUI.HoaDon.ChiTietHoaDonModal;
import com.example.retaurant.GUI.KhachHang.AddKhachHangPanel;
import com.example.retaurant.MyCustom.MyDialog;
import com.example.retaurant.utils.ImageUtil;
import java.awt.CardLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
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
    private CustomerBUS busCustomer;
    static private MonAnBUS busMonAn;
    MyTableModel model;
    private ScrollableRowPanel listItemInBillPanel;
    private Timer searchTimer;

    private List<MonAnDTO> searchResults;
    private int currentNvId;
    ButtonCellEditor btnCellEditor;

    public DatBanPN() {
        currentNvId = 2;
        busBan = new BanBUS();
        busHoaDon = new HoaDonBUS();
        busCtHoaDon = new CtHoaDonBUS();
        busMonAn = new MonAnBUS();
        busCustomer = new CustomerBUS();
        initComponents();
        // Load the image (replace "icon.png" with your image path)
        ImageIcon originalIcon = new ImageIcon("image/three-dots.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        btnKhModal.setIcon(scaledIcon);

        intStyle();
    }

    public void setCurrentNvId(int nvId) {
        this.currentNvId = nvId;
        System.out.println("dat ban pn nvid: " + this.currentNvId);
    }

    public void intStyle() {
        model = new MyTableModel();
        table.setModel(model);
        table.setRowHeight(40);
        table.setCellSelectionEnabled(false);
        table.setRowSelectionAllowed(false);
        table.setShowVerticalLines(false);

        TableColumn column = table.getColumnModel().getColumn(3);
        btnCellEditor = new ButtonCellEditor(table, model, this);
        column.setCellRenderer(new ButtonCellRenderer());
        column.setCellEditor(btnCellEditor);
        renderThongTinBan();

        listItemInBillPanel = new ScrollableRowPanel();
        bodyPN.add(listItemInBillPanel);

        searchTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (searchTimer != null) {
                    searchTimer.stop();
                }
                searchTimer = new Timer(300, new ActionListener() {
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
        CardLayout cardLayoutSettingTable = new CardLayout();
        pnCardLayoutSetting.setLayout(cardLayoutSettingTable);
        pnCardLayoutSetting.add(pnThemBan,"pnThemBan");
        pnCardLayoutSetting.add(pnXoaBan,"pnXoaBan");
        
        dialogTable.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialogTable.pack();
        dialogTable.setLocationRelativeTo(this);
        btnSwitchAddTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayoutSettingTable.show(pnCardLayoutSetting, "pnThemBan");
            }
        });
        btnSwitchDeleteTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("xxoa pn");
                cardLayoutSettingTable.show(pnCardLayoutSetting, "pnXoaBan");
            }
        });
    }

    private void performSearch(String query) {
        searchResults = new ArrayList<>();
        List<MonAnDTO> listMonAn = busMonAn.searchMonAnByName(query);
        for (MonAnDTO item : listMonAn) {
            searchResults.add(item);
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
                        boolean isThemThanhCong = themSanPhamVaoHoaDonHienTai(result);
                        popupMenu.setVisible(false);
                        if (isThemThanhCong) {
                            renderMonAnTrongHoaDon();
                        }

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

    public void renderThongTinBan() {
        model.resetData();
        List<BanDTO> listBan = busBan.getAllBans();

        for (BanDTO item : listBan) {
            Vector row = new Vector();
            row.add(item.getBanId());
            row.add(item.getTenBan());
            row.add(item.getTinhTrangSuDung());
            model.addRow(row);
        }
    }

    public boolean themSanPhamVaoHoaDonHienTai(MonAnDTO monAn) {
        HoaDonDTO currentHoaDonDTO = listItemInBillPanel.getHoaDonDTO();

        if (currentHoaDonDTO == null) {
            System.out.println("bàn hiện tại không có hóa đơn");
            JOptionPane.showMessageDialog(null, "Chưa chọn bàn!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return false;
        } else {
            CtHoaDonDTO newCtHoaDon = new CtHoaDonDTO(currentHoaDonDTO.getHdId(), monAn.getSpId(), 1, monAn.getGiaSp());
            busCtHoaDon.addCtHoaDon(newCtHoaDon);
            System.out.println("them xong");
            return true;
        }

    }

    public void resetThongTinHoaDon() {
        tenBanLb.setText("Chưa chọn bàn");
        textFieldTenKh.setText("Chưa chọn khách hàng");
        textFieldsdt.setText("Trống");
        listItemInBillPanel.setDtoBan(null);
        listItemInBillPanel.setDtoHoaDon(null);
        listItemInBillPanel.removeAllChildPanels();
    }

    public void renderMonAnTrongHoaDon() {
        listItemInBillPanel.removeAllChildPanels();
        int hoadonId = listItemInBillPanel.getHoaDonDTO().getHdId();
        ArrayList<CtHoaDonDTO> listMonAn = (ArrayList) busCtHoaDon.getAllCtHoaDonsByHoaDonId(hoadonId);
        if (listMonAn == null) {
            System.out.println("Không có sản phẩm" + hoadonId);
            listItemInBillPanel.addEmptyLabel();
            return;
        };
        for (CtHoaDonDTO item : listMonAn) {
            MonAnDTO monAnItem = busMonAn.getMonAnById(item.getSpdId());
            OrderItemPn rowPanel = new OrderItemPn(item, monAnItem, this);
            rowPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

            listItemInBillPanel.addRowPanel(rowPanel);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        dialogTable = new javax.swing.JDialog();
        pnCardLayoutSetting = new javax.swing.JPanel();
        pnThemBan = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        textMaBanThem = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        textSttBan = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        btnXacNhanThemBan = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        pnXoaBan = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        textMaBanXoa = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        btnXacNhanThemBan1 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        btnSwitchAddTable = new javax.swing.JButton();
        btnSwitchDeleteTable = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        tenKhLabel = new javax.swing.JLabel();
        txtFieldSdt = new javax.swing.JTextField();
        btnInsertKhForHoaDon = new javax.swing.JButton();
        textFieldTenKh = new javax.swing.JTextField();
        sdtTextField = new javax.swing.JLabel();
        textFieldsdt = new javax.swing.JTextField();
        btnThemKh = new javax.swing.JButton();
        btnKhModal = new javax.swing.JButton();
        btnSetting = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        leftPanel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        searchTextField = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        tenBanLb = new javax.swing.JLabel();
        headerPN = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        bodyPN = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        btnPay = new javax.swing.JButton();

        jMenu1.setText("jMenu1");

        jMenuItem1.setText("jMenuItem1");
        jMenu1.add(jMenuItem1);

        pnCardLayoutSetting.setLayout(new java.awt.CardLayout());

        jLabel1.setText("Nhập mã bàn");

        jLabel6.setText("Nhập tên bàn(số thứ tự)");

        btnXacNhanThemBan.setText("Xác nhận");
        btnXacNhanThemBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXacNhanThemBanActionPerformed(evt);
            }
        });
        jPanel9.add(btnXacNhanThemBan);

        jLabel10.setText("Thêm");

        javax.swing.GroupLayout pnThemBanLayout = new javax.swing.GroupLayout(pnThemBan);
        pnThemBan.setLayout(pnThemBanLayout);
        pnThemBanLayout.setHorizontalGroup(
            pnThemBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnThemBanLayout.createSequentialGroup()
                .addGroup(pnThemBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnThemBanLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(pnThemBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)
                        .addGroup(pnThemBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textSttBan, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textMaBanThem, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnThemBanLayout.createSequentialGroup()
                        .addGap(150, 150, 150)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(66, Short.MAX_VALUE))
        );
        pnThemBanLayout.setVerticalGroup(
            pnThemBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnThemBanLayout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnThemBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(textMaBanThem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnThemBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textSttBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pnCardLayoutSetting.add(pnThemBan, "pnThemBan");

        jLabel7.setText("Nhập mã bàn");

        textMaBanXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textMaBanXoaActionPerformed(evt);
            }
        });

        btnXacNhanThemBan1.setText("Xác nhận");
        btnXacNhanThemBan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXacNhanThemBan1ActionPerformed(evt);
            }
        });
        jPanel10.add(btnXacNhanThemBan1);

        jLabel9.setText("Xóa");

        javax.swing.GroupLayout pnXoaBanLayout = new javax.swing.GroupLayout(pnXoaBan);
        pnXoaBan.setLayout(pnXoaBanLayout);
        pnXoaBanLayout.setHorizontalGroup(
            pnXoaBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnXoaBanLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56)
                .addComponent(textMaBanXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(66, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnXoaBanLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(148, 148, 148))
        );
        pnXoaBanLayout.setVerticalGroup(
            pnXoaBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnXoaBanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addGap(13, 13, 13)
                .addGroup(pnXoaBanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(textMaBanXoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 82, Short.MAX_VALUE)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pnCardLayoutSetting.add(pnXoaBan, "pnXoaBan");

        btnSwitchAddTable.setText("Thêm bàn");
        jPanel7.add(btnSwitchAddTable);

        btnSwitchDeleteTable.setText("Xóa bàn");
        jPanel7.add(btnSwitchDeleteTable);

        jLabel3.setText("Quản lý bàn");
        jPanel8.add(jLabel3);

        javax.swing.GroupLayout dialogTableLayout = new javax.swing.GroupLayout(dialogTable.getContentPane());
        dialogTable.getContentPane().setLayout(dialogTableLayout);
        dialogTableLayout.setHorizontalGroup(
            dialogTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnCardLayoutSetting, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dialogTableLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        dialogTableLayout.setVerticalGroup(
            dialogTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogTableLayout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnCardLayoutSetting, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel2.setPreferredSize(new java.awt.Dimension(708, 100));

        tenKhLabel.setText("Tên khách hàng");

        btnInsertKhForHoaDon.setText("Thêm số điện thoại");
        btnInsertKhForHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertKhForHoaDonActionPerformed(evt);
            }
        });

        textFieldTenKh.setEditable(false);

        sdtTextField.setText("Số điện thoại");

        textFieldsdt.setEditable(false);

        btnThemKh.setText("Thêm khách hàng mới");
        btnThemKh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemKhActionPerformed(evt);
            }
        });

        btnKhModal.setMaximumSize(new java.awt.Dimension(50, 11));
        btnKhModal.setMinimumSize(new java.awt.Dimension(20, 11));
        btnKhModal.setPreferredSize(new java.awt.Dimension(32, 32));
        btnKhModal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKhModalActionPerformed(evt);
            }
        });

        btnSetting.setText("Cài đặt");
        btnSetting.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSettingActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(tenKhLabel)
                        .addGap(34, 34, 34)
                        .addComponent(textFieldTenKh))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtFieldSdt, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnInsertKhForHoaDon)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(sdtTextField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(textFieldsdt, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 221, Short.MAX_VALUE)
                        .addComponent(btnSetting))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(btnKhModal, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnThemKh)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtFieldSdt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnInsertKhForHoaDon)
                        .addComponent(btnThemKh))
                    .addComponent(btnKhModal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tenKhLabel)
                    .addComponent(textFieldTenKh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sdtTextField)
                    .addComponent(textFieldsdt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSetting))
                .addContainerGap(25, Short.MAX_VALUE))
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

        jPanel4.setMaximumSize(new java.awt.Dimension(32767, 40));
        jPanel4.setMinimumSize(new java.awt.Dimension(88, 25));
        jPanel4.setPreferredSize(new java.awt.Dimension(332, 45));

        jLabel2.setText("Tìm kiếm món ăn");
        jPanel4.add(jLabel2);

        searchTextField.setMargin(new java.awt.Insets(2, 6, 2, 10));
        searchTextField.setMaximumSize(new java.awt.Dimension(2147483647, 26));
        searchTextField.setPreferredSize(new java.awt.Dimension(250, 30));
        jPanel4.add(searchTextField);

        leftPanel.add(jPanel4);

        jPanel3.setPreferredSize(new java.awt.Dimension(400, 50));

        tenBanLb.setText("Chưa chọn bàn");
        tenBanLb.setMaximumSize(new java.awt.Dimension(100, 16));
        tenBanLb.setPreferredSize(new java.awt.Dimension(50, 16));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(tenBanLb, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(118, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tenBanLb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        tenBanLb.getAccessibleContext().setAccessibleName("ten ban");

        leftPanel.add(jPanel3);

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

        leftPanel.add(jPanel6);

        add(leftPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void btnPayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPayActionPerformed
        HoaDonDTO hdDto = listItemInBillPanel.getHoaDonDTO();
        System.out.println("get dto hoa don:" + hdDto.toString());
        if (hdDto == null) {
            new MyDialog("Chưa chọn bàn thanh toán!", 0);
            return;
        }
        if (hdDto.getKhId() == null) {
            new MyDialog("Chưa thêm thông tin khách hàng", 0);
            return;
        }
        HoaDonDTO2 hdDto2 = busHoaDon.getBillDTO2ById(hdDto.getHdId());
        System.out.println(hdDto2.toString());
        ChiTietHoaDonModal cthdm = new ChiTietHoaDonModal(hdDto2, true);
        cthdm.setDatBanPn(this);
        cthdm.setLocationRelativeTo(this); // Center relative to the parent
        cthdm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Crucial line
        cthdm.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        cthdm.setVisible(true);

//        ArrayList<OrderItemPn> listOrderItemPns = listItemInBillPanel.getOrderItemPns();
//        CtHoaDonBUS ctHoaDonBUS = new CtHoaDonBUS();
//        for ( OrderItemPn item : listOrderItemPns) {
//            CtHoaDonDTO itemCtHoaDonDTO = item.getCtHoaDonDTO();
//            itemCtHoaDonDTO.setTongTienCt(itemCtHoaDonDTO.getSoLuong() * itemCtHoaDonDTO.getGiaTaiLucDat());
//            ctHoaDonBUS.updateCtHoaDon(itemCtHoaDonDTO);
//        }
    }//GEN-LAST:event_btnPayActionPerformed

    private void btnThemKhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemKhActionPerformed
        // TODO add your handling code here:
        if (listItemInBillPanel.getHoaDonDTO() == null) {
            new MyDialog("Chọn bàn trước khi thêm khách hàng!", WIDTH);
            return;
        }

        AddKhachHangPanel addPanel = new AddKhachHangPanel();
        addPanel.setLocationRelativeTo(this); // Center relative to the parent
        addPanel.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Crucial line
        addPanel.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        addPanel.setVisible(true);
    }//GEN-LAST:event_btnThemKhActionPerformed

    private void btnInsertKhForHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertKhForHoaDonActionPerformed
        String sdt = txtFieldSdt.getText();
        CustomerDTO cust = busCustomer.getCustomerByPhone(sdt);
        HoaDonDTO hoaDonDto = listItemInBillPanel.getHoaDonDTO();
        System.out.println("scroll" + hoaDonDto.toString());
        if (cust != null && hoaDonDto != null) {
            hoaDonDto.setKhId(cust.getKhId());
            System.out.println(hoaDonDto.toString());
            busHoaDon.updateBill(hoaDonDto);
            updateCustomerInforForTable(cust.getKhId());
        } else if (hoaDonDto == null) {
            new MyDialog("Chưa chọn hóa đơn thêm khách hàng", 0);
        } else if (cust == null) {
            new MyDialog("Không tìm thấy khách hàng này", 0);
        }
    }//GEN-LAST:event_btnInsertKhForHoaDonActionPerformed

    private void btnKhModalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKhModalActionPerformed
        if (listItemInBillPanel.getHoaDonDTO() == null) {
            new MyDialog("Chọn bàn trước khi thêm khách hàng!", WIDTH);
            return;
        }

        KhachHangModal khachHangModal = new KhachHangModal(this);
        khachHangModal.setLocationRelativeTo(this);
        khachHangModal.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        khachHangModal.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        khachHangModal.setVisible(true);


    }//GEN-LAST:event_btnKhModalActionPerformed

    private void textMaBanXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textMaBanXoaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textMaBanXoaActionPerformed

    private void btnXacNhanThemBan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXacNhanThemBan1ActionPerformed
        // TODO add your handling code here:
        BanBUS banBUS = new BanBUS();
        banBUS.deleteBan(Integer.parseInt(textMaBanXoa.getText()));
        renderThongTinBan();
        dialogTable.dispose();
    }//GEN-LAST:event_btnXacNhanThemBan1ActionPerformed

    private void btnXacNhanThemBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXacNhanThemBanActionPerformed
        // TODO add your handling code here:
        BanDTO banDTO = new BanDTO();
        banDTO.setBanId(Integer.parseInt(textMaBanThem.getText()));
        banDTO.setTenBan("Bàn " + textSttBan.getText());
        BanBUS banBUS = new BanBUS();
        banBUS.addBan(banDTO);
        renderThongTinBan();
        dialogTable.dispose();
    }//GEN-LAST:event_btnXacNhanThemBanActionPerformed

    private void btnSettingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSettingActionPerformed
        // TODO add your handling code here:
        dialogTable.setVisible(true);
    }//GEN-LAST:event_btnSettingActionPerformed
    public void updateNewInsertKhachHanhForHoaDon(int khId) {
        CustomerDTO cust = busCustomer.getCustomerById(khId);
        HoaDonDTO hoaDonDto = listItemInBillPanel.getHoaDonDTO();
        if (cust != null && hoaDonDto != null) {
            hoaDonDto.setKhId(cust.getKhId());
            busHoaDon.updateBill(hoaDonDto);
            updateCustomerInforForTable(cust.getKhId());
        } else if (hoaDonDto == null) {
            new MyDialog("Chưa chọn hóa đơn thêm khách hàng", 0);
            return;
        }
    }

    public void resetCurrentHoaDonAndBanAndTable() {
        btnCellEditor.resetCurrentHoaDonAndBanAndTable();
    }

    public void updateCustomerInforForTable(Integer custId) {
        CustomerDTO cust = busCustomer.getCustomerById(custId);
        System.out.println(cust.toString());
        textFieldTenKh.setText(cust.getHoKh() + " " + cust.getTenKh());
        textFieldsdt.setText(cust.getSdt());
    }

    public void clearCustomerInforForTable() {
        textFieldTenKh.setText("");
        textFieldsdt.setText("");
    }

    public JPanel getListItemJPanel() {
        return bodyPN;
    }

    public int getCreatorId() {
        return this.currentNvId;
    }

    public BanDTO getCurrentBanDTO() {
        return listItemInBillPanel.getBanDTO();
    }

    public JLabel getTenBanLabel() {
        return tenBanLb;
    }

    public void setTenBanXemChiTietHienTai(String name) {
        tenBanLb.setText(name);
    }

    public void setBanForListScrollItemPN(BanDTO banDTO) {
        this.listItemInBillPanel.setDtoBan(banDTO);
    }

    public void setHoaDonForListScrollItemPN(HoaDonDTO dtoHoaDonDTO) {
        this.listItemInBillPanel.setDtoHoaDon(dtoHoaDonDTO);
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
    private javax.swing.JButton btnInsertKhForHoaDon;
    private javax.swing.JButton btnKhModal;
    private javax.swing.JButton btnPay;
    private javax.swing.JButton btnSetting;
    private javax.swing.JButton btnSwitchAddTable;
    private javax.swing.JButton btnSwitchDeleteTable;
    private javax.swing.JButton btnThemKh;
    private javax.swing.JButton btnXacNhanThemBan;
    private javax.swing.JButton btnXacNhanThemBan1;
    private javax.swing.JDialog dialogTable;
    private javax.swing.JPanel headerPN;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel leftPanel;
    private javax.swing.JPanel pnCardLayoutSetting;
    private javax.swing.JPanel pnThemBan;
    private javax.swing.JPanel pnXoaBan;
    private javax.swing.JLabel sdtTextField;
    private javax.swing.JTextField searchTextField;
    private javax.swing.JTable table;
    private javax.swing.JLabel tenBanLb;
    private javax.swing.JLabel tenKhLabel;
    private javax.swing.JTextField textFieldTenKh;
    private javax.swing.JTextField textFieldsdt;
    private javax.swing.JTextField textMaBanThem;
    private javax.swing.JTextField textMaBanXoa;
    private javax.swing.JTextField textSttBan;
    private javax.swing.JTextField txtFieldSdt;
    // End of variables declaration//GEN-END:variables
}
