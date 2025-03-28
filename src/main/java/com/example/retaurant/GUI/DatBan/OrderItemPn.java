/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.retaurant.GUI.DatBan;

/**
 *
 * @author light
 */
import com.example.retaurant.BUS.CtHoaDonBUS;
import com.example.retaurant.DTO.CtHoaDonDTO;
import com.example.retaurant.DTO.MonAnDTO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrderItemPn extends JPanel {

    private JLabel itemNameLabel;
    private JLabel quantityLabel;
    private JButton increaseButton;
    private JButton decreaseButton;
    private CtHoaDonDTO dtoCtHoaDon;
    private MonAnDTO dtoMonAn;
    private DatBanPN datBanPN;
    static private CtHoaDonBUS busCtHoaDon = new CtHoaDonBUS();

    public OrderItemPn(CtHoaDonDTO dtoCtHoaDonDTO, MonAnDTO dtoMonAn, DatBanPN datBanPN) {
        this.dtoCtHoaDon = dtoCtHoaDonDTO;
        this.dtoMonAn = dtoMonAn;
        this.datBanPN = datBanPN;
        this.dtoCtHoaDon.setGiaTaiLucDat(dtoMonAn.getGiaSp());
        busCtHoaDon.updateCtHoaDon(this.dtoCtHoaDon);
        
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Item Name
        itemNameLabel = new JLabel(dtoMonAn.getTenSp());
        itemNameLabel.setPreferredSize(new Dimension(200, itemNameLabel.getPreferredSize().height));
        itemNameLabel.setMinimumSize(new Dimension(170, itemNameLabel.getPreferredSize().height));
        itemNameLabel.setBackground(Color.red);
        add(itemNameLabel, gbc);

        // Quantity
        gbc.gridx++;
        quantityLabel = new JLabel(String.valueOf(dtoCtHoaDonDTO.getSoLuong()));
        quantityLabel.setPreferredSize(new Dimension(40, itemNameLabel.getPreferredSize().height));
        add(quantityLabel, gbc);

        // Increase Button
        gbc.gridx++;
        increaseButton = new JButton("+");
        increaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                increaseQuantity();
            }
        });
        add(increaseButton, gbc);

        // Decrease Button
        gbc.gridx++;
        decreaseButton = new JButton("-");
        decreaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                decreaseQuantity();
            }
        });
        add(decreaseButton, gbc);
    }
    
    private void increaseQuantity() {
        dtoCtHoaDon.setSoLuong(dtoCtHoaDon.getSoLuong() + 1);
        busCtHoaDon.updateCtHoaDon(dtoCtHoaDon);
        updateQuantity();
    }

    private void decreaseQuantity() {
        if (dtoCtHoaDon.getSoLuong() <= 1) {
            Object[] options = {"Xác nhận", "Hủy"};
            int choice = JOptionPane.showOptionDialog(null,
                    "Xác nhận xóa sản phẩm: " + dtoMonAn.getTenSp(),
                    "Xác nhận",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[1]); // Default to "Không đồng ý"

            if (choice == 0) { // "Đồng ý"
                busCtHoaDon.deleteCtHoaDon(dtoCtHoaDon.getHdId(), dtoCtHoaDon.getSpdId());
                datBanPN.renderMonAnTrongHoaDon();
            }
        } else {
            dtoCtHoaDon.setSoLuong(dtoCtHoaDon.getSoLuong() - 1);
            busCtHoaDon.updateCtHoaDon(dtoCtHoaDon);
            updateQuantity();
        }
    }
    public MonAnDTO getMonAnDTO() {
        return dtoMonAn;
    }
    
    public CtHoaDonDTO getCtHoaDonDTO() {
        return dtoCtHoaDon;
    }
    
    private void updateQuantity() {
        quantityLabel.setText(String.valueOf(dtoCtHoaDon.getSoLuong()));
        busCtHoaDon.updateCtHoaDon(dtoCtHoaDon);
    }
}
