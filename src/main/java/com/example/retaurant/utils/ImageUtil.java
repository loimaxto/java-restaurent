package com.example.retaurant.utils;

import javax.swing.JLabel;

public class ImageUtil {

    public static void setIcon(JLabel target, String srcName, int width, int height) {
        if (srcName != null) {
            target.setIcon(new javax.swing.ImageIcon(
                    new javax.swing.ImageIcon(
                            ImageUtil.class.getResource(srcName)).getImage()
                            .getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH)));
        }
    }
}
