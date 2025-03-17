/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.retaurant.Main;

import com.example.retaurant.utils.DBConnection ;
import com.example.retaurant.GUI.DangNhap.DangNhapGUI ;
public class Main {
    public static void main(String[] args) {
        DBConnection.getConnection();
        changLNF("Nimbus");
        DangNhapGUI login = new DangNhapGUI();
        login.showWindow();
    }

    public static void changLNF(String nameLNF) {
    }
}
