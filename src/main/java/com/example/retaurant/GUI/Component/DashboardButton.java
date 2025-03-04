package com.example.retaurant.GUI.Component;

import javax.swing.JLabel;

public class DashboardButton extends JLabel {
  public DashboardButton(String text, int width, int height) {
    super();
    this.setFont(new java.awt.Font("Segoe UI", 1, 14));
    this.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    this.setText(text);
    setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
    this.setMaximumSize(new java.awt.Dimension(width, height));
    this.setPreferredSize(new java.awt.Dimension(width, height));
  }
}
