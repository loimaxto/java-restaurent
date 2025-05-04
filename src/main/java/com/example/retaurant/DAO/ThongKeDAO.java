/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.retaurant.DAO;

import com.example.retaurant.DTO.MonAnDTO;
import com.example.retaurant.DTO.ThongKe;
import com.example.retaurant.utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class ThongKeDAO {

    public ThongKe getThongKe() {
        ThongKe thongKe = new ThongKe();
        int[] tongThuQuy = new int[4];
        thongKe.setSoLuongMonAn(getSoLuongMonAn());
        thongKe.setSoLuongKH(getSoLuongKhachHang());
        thongKe.setTongDoanhThu(getDoanhThu());
        return thongKe;
    }

    private String[] getDateString(int nam, int quy) {
        int namBatDau = nam;
        int namKetThuc = nam;
        String thangBatDau = "01";
        String thangKetThuc = "04";
        String[] kq = new String[2];
        switch (quy) {
            case 1:
                thangBatDau = "01";
                thangKetThuc = "04";
                break;
            case 2:
                thangBatDau = "03";
                thangKetThuc = "07";
                break;
            case 3:
                thangBatDau = "06";
                thangKetThuc = "10";
                break;
            case 4:
                thangBatDau = "09";
                thangKetThuc = "01";
                namKetThuc++;
        }
        String strBatDau = Integer.toString(namBatDau) + thangBatDau + "01";
        String strKetThuc = Integer.toString(namKetThuc) + thangKetThuc + "01";
        kq[0] = strBatDau;
        kq[1] = strKetThuc;
        return kq;
    }

    private int getSoLuongMonAn() {
        int totalQuantity = 0;
        Connection conn = DBConnection.getConnection();
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT SUM(so_luong) FROM ct_hoa_don");
            if (rs.next()) {
                totalQuantity = rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            totalQuantity = -1;
        }
        return totalQuantity;
    }

    private int getSoLuongKhachHang() {
        Connection conn = DBConnection.getConnection();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM khach_hang");
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            return -1;
        }
        return 0;
    }

    public int getDoanhThu() {
        Connection conn = DBConnection.getConnection();
        try {
            String sql = "SELECT SUM(tong_gia) FROM hoa_don";
            PreparedStatement pre = conn.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getDoanhThuNgay(int ngay, int thang, int nam) {
        String sql = "SELECT SUM(tong_gia) FROM hoa_don WHERE DATE(thoi_gian) = ?";
        String dateStr = String.format("%04d-%02d-%02d", nam, thang, ngay); // yyyy-MM-dd

        try (Connection conn = DBConnection.getConnection(); PreparedStatement pre = conn.prepareStatement(sql)) {

            pre.setString(1, dateStr);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getDoanhThuKhoangNgay(String tuNgay, String denNgay) {
        Connection conn = DBConnection.getConnection();
        try {
            String sql = "SELECT SUM(tong_gia) FROM hoa_don WHERE thoi_gian BETWEEN CAST(? AS DATE) AND CAST(? AS DATE)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, tuNgay);
            ps.setString(2, denNgay);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double getDoanhThuThang(int thang, int nam) {
        Connection conn = DBConnection.getConnection();
        try {
            String sql = "SELECT SUM(tong_gia) FROM hoa_don WHERE MONTH(thoi_gian) = ? AND YEAR(thoi_gian) = ?";
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, thang);
            pre.setInt(2, nam);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double getDoanhThuThangCuThe(int thang, int nam) {
        Connection conn = DBConnection.getConnection();
        try {
            String d1 = nam + "-" + thang + "-01";
            String d2 = nam + "-" + (thang + 1) + "-01";
            String sql = "SELECT SUM(tong_gia) FROM hoa_don WHERE ngayLap BETWEEN CAST(? AS DATE) AND CAST(? AS DATE)";

            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, d1);
            pre.setString(2, d2);
            ResultSet rs = pre.executeQuery();

            while (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0f;
    }

    public ArrayList<MonAnDTO> getTopBanChay() {
        ArrayList<MonAnDTO> dsMonAn = new ArrayList<>();
        String sql = """
        SELECT ma.ten_sp AS ten_mon, SUM(cthd.so_luong) AS tong_so_luong
        FROM ct_hoa_don cthd
        JOIN mon_an ma ON cthd.spd_id = ma.sp_id
        GROUP BY ma.ten_sp
        ORDER BY tong_so_luong DESC
        LIMIT 5
    """;

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                MonAnDTO mon = new MonAnDTO();
                mon.setTenSp(rs.getString("ten_mon"));
                mon.setGiaSp(rs.getInt("tong_so_luong"));
                dsMonAn.add(mon);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dsMonAn;
    }

    public ArrayList<MonAnDTO> getTopBanChayTheoThang(int thang) {
        ArrayList<MonAnDTO> dsMonAn = new ArrayList<>();
        String sql = """
        SELECT ma.ten_sp AS ten_mon, SUM(cthd.so_luong) AS tong_so_luong
        FROM ct_hoa_don cthd
        JOIN mon_an ma ON cthd.spd_id = ma.sp_id
        JOIN hoa_don hd ON cthd.hd_id = hd.hd_id
        WHERE MONTH(hd.thoi_gian) = ? AND YEAR(hd.thoi_gian) = ?
        GROUP BY ma.ten_sp
        ORDER BY tong_so_luong DESC
        LIMIT 5
        """;

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            LocalDate now = LocalDate.now(); // năm hiện tại
            ps.setInt(1, thang);
            ps.setInt(2, now.getYear());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    MonAnDTO mon = new MonAnDTO();
                    mon.setTenSp(rs.getString("ten_mon"));
                    mon.setGiaSp(rs.getInt("tong_so_luong")); // lưu vào field GiaSp như cũ
                    dsMonAn.add(mon);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dsMonAn;
    }

    public ArrayList<MonAnDTO> getTopBanChayTheoKhoangThang(int startMonth, int endMonth) {
        ArrayList<MonAnDTO> ds = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            String sql = """
                    SELECT ma.ten_sp AS ten_mon, SUM(cthd.so_luong) AS tong_so_luong
                        FROM ct_hoa_don cthd
                        JOIN mon_an ma ON cthd.spd_id = ma.sp_id
                        JOIN hoa_don hd ON cthd.hd_id = hd.hd_id
                        WHERE MONTH(hd.thoi_gian) BETWEEN ? AND ?
                          AND YEAR(hd.thoi_gian) = ?
                        GROUP BY ma.ten_sp
                        ORDER BY tong_so_luong DESC
                        LIMIT 10
                """;

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, startMonth);
            stmt.setInt(2, endMonth);
            rs = stmt.executeQuery();

            while (rs.next()) {
                MonAnDTO mon = new MonAnDTO();
                mon.setTenSp(rs.getString("TenMonAn"));
                mon.setGiaSp(rs.getInt("SoLuongBan"));
                ds.add(mon);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Có thể thay bằng log
        }
        return ds;
    }
}
