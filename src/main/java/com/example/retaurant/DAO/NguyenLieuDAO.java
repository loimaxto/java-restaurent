package com.example.retaurant.DAO;

import com.example.retaurant.DTO.NguyenLieuDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class NguyenLieuDAO {
    
    private Connection connection;

    public NguyenLieuDAO(Connection connection) {
        this.connection = connection;
    }
    
    public NguyenLieuDTO getNguyenLieuById(Integer nlId) throws SQLException {
        String sql = "SELECT * FROM nguyen_lieu WHERE nl_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, nlId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToNguyenLieuDTO(resultSet);
                }
                return null;
            }
        }       
    }
    
    public List<NguyenLieuDTO> getAllNguyenLieu() throws SQLException {
        String sql = "SELECT * FROM nguyen_lieu";
        List<NguyenLieuDTO> dsNguyenLieu = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                dsNguyenLieu.add(mapResultSetToNguyenLieuDTO(resultSet));
            }
            return dsNguyenLieu;
        }
    }
    
    public int addNguyenLieu(NguyenLieuDTO nguyenLieu) throws SQLException {
    String sql = "INSERT INTO nguyen_lieu (nl_id, ten_nl, don_vi, so_luong) VALUES (?, ?, ?, ?)";
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, nguyenLieu.getNlId());
        statement.setString(2, nguyenLieu.getTenNl());
        statement.setString(3, nguyenLieu.getDonVi());
        statement.setFloat(4, nguyenLieu.getSoLuong());
        
        int affectedRows = statement.executeUpdate();
        if (affectedRows > 0) {
            return nguyenLieu.getNlId();
        } else {
            throw new SQLException("Failed to insert nguyen lieu, no rows affected.");
        }
    }
}
    
    public boolean updateNguyenLieu(NguyenLieuDTO nguyenLieu) throws SQLException {
        String sql = "UPDATE nguyen_lieu SET ten_nl = ?, don_vi = ?, so_luong = ? WHERE nl_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nguyenLieu.getTenNl());
            statement.setString(2, nguyenLieu.getDonVi());
            statement.setFloat(3, nguyenLieu.getSoLuong());
            statement.setInt(4, nguyenLieu.getNlId());

            return statement.executeUpdate() > 0;
        }
    }
    
    public boolean deleteNguyenLieu(int nlId) throws SQLException {
        String sql = "DELETE FROM nguyen_lieu WHERE nl_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, nlId);
            return statement.executeUpdate() > 0;
        }
    }
    
    public boolean updateNguyenLieuQuantityNhap(int nlId, long quantityToAdd) throws SQLException {
    String sql = "UPDATE nguyen_lieu SET so_luong = so_luong + ? WHERE nl_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setLong(1, quantityToAdd);
        statement.setInt(2, nlId);
        return statement.executeUpdate() > 0;
        }
    }
    public boolean updateNguyenLieuQuantityXuat(int nlId, long quantityToSubtract) throws SQLException {
    String sql = "UPDATE nguyen_lieu SET so_luong = so_luong - ? WHERE nl_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setLong(1, quantityToSubtract);
        statement.setInt(2, nlId);
        return statement.executeUpdate() > 0;
        }
    }

    public List<NguyenLieuDTO> searchNguyenLieuByName(String name) throws SQLException {
        String sql = "SELECT * FROM nguyen_lieu WHERE ten_nl LIKE ?";
        List<NguyenLieuDTO> dsNguyenLieu = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%" + name + "%");
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    dsNguyenLieu.add(mapResultSetToNguyenLieuDTO(resultSet));
                }
                return dsNguyenLieu;
            }
        }
    }

    private NguyenLieuDTO mapResultSetToNguyenLieuDTO(ResultSet resultSet) throws SQLException {
        NguyenLieuDTO nguyenLieu = new NguyenLieuDTO();
        nguyenLieu.setNlId(resultSet.getInt("nl_id"));
        nguyenLieu.setTenNl(resultSet.getString("ten_nl"));
        nguyenLieu.setDonVi(resultSet.getString("don_vi"));
        nguyenLieu.setSoLuong(resultSet.getFloat("so_luong"));
        return nguyenLieu;
    }
}