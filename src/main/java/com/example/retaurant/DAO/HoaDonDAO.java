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

    public HoaDonDAO() {
        this.connection = DBConnection.getConnection();
    }

    public HoaDonDTO getBillById(Integer hdId) throws SQLException {
        String sql = "SELECT * FROM hoa_don WHERE hd_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, hdId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
//                    HoaDonDTO hoadon = new HoaDonDTO();
//                    hoadon.setHdId(resultSet.getInt("hd_id"));
//                    hoadon.setGhiChu(resultSet.getBy);
                    return mapResultSetToBillDTO(resultSet);
                }
                return null;
            }
        }
    }

    public List<HoaDonDTO> getAllBills() throws SQLException {
        String sql = "SELECT * FROM hoa_don";
        List<HoaDonDTO> bills = new ArrayList<>();
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
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

            // Handle nulls for Timestamp, Byte and Integer.
            setNullableTimestamp(statement, 1, bill.getThoiGian());
            setNullableByte(statement, 2, bill.getGhiChu());
            setNullableInt(statement, 3, bill.getTongGia());

            // Helper method to set integer parameters, handling nulls
            setNullableInt(statement, 4, bill.getKhId());
            setNullableInt(statement, 5, bill.getBanId());
            setNullableInt(statement, 6, bill.getNguoiLapId());
            setNullableInt(statement, 7, bill.getKmId());

            statement.setInt(8, bill.getHdId());
            return statement.executeUpdate() > 0;
        }
    }

    // Helper method to set integer parameters, handling nulls
    private void setNullableInt(PreparedStatement statement, int parameterIndex, Integer value) throws SQLException {
        if (value != null) {
            statement.setInt(parameterIndex, value);
        } else {
            statement.setNull(parameterIndex, Types.INTEGER);
        }
    }

    // Helper method to set Timestamp parameters, handling nulls
    private void setNullableTimestamp(PreparedStatement statement, int parameterIndex, Timestamp value) throws SQLException {
        if (value != null) {
            statement.setTimestamp(parameterIndex, value);
        } else {
            statement.setNull(parameterIndex, Types.TIMESTAMP);
        }
    }

    // Helper method to set Byte parameters, handling nulls
    private void setNullableByte(PreparedStatement statement, int parameterIndex, Byte value) throws SQLException {
        if (value != null) {
            statement.setByte(parameterIndex, value);
        } else {
            statement.setNull(parameterIndex, Types.TINYINT); // or Types.SMALLINT or Types.INTEGER, depending on your database type.
        }
    }

    public boolean deleteBill(Integer hdId) throws SQLException {
        String sql = "DELETE FROM hoa_don WHERE hd_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, hdId);
            return statement.executeUpdate() > 0;
        }
    }

    public int addDefaultHoaDon(int tableId, int creatorId) {
        String sql = "INSERT INTO hoa_don (ban_id, nguoi_lap_id) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, tableId);
            statement.setInt(2, creatorId); // cố định người tạo là 2

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
        Object tongGiaObj = resultSet.getObject("tong_gia");
        bill.setTongGia(tongGiaObj != null ? ((Number) tongGiaObj).intValue() : null);
        Object khIdObj = resultSet.getObject("kh_id");
        bill.setKhId(khIdObj != null ? ((Number) khIdObj).intValue() : null);
        Object banIdObj = resultSet.getObject("ban_id");
        bill.setBanId(banIdObj != null ? ((Number) banIdObj).intValue() : null);
        Object nguoiLapIdObj = resultSet.getObject("nguoi_lap_id");
        bill.setNguoiLapId(nguoiLapIdObj != null ? ((Number) nguoiLapIdObj).intValue() : null);
        Object kmIdObj = resultSet.getObject("km_id");
        bill.setKmId(kmIdObj != null ? ((Number) kmIdObj).intValue() : null);
        return bill;
    }

}
