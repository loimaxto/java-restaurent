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
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CtHoaDonDAO {

    private Connection connection;

    public CtHoaDonDAO(Connection connection) {
        this.connection = connection;
    }

    public CtHoaDonDTO getCtHoaDonByIds(Integer hdId, Integer spdId) throws SQLException {
        String sql = "SELECT * FROM ct_hoa_don WHERE hd_id = ? AND spd_id = ?";
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
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToCtHoaDonDTO(resultSet);
                }
                return null;
            }
        }
    }

    public List<CtHoaDonDTO> getAllCtHoaDons() throws SQLException {
        String sql = "SELECT * FROM ct_hoa_don";
        List<CtHoaDonDTO> ctHoaDons = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                ctHoaDons.add(mapResultSetToCtHoaDonDTO(resultSet));
            }
            return ctHoaDons;
        }
    }

    public boolean addCtHoaDon(CtHoaDonDTO ctHoaDon) throws SQLException {
        String sql = "INSERT INTO ct_hoa_don (hd_id, spd_id, so_luong, gia_tai_luc_dat) VALUES (?, ?, ?, ?)";
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
            return statement.executeUpdate() > 0;
        }
    }

    public boolean updateCtHoaDon(CtHoaDonDTO ctHoaDon) throws SQLException {
        String sql = "UPDATE ct_hoa_don SET so_luong = ?, gia_tai_luc_dat = ? WHERE hd_id = ? AND spd_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            if (ctHoaDon.getSoLuong() != null) {
                statement.setInt(1, ctHoaDon.getSoLuong());
            } else {
                statement.setNull(1, Types.INTEGER);
            }
            if (ctHoaDon.getGiaTaiLucDat() != null) {
                statement.setInt(2, ctHoaDon.getGiaTaiLucDat());
            } else {
                statement.setNull(2, Types.INTEGER);
            }
            if (ctHoaDon.getHdId() != null) {
                statement.setInt(3, ctHoaDon.getHdId());
            } else {
                statement.setNull(3, Types.INTEGER);
            }
            if (ctHoaDon.getSpdId() != null) {
                statement.setInt(4, ctHoaDon.getSpdId());
            } else {
                statement.setNull(4, Types.INTEGER);
            }
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
        ctHoaDon.setHdId((Integer) resultSet.getObject("hd_id"));
        ctHoaDon.setSpdId((Integer) resultSet.getObject("spd_id"));
        ctHoaDon.setSoLuong((Integer) resultSet.getObject("so_luong"));
        ctHoaDon.setGiaTaiLucDat((Integer) resultSet.getObject("gia_tai_luc_dat"));
        return ctHoaDon;
    }
}
