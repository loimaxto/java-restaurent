/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.retaurant.GUI.DatBan;

/**
 *
 * @author light
 */
import com.example.retaurant.DTO.BanDTO;
import com.example.retaurant.DTO.HoaDonDTO;
import javax.swing.*;
import java.awt.*;

public class ScrollableRowPanel extends JPanel {
    
    
    private JPanel contentPanel;
    private JScrollPane scrollPane;
    private GridBagConstraints gbc;
    private BanDTO dtoBan;
    private HoaDonDTO dtoHoaDon;
    
    public ScrollableRowPanel() {
        setLayout(new BorderLayout());
        
        contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());

        scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollPane, BorderLayout.CENTER);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 5, 5, 5);
    }

    public void addRowPanel(JPanel rowPanel) {
        contentPanel.add(rowPanel, gbc);
        gbc.gridy++;
    }

    public JPanel getContentPanel(){
        return contentPanel;
    }

    public void setDtoBan(BanDTO dtoBan) {
        this.dtoBan = dtoBan;
    }

    public void setDtoHoaDon(HoaDonDTO dtoHoaDon) {
        this.dtoHoaDon = dtoHoaDon;
    }
}
