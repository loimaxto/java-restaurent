package com.example.retaurant.GUI.DatBan;

import com.example.retaurant.DTO.BanDTO;
import com.example.retaurant.DTO.HoaDonDTO;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ScrollableRowPanel extends JPanel {

    private JPanel contentPanel;
    private JScrollPane scrollPane;
    private BanDTO dtoBan;
    private HoaDonDTO dtoHoaDon;
    private List itemListPanel;
    public ScrollableRowPanel() {
        initComponent();
    }

    private void initComponent() {
        itemListPanel = new List();
        setLayout(new BorderLayout());

        // FlowLayout with vertical stacking
        contentPanel = new JPanel();
        contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        // Ensure contentPanel can grow in height
        contentPanel.setPreferredSize(new Dimension(300, 0)); 

        scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollPane, BorderLayout.CENTER);
    }

    public void addRowPanel(JPanel rowPanel) {
        // Insert new row at the top by adding at index 0
        contentPanel.add(rowPanel, 0);

        // Adjust panel height dynamically
        int newHeight = contentPanel.getComponentCount() * rowPanel.getPreferredSize().height + 20;
        contentPanel.setPreferredSize(new Dimension(300, newHeight));

        

        // Scroll to top to make new item fully visible
        SwingUtilities.invokeLater(() -> {
            JViewport viewport = scrollPane.getViewport();
            viewport.setViewPosition(new Point(0, 0));
        });
    }
    public void addEmptyLabel() {
        JLabel emptyLabel = new JLabel("Bàn chưa gọi món");
        contentPanel.add(emptyLabel);
    }
    public JPanel getContentPanel() {
        return contentPanel;
    }

    public BanDTO getBanDTO() {
        return dtoBan;
    }

    public void removeAllChildPanels() {
        contentPanel.removeAll();
        contentPanel.setPreferredSize(new Dimension(300, 0));
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public HoaDonDTO getHoaDonDTO() {
        return dtoHoaDon;
    }
    public void setListOrderItem(ArrayList listMonAnOrder) {
//        this.itemListPanel =(List) listMonAnOrder;
    }
    public void setDtoBan(BanDTO dtoBan) {
        this.dtoBan = dtoBan;
    }

    public void setDtoHoaDon(HoaDonDTO dtoHoaDon) {
        this.dtoHoaDon = dtoHoaDon;
    }
}
