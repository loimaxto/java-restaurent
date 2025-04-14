/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.retaurant.Main;

import com.example.retaurant.utils.DBConnection;
import com.example.retaurant.GUI.DangNhap.DangNhapGUI;
import com.example.retaurant.GUI.NguyenLieu.NguyenLieuGUI;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {
    public static void main(String[] args) {
        // Initialize database connection
        DBConnection.getConnection();
        
        // Set look and feel
        changLNF("Nimbus");
        
        // Show login window
        DangNhapGUI login = new DangNhapGUI();
        login.showWindow();
        
        // After successful login, you would typically show the main application window
        // which would include access to NguyenLieuGUI
    }

    public static void changLNF(String nameLNF) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if (nameLNF.equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | 
                IllegalAccessException | UnsupportedLookAndFeelException ex) {
            System.err.println("Error setting look and feel: " + ex.getMessage());
        }
    }
    
    // Add a method to show the NguyenLieuGUI (typically called after successful login)
    public static void showNguyenLieuGUI() {
        SwingUtilities.invokeLater(() -> {
            NguyenLieuGUI nguyenLieuGUI = new NguyenLieuGUI();
            nguyenLieuGUI.setVisible(true);
        });
    }
}