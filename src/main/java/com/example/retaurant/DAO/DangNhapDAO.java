/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.retaurant.DAO;

import com.example.retaurant.DTO.TaiKhoan;
import com.example.retaurant.utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ASUS
 */
public class DangNhapDAO {

    public TaiKhoan dangNhap(TaiKhoan tk) {
        TaiKhoan tkLogin = null;
        Connection conn = DBConnection.getConnection(); // Kết nối cơ sở dữ liệu

        if (conn == null) {
            System.out.println("Lỗi kết nối cơ sở dữ liệu!");
            return null;
        }
        try {
            String sql = "SELECT * FROM tai_khoan WHERE username=? AND password=? AND trang_thai=1";
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, tk.getTenDangNhap());
            pre.setString(2, tk.getMatKhau());
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                tkLogin = tk;
                tkLogin.setTenDangNhap(rs.getString("username"));
                tkLogin.setQuyen(rs.getString("quyen"));
            }
            return tkLogin;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tk;
    }
}
