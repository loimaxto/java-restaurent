/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.retaurant.DAO;

/**
 *
 * @author light
 */
import com.example.retaurant.DTO.CtHoaDonDTO;
import com.example.retaurant.DTO.CtSanPhamThanhToanDTO;
import com.example.retaurant.utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CtHoaDonDAO {

    private Connection connection;

    public CtHoaDonDAO() {
        this.connection = DBConnection.getConnection();
    }

    public List<CtHoaDonDTO> getAllCtHoaDonByHoaDonId(int hoadonId) throws SQLException {
        String sql = "SELECT * FROM ct_hoa_don WHERE hd_id = ?"; // Semicolon removed
        List<CtHoaDonDTO> ctHoaDons = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, hoadonId);
            try (ResultSet resultSet = statement.executeQuery()) { // Executing the prepared statement
                while (resultSet.next()) {
                    ctHoaDons.add(mapResultSetToCtHoaDonDTO(resultSet));
                }
            }
            return ctHoaDons;
        }
    }

    public List<CtSanPhamThanhToanDTO> getAllCtHoaDonByHoaDonIdCoTenSp(int hoadonId) throws SQLException {
        String sql = "SELECT * FROM ct_hoa_don\n"
                + "INNER JOIN mon_an ON ct_hoa_don.spd_id = mon_an.sp_id\n"
                + "WHERE ct_hoa_don.hd_id = ?";
        List<CtSanPhamThanhToanDTO> ctHoaDons = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, hoadonId);
            try (ResultSet rs = statement.executeQuery()) { // Executing the prepared statement
                while (rs.next()) {
                    ctHoaDons.add(
                            new CtSanPhamThanhToanDTO(
                                    rs.getInt("hd_id"),
                                    rs.getInt("spd_id"),
                                    rs.getInt("so_luong"),
                                    rs.getInt("gia_tai_luc_dat"),
                                    rs.getInt("tong_tien_ct"),
                                    rs.getString("ten_sp"))
                    );
                }
            }
            return ctHoaDons;
        }
    }
    public boolean addCtHoaDon(CtHoaDonDTO ctHoaDon) throws SQLException {
        String sql = "INSERT INTO ct_hoa_don (hd_id, spd_id, so_luong, gia_tai_luc_dat,tong_tien_ct) VALUES (?, ?, ?, ?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            if (ctHoaDon.getHdId() != null) {
                statement.setInt(1, ctHoaDon.getHdId());
            } else {
                statement.setNull(1, Types.INTEGER);
            }
            if (ctHoaDon.getSpdId() != null) {
                statement.setInt(2, ctHoaDon.getSpdId());
            } else {
                statement.setNull(2, Types.INTEGER);
            }
            if (ctHoaDon.getSoLuong() != null) {
                statement.setInt(3, ctHoaDon.getSoLuong());
            } else {
                statement.setNull(3, Types.INTEGER);
            }
            if (ctHoaDon.getGiaTaiLucDat() != null) {
                statement.setInt(4, ctHoaDon.getGiaTaiLucDat());
            } else {
                statement.setNull(4, Types.INTEGER);
            }
            if (ctHoaDon.getTongTienCt() != null) {
                statement.setInt(5, ctHoaDon.getTongTienCt());
            } else {
                statement.setNull(5, Types.INTEGER);
            }
            return statement.executeUpdate() > 0;
        }
    }

    public boolean updateCtHoaDonThemMonVaoHoaDon(CtHoaDonDTO ctHoaDon) throws SQLException {
        String sql = "UPDATE ct_hoa_don SET so_luong = ?, gia_tai_luc_dat = ?,gia_tai_luc_dat =? WHERE hd_id = ? AND spd_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, ctHoaDon.getSoLuong());
            if (ctHoaDon.getGiaTaiLucDat() != null) {
                statement.setInt(2, ctHoaDon.getGiaTaiLucDat());
                statement.setInt(3, ctHoaDon.getGiaTaiLucDat() * ctHoaDon.getSoLuong());
            } else {
                statement.setNull(2, Types.INTEGER);
                statement.setNull(3, Types.INTEGER);
            }

            statement.setInt(4, ctHoaDon.getHdId());
            statement.setInt(5, ctHoaDon.getSpdId());

            return statement.executeUpdate() > 0;
        }
    }

    public boolean deleteCtHoaDon(Integer hdId, Integer spdId) throws SQLException {
        String sql = "DELETE FROM ct_hoa_don WHERE hd_id = ? AND spd_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            if (hdId != null) {
                statement.setInt(1, hdId);
            } else {
                statement.setNull(1, Types.INTEGER);
            }
            if (spdId != null) {
                statement.setInt(2, spdId);
            } else {
                statement.setNull(2, Types.INTEGER);
            }
            return statement.executeUpdate() > 0;
        }
    }

    private CtHoaDonDTO mapResultSetToCtHoaDonDTO(ResultSet resultSet) throws SQLException {
        CtHoaDonDTO ctHoaDon = new CtHoaDonDTO();
        ctHoaDon.setHdId(resultSet.getInt("hd_id"));
        ctHoaDon.setSpdId(resultSet.getInt("spd_id"));
        ctHoaDon.setSoLuong(resultSet.getInt("so_luong"));
        ctHoaDon.setGiaTaiLucDat(resultSet.getInt("gia_tai_luc_dat"));
        ctHoaDon.setTongTienCt(resultSet.getInt("tong_tien_ct"));
        return ctHoaDon;
    }
}
