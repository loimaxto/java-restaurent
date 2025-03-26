package com.example.retaurant.DAO;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author light
 */
import com.example.retaurant.DTO.HoaDonDTO;
import com.example.retaurant.utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HoaDonDAO {

    private Connection connection;
    public HoaDonDAO( ) {
        this.connection = DBConnection.getConnection();
    }

    public HoaDonDTO getBillById(Integer hdId) throws SQLException {
        String sql = "SELECT * FROM hoa_don WHERE hd_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, hdId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToBillDTO(resultSet);
                }
                return null;
            }
        }
    }

    public List<HoaDonDTO> getAllBills() throws SQLException {
        String sql = "SELECT * FROM hoa_don";
        List<HoaDonDTO> bills = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                bills.add(mapResultSetToBillDTO(resultSet));
            }
            return bills;
        }
    }

    public int addBill(HoaDonDTO bill) throws SQLException {
        String sql = "INSERT INTO hoa_don (thoi_gian, ghi_chu, tong_gia, kh_id, ban_id, nguoi_lap_id, km_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setTimestamp(1, bill.getThoiGian());
            statement.setByte(2, bill.getGhiChu());
            statement.setInt(3, bill.getTongGia());
            if (bill.getKhId() != null) {
                statement.setInt(4, bill.getKhId());
            } else {
                statement.setNull(4, Types.INTEGER);
            }
            if (bill.getBanId() != null) {
                statement.setInt(5, bill.getBanId());
            } else {
                statement.setNull(5, Types.INTEGER);
            }
            if (bill.getNguoiLapId() != null) {
                statement.setInt(6, bill.getNguoiLapId());
            } else {
                statement.setNull(6, Types.INTEGER);
            }
            if (bill.getKmId() != null) {
                statement.setInt(7, bill.getKmId());
            } else {
                statement.setNull(7, Types.INTEGER);
            }

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
                throw new SQLException("Failed to insert bill, no rows affected.");
            }
        }
    }

    public boolean updateBill(HoaDonDTO bill) throws SQLException {
        String sql = "UPDATE hoa_don SET thoi_gian = ?, ghi_chu = ?, tong_gia = ?, kh_id = ?, ban_id = ?, nguoi_lap_id = ?, km_id = ? WHERE hd_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setTimestamp(1, bill.getThoiGian());
            statement.setByte(2, bill.getGhiChu());
            statement.setInt(3, bill.getTongGia());
            if (bill.getKhId() != null) {
                statement.setInt(4, bill.getKhId());
            } else {
                statement.setNull(4, Types.INTEGER);
            }
            if (bill.getBanId() != null) {
                statement.setInt(5, bill.getBanId());
            } else {
                statement.setNull(5, Types.INTEGER);
            }
            if (bill.getNguoiLapId() != null) {
                statement.setInt(6, bill.getNguoiLapId());
            } else {
                statement.setNull(6, Types.INTEGER);
            }
            if (bill.getKmId() != null) {
                statement.setInt(7, bill.getKmId());
            } else {
                statement.setNull(7, Types.INTEGER);
            }

            statement.setInt(8, bill.getHdId());
            return statement.executeUpdate() > 0;
        }
    }

    public boolean deleteBill(Integer hdId) throws SQLException {
        String sql = "DELETE FROM hoa_don WHERE hd_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, hdId);
            return statement.executeUpdate() > 0;
        }
    }
   public int addDefaultHoaDon(int tableId) {
    String sql = "INSERT INTO hoa_don (ban_id, nguoi_lap_id) VALUES (?, ?)";
    try (PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
        statement.setInt(1, tableId);
        statement.setInt(2, 2); // cố định người tạo là 2

        int affectedRows = statement.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Inserting hoa_don failed, no rows affected.");
        }
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Failed to retrieve the generated key.");
            }
        }
    } catch (SQLException e) {
        System.err.println("SQL Exception occurred while adding default hoa_don: " + e.getMessage());
        e.printStackTrace();
        return -1; // Return a default value to indicate failure
    }
}

    private HoaDonDTO mapResultSetToBillDTO(ResultSet resultSet) throws SQLException {
        HoaDonDTO bill = new HoaDonDTO();
        bill.setHdId(resultSet.getInt("hd_id"));
        bill.setThoiGian(resultSet.getTimestamp("thoi_gian"));
        bill.setGhiChu(resultSet.getByte("ghi_chu"));
        bill.setTongGia(resultSet.getInt("tong_gia"));
        bill.setKhId(resultSet.getInt("kh_id"));
        bill.setBanId( resultSet.getInt("ban_id"));
        bill.setNguoiLapId( resultSet.getInt("nguoi_lap_id"));
        bill.setKmId( resultSet.getInt("km_id"));
        return bill;
    }

    
}
