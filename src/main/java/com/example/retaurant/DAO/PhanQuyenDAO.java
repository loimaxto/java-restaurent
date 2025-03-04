/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.retaurant.DAO;

import com.example.retaurant.DTO.PhanQuyen;
import com.example.retaurant.utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class PhanQuyenDAO {

    public ArrayList<PhanQuyen> getListQuyen() {
        ArrayList<PhanQuyen> dspq = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            System.out.println("Lỗi kết nối cơ sở dữ liệu!");
            return dspq;
        }
        try {
            String sql = "SELECT * FROM phanquyen";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                PhanQuyen phanQuyen = new PhanQuyen();
                phanQuyen.setQuyen(rs.getString(1));
                phanQuyen.setNhapHang(rs.getInt(2));
                phanQuyen.setQlSanPham(rs.getInt(3));
                phanQuyen.setQlNhanVien(rs.getInt(4));
                phanQuyen.setQlKhachHang(rs.getInt(5));
                phanQuyen.setThongKe(rs.getInt(6));
                dspq.add(phanQuyen);
            }
            rs.close();
            st.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dspq;
    }

    public PhanQuyen getQuyen(String quyen) {
        Connection conn = DBConnection.getConnection();

        if (conn == null) {
            System.out.println("Lỗi kết nối cơ sở dữ liệu!");
            return null;
        }
        try {
            String sql = "SELECT * FROM phanquyen WHERE quyen=?";
//            Statement st = MyConnect.conn.createStatement();
//            ResultSet rs = st.executeQuery(sql);
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, quyen);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                PhanQuyen phanQuyen = new PhanQuyen();
                phanQuyen.setQuyen(quyen);
                phanQuyen.setNhapHang(rs.getInt(2));
                phanQuyen.setQlSanPham(rs.getInt(3));
                phanQuyen.setQlNhanVien(rs.getInt(4));
                phanQuyen.setQlKhachHang(rs.getInt(5));
                phanQuyen.setThongKe(rs.getInt(6));
                rs.close();
                pre.close();
                conn.close();
                return phanQuyen;
            }
        } catch (Exception e) {
        }
        return null;
    }

    public boolean suaQuyen(PhanQuyen phanQuyen) {
        Connection conn = DBConnection.getConnection();

        if (conn == null) {
            System.out.println("Lỗi kết nối cơ sở dữ liệu!");
            return false;
        }
        try {
            String sql = "UPDATE phanquyen SET NhapHang=?,QLSanPham=?,QLNhanVien=?,QLKhachHang=?,ThongKe=? WHERE Quyen=?";
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, phanQuyen.getNhapHang());
            pre.setInt(2, phanQuyen.getQlSanPham());
            pre.setInt(3, phanQuyen.getQlNhanVien());
            pre.setInt(4, phanQuyen.getQlKhachHang());
            pre.setInt(5, phanQuyen.getThongKe());
            pre.setString(6, phanQuyen.getQuyen());
            boolean result = pre.executeUpdate() > 0;
            pre.close();
            conn.close();
            return result;
        } catch (Exception e) {
        }
        return false;
    }

    public boolean themQuyen(PhanQuyen phanQuyen) {
        Connection conn = DBConnection.getConnection();

        if (conn == null) {
            System.out.println("Lỗi kết nối cơ sở dữ liệu!");
            return false;
        }
        try {
            String sql = "INSERT INTO phanquyen VALUES (?,?,?,?,?,?)";
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, phanQuyen.getQuyen());
            pre.setInt(2, phanQuyen.getNhapHang());
            pre.setInt(3, phanQuyen.getQlSanPham());
            pre.setInt(4, phanQuyen.getQlNhanVien());
            pre.setInt(5, phanQuyen.getQlKhachHang());
            pre.setInt(6, phanQuyen.getThongKe());

            boolean result = pre.executeUpdate() > 0;
            pre.close();
            conn.close();

            return result;
        } catch (Exception e) {
        }
        return false;
    }

    public boolean xoaQuyen(String phanQuyen) {
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            System.out.println("Lỗi kết nối cơ sở dữ liệu!");
            return false;
        }
        try {
            String sql1 = "UPDATE TaiKhoan SET Quyen='Default' WHERE Quyen=?";
            PreparedStatement pre1 = conn.prepareStatement(sql1);
            pre1.setString(1, phanQuyen);
            pre1.executeUpdate();
            String sql = "DELETE FROM phanquyen WHERE Quyen=?";
            PreparedStatement pre2 = conn.prepareStatement(sql);

            boolean result = pre2.executeUpdate() > 0;
            pre1.close();
            pre2.close();
            conn.close();

            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
