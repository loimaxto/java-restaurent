/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.retaurant.DAO;

/**
 *
 * @author light
 */

import com.example.retaurant.DTO.BanDTO;
import com.example.retaurant.utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BanDAO {

    private Connection connection;

    public BanDAO() {
        connection =  DBConnection.getConnection();
    }

    public BanDTO getBanById(int banId) throws SQLException {
        String sql = "SELECT * FROM ban WHERE ban_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, banId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToBanDTO(resultSet);
                }
                return null;
            }
        }
    }

    public List<BanDTO> getAllBans() throws SQLException {
        String sql = "SELECT * FROM ban";
        List<BanDTO> bans = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                bans.add(mapResultSetToBanDTO(resultSet));
            }
            return bans;
        }
    }

    public int addBan(BanDTO ban) throws SQLException {
        String sql = "INSERT INTO ban (ten_ban, tinh_trang_su_dung, trang_thai_ban) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, ban.getTenBan());
            statement.setInt(2, ban.getTinhTrangSuDung());
            statement.setInt(3, ban.getTrangThaiBan());
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Failed to retrieve generated key.");
                    }
                }
            } else {
                throw new SQLException("Failed to insert ban, no rows affected.");
            }
        }
    }

    public boolean updateBan(BanDTO ban) throws SQLException {
        String sql = "UPDATE ban SET ten_ban = ?, tinh_trang_su_dung = ?, trang_thai_ban = ?,id_hoadon_hientai = ? WHERE ban_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, ban.getTenBan());
            statement.setInt(2, ban.getTinhTrangSuDung());
            statement.setInt(3, ban.getTrangThaiBan());
            statement.setInt(4, ban.getBanId());
            statement.setInt(5, ban.getIdHoaDonHienTai());
            return statement.executeUpdate() > 0;
        }
    }

    public boolean deleteBan(int banId) throws SQLException {
        String sql = "DELETE FROM ban WHERE ban_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, banId);
            return statement.executeUpdate() > 0;
        }
    }

   private BanDTO mapResultSetToBanDTO(ResultSet resultSet) throws SQLException {
        BanDTO ban = new BanDTO();
        ban.setBanId(resultSet.getInt("ban_id"));
        ban.setTenBan(resultSet.getString("ten_ban"));
        ban.setTinhTrangSuDung(resultSet.getInt("tinh_trang_su_dung"));
        ban.setTrangThaiBan(resultSet.getInt("trang_thai_ban"));
        ban.setIdHoaDonHienTai(resultSet.getInt("id_hoadon_hientai"));
        return ban;
    }
   
    public static void main(String[] args) {
        BanDAO a= new BanDAO();
        
        try {
            System.out.println(a.getBanById(1).toString());
        } catch (SQLException ex) {
            Logger.getLogger(BanDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
