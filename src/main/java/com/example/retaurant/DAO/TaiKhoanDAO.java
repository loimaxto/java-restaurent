/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.retaurant.DAO;

import com.example.retaurant.DTO.TaiKhoan;
import com.example.retaurant.BUS.DangNhapBUS;
import com.example.retaurant.utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author ASUS
 */
public class TaiKhoanDAO {

    public boolean themTaiKhoan(int maNV, String tenDangNhap, String quyen) {
        Connection conn = DBConnection.getConnection();

        if (conn == null) {
            System.out.println("Lỗi kết nối cơ sở dữ liệu!");
            return false;
        }

        // Kiểm tra trùng mã nhân viên
        if (kiemTraTrungMaNV(maNV)) {
            System.out.println("Trùng mã nhân viên: " + maNV);
            return false;
        }

        // Kiểm tra trùng username
        if (kiemTraTrungTenDangNhap(tenDangNhap)) {
            System.out.println("Trùng username: " + tenDangNhap);
            return false;
        }

        try {
            String sql = "INSERT INTO tai_khoan(tk_id, username, password, quyen) VALUES (?, ?, ?, ?)";
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, maNV);
            pre.setString(2, tenDangNhap);
            pre.setString(3, tenDangNhap); // mật khẩu mặc định = tên đăng nhập
            pre.setString(4, quyen);
            int rows = pre.executeUpdate();

            System.out.println("Số dòng thêm thành công: " + rows);
            return rows > 0;

        } catch (Exception e) {
            // In lỗi chi tiết
            System.err.println("Lỗi thêm tài khoản: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public boolean kiemTraTrungTenDangNhap(String tenDangNhap) {
        Connection conn = DBConnection.getConnection();
        try {
            String sql = "SELECT * FROM tai_khoan WHERE username = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, tenDangNhap);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            System.err.println("Lỗi kiểm tra tên đăng nhập: " + e.getMessage());
            return false;
        }
    }

//    public boolean kiemTraTrungTenDangNhap(String tenDangNhap) {
//        Connection conn = DBConnection.getConnection();
//
//        if (conn == null) {
//            System.out.println("Lỗi kết nối cơ sở dữ liệu!");
//            return false;
//        }
//        try {
//            String sql = "SELECT * FROM tai_khoan WHERE username = '" + tenDangNhap + "'";
//            Statement st = conn.createStatement();
//            ResultSet rs = st.executeQuery(sql);
//            return rs.next();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
    public boolean kiemTraTrungMaNV(int maNV) {
        Connection conn = DBConnection.getConnection();
        String sql = "SELECT * FROM tai_khoan WHERE tk_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, maNV);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            return false;
        }
    }

    public String getTenDangNhapTheoMa(int maNV) {
        Connection conn = DBConnection.getConnection();

        if (conn == null) {
            System.out.println("Lỗi kết nối cơ sở dữ liệu!");
            return null;
        }
        try {
            String sql = "SELECT username FROM tai_khoan WHERE tk_id=" + maNV;
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception e) {
        }
        return "";
    }

    public boolean datLaiMatKhau(int maNV, String tenDangNhap) {
        Connection conn = DBConnection.getConnection();

        if (conn == null) {
            System.out.println("Lỗi kết nối cơ sở dữ liệu!");
            return false;
        }
        try {
            String sql = "UPDATE tai_khoan SET password=? WHERE tk_id=?";
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, tenDangNhap);
            pre.setInt(2, maNV);
            return pre.executeUpdate() > 0;
        } catch (Exception e) {
        }
        return false;
    }

    public boolean datLaiQuyen(int maNV, String quyen) {
        Connection conn = DBConnection.getConnection();

        if (conn == null) {
            System.out.println("Lỗi kết nối cơ sở dữ liệu!");
            return false;
        }
        try {
            String sql = "UPDATE tai_khoan SET quyen=? WHERE tk_id=?";
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, quyen);
            pre.setInt(2, maNV);
            return pre.executeUpdate() > 0;
        } catch (Exception e) {
        }
        return false;
    }

    public String getQuyenTheoMa(int maNV) {
        Connection conn = DBConnection.getConnection();

        if (conn == null) {
            System.out.println("Lỗi kết nối cơ sở dữ liệu!");
            return null;
        }
        try {
            String sql = "SELECT quyen FROM tai_khoan WHERE tk_id=" + maNV;
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception e) {
        }
        return "";
    }

    public boolean khoaTaiKhoan(int maNV) {
        Connection conn = DBConnection.getConnection();

        if (conn == null) {
            System.out.println("Lỗi kết nối cơ sở dữ liệu!");
            return false;
        }
        try {
            String sql = "UPDATE tai_khoan SET trang_thai=0 WHERE tk_id=?";
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, maNV);
            return pre.executeUpdate() > 0;
        } catch (Exception e) {
        }
        return false;
    }

    public boolean moKhoaTaiKhoan(int maNV) {
        Connection conn = DBConnection.getConnection();

        if (conn == null) {
            System.out.println("Lỗi kết nối cơ sở dữ liệu!");
            return false;
        }
        try {
            String sql = "UPDATE tai_khoan SET trang_thai=1 WHERE tk_id=?";
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, maNV);
            return pre.executeUpdate() > 0;
        } catch (Exception e) {
        }
        return false;
    }

    public boolean doiMatKhau(String matKhauCu, String matKhauMoi) {
        Connection conn = DBConnection.getConnection();

        if (conn == null) {
            System.out.println("Lỗi kết nối cơ sở dữ liệu!");
            return false;
        }
        try {
            String sql = "UPDATE tai_khoan SET password=? WHERE tk_id=? AND password=?";
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, matKhauMoi);
            pre.setInt(2, DangNhapBUS.taiKhoanLogin.getMaTaiKhoan());
            pre.setString(3, matKhauCu);
            return pre.executeUpdate() > 0;
        } catch (Exception e) {
        }
        return false;
    }

    public int getTrangThai(int ma) {
        Connection conn = DBConnection.getConnection();

        if (conn == null) {
            System.out.println("Lỗi kết nối cơ sở dữ liệu!");
            return -1;
        }
        try {
            String sql = "SELECT trang_thai FROM tai_khoan WHERE tk_id=" + ma;
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
        }
        return -1;
    }
}
