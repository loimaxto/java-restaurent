package com.example.retaurant.utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.image.PNGTranscoder;

public class ImageUtil {

    public static void setIcon(JLabel target, String srcName, int width, int height) {
        if (srcName != null) {
            target.setIcon(new javax.swing.ImageIcon(
                    new javax.swing.ImageIcon(
                            ImageUtil.class.getResource(srcName)).getImage()
                            .getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH)));
        }
    }
    
    public static void setIcont(JButton target, String srcName, int width, int height) {
        try {
                InputStream svgStream = ImageUtil.class.getResourceAsStream(srcName);
                if (svgStream == null) {
                    throw new IllegalArgumentException("SVG resource not found: "+srcName);
                }
                PNGTranscoder transcoder = new PNGTranscoder();
                TranscoderInput input = new TranscoderInput(svgStream);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                transcoder.transcode(input, new org.apache.batik.transcoder.TranscoderOutput(outputStream));
                BufferedImage bufferedImage = javax.imageio.ImageIO.read(new java.io.ByteArrayInputStream(outputStream.toByteArray()));
                Image scaledImage = bufferedImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImage);
                target.setIcon(scaledIcon);
                target.setBorderPainted(false); // Optional: Cleaner look
                target.setContentAreaFilled(false);

            } catch (Exception e) {
                e.printStackTrace();
            }
        
    }
    public static void main(String[] args) {
        JButton btn = new JButton();
        ImageUtil.setIcont(btn, "./image/three-dots-vertical-svgrepo-com.svg", 0, 0);
    }
 }
