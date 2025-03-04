/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.retaurant.GUI.Component;

import javax.swing.JButton;
import java.awt.*;

/**
 *
 * @author truon
 */
// Class nút toggle bo tròn
public class RoundedButton extends JButton {
    public static final int NORMAL = 0;
    public static final int DONE = 1;
    public static final int NOT_DONE = 2;
    private int state = 0;

    public RoundedButton() {
        this("");
    }

    public RoundedButton(String label) {
        super(label);
        // setFocusPainted(false); // Loại bỏ viền khi focus vào nút
        setContentAreaFilled(false); // Loại bỏ nền mặc định
        setBorderPainted(false); // Loại bỏ viền mặc định
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Tô màu nền dựa trên trạng thái
        if (state == 0) {
            g2.setColor(new Color(0, 0, 0, 0)); // Trạng thái bình thường (trong suốt)
            setForeground(Color.BLACK);
        } else if (state == 1) {
            g2.setColor(Color.BLUE); // Trạng thái "done"
            setForeground(Color.WHITE);
        } else if (state == 2) {
            g2.setColor(Color.RED); // Trạng thái "not done"
            setForeground(Color.WHITE);
        }

        int arc = 30; // Bán kính bo góc
        int padding = 2; // Khoảng cách để viền không bị cắt

        // Vẽ nền nút bo tròn
        g2.fillRoundRect(padding, padding, getWidth() - padding * 2, getHeight() - padding * 2, arc, arc);

        // Vẽ viền nút
        g2.setColor(Color.BLACK);
        g2.drawRoundRect(padding, padding, getWidth() - padding * 2 - 1, getHeight() - padding * 2 - 1, arc, arc);

        g2.dispose();
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        // Không vẽ viền mặc định
    }

    public void setState(int state) {
        this.state = state;
        repaint();
    }

    public void hasDone() {
        setState(DONE);
    }

    public void hasNotDone() {
        setState(NOT_DONE);
    }

    public void reset() {
        setState(NORMAL);
    }

    public int getState() {
        return state;
    }

}
