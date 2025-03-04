/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.retaurant.GUI.Component;

import java.awt.FontMetrics;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 *
 * @author truon
 */
public class Paragraph extends JLabel {
  public Paragraph() {
    super();
    setText("");
  }

  public Paragraph(String text) {
    super();
    setText(text);
  }

  @Override
  public void setText(String text) {
    super.setText("<html><p> " + text + "</p></html>");
  }
}
