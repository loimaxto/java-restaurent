/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.retaurant.DAO;
import com.example.retaurant.DTO.NhanVien;
import com.example.retaurant.BUS.NhanVienBUS;
import com.example.retaurant.utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class NhanVienDAO {
    public ArrayList<NhanVien> getDanhSachNhanVien() {
        Connection conn = DBConnection.getConnection();

        if (conn == null) {
            System.out.println("Lỗi kết nối cơ sở dữ liệu!");
            return null;
        }
        try {
            String sql = "SELECT * FROM nhan_vien";
            PreparedStatement pre = conn.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            ArrayList<NhanVien> dssv = new ArrayList<>();
            while (rs.next()) {
                NhanVien nv = new NhanVien();

                nv.setMaNhanvien(rs.getInt(1));
                nv.setHoTen(rs.getString(2));
                nv.setGioiTinh(rs.getString(3));
                nv.setChucVu(rs.getString(4));
                nv.setMaTaiKhoan(rs.getInt(5));

                dssv.add(nv);
            }
            return dssv;
        } catch (SQLException e) {
        }

        return null;
    }

    public NhanVien getNhanVien(int maNV) {
        Connection conn = DBConnection.getConnection();

        if (conn == null) {
            System.out.println("Lỗi kết nối cơ sở dữ liệu!");
            return null;
        }
        NhanVien nv = null;
        try {
            String sql = "SELECT * FROM nhan_vien WHERE nv_id=?";
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(0, maNV);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                nv = new NhanVien();
                nv.setMaNhanvien(rs.getInt(1));
                nv.setHoTen(rs.getString(2));
                nv.setGioiTinh(rs.getString(3));
                nv.setChucVu(rs.getString(4));
                nv.setMaTaiKhoan(rs.getInt(5));
            }
        } catch (SQLException e) {
            return null;
        }

        return nv;
    }

    public boolean updateNhanVien(NhanVien nv) {
        Connection conn = DBConnection.getConnection();

        if (conn == null) {
            System.out.println("Lỗi kết nối cơ sở dữ liệu!");
            return false;
        }
        boolean result = false;
        try {
            String sql = "UPDATE nhanvien SET ho_ten=?, gioi_tinh=?, chuc_vu=?, tk_id=? WHERE nv_id=?";
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, nv.getHoTen());
            pre.setString(2, nv.getGioiTinh());
            pre.setString(3, nv.getChucVu());
            pre.setInt(4, nv.getMaTaiKhoan());
            pre.setInt(5, nv.getMaNhanvien());
            result = pre.executeUpdate() > 0;
        } catch (SQLException ex) {
            return false;
        }
        return result;
    }

    public boolean deleteNhanVien(int maNV) {
        Connection conn = DBConnection.getConnection();

        if (conn == null) {
            System.out.println("Lỗi kết nối cơ sở dữ liệu!");
            return false;
        }
        boolean result = false;
        try {
            String sql = "DELETE FROM nhan_vien WHERE nv_id=?";
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, maNV);
            result = pre.executeUpdate() > 0;
        } catch (SQLException ex) {
            return false;
        }
        return result;
    }

    public boolean themNhanVien(NhanVien nv) {
        Connection conn = DBConnection.getConnection();

        if (conn == null) {
            System.out.println("Lỗi kết nối cơ sở dữ liệu!");
            return false;
        }
        boolean result = false;
        try {
            String sql = "INSERT INTO NhanVien(ho_ten, gioi_tinh, chuc_vu, tk_id) " +
                    "VALUES(?, ?, ?, ?)";
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, nv.getHoTen());
            pre.setString(2, nv.getGioiTinh());
            pre.setString(3, nv.getChucVu());
            pre.setInt(4, nv.getMaTaiKhoan());
            result = pre.executeUpdate() > 0;
        } catch (SQLException ex) {
            return false;
        }
        return result;
    }

    public boolean nhapExcel(NhanVien nv) {
        Connection conn = DBConnection.getConnection();

        if (conn == null) {
            System.out.println("Lỗi kết nối cơ sở dữ liệu!");
            return false;
        }
        try {
            String sql = "DELETE * FROM nhanvien; " +
                    "INSERT INTO nhan_vien(ho_ten, gioi_tinh, chuc_vu, tk_id) " +
                    "VALUES(?, ?, ?, ?)";
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, nv.getHoTen());
            pre.setString(2, nv.getGioiTinh());
            pre.setString(3, nv.getChucVu());
            pre.setInt(4, nv.getMaTaiKhoan());
            return true;
        } catch (SQLException ex) {
        }
        return false;
    }
}
