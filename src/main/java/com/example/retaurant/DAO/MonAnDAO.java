/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.retaurant.DAO;

import com.example.retaurant.DTO.MonAnDTO;
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
public class MonAnDAO {

    private Connection connection;

    public MonAnDAO(Connection connection) {
        this.connection = connection;
    }

    public MonAnDTO getMonAnById(Integer spId) throws SQLException {
        String sql = "SELECT * FROM mon_an WHERE sp_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, spId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToMonAnDTO(resultSet);
                }
                return null;
            }
        }
    }

    public List<MonAnDTO> getAllDsMonAn() throws SQLException {
        String sql = "SELECT * FROM mon_an";
        List<MonAnDTO> dsMonAn = new ArrayList<>();
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                dsMonAn.add(mapResultSetToMonAnDTO(resultSet));

            }
            return dsMonAn;
        }
    }

    public List<MonAnDTO> searchMonAnByName(String keyword) throws SQLException {
        String sql = "SELECT * FROM mon_an where ten_sp Like ?";
        
        List<MonAnDTO> dsMonAn = new ArrayList<>();
       try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setString(1, "%" + keyword + "%");
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            dsMonAn.add(mapResultSetToMonAnDTO(resultSet));
        }
        return dsMonAn;
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return dsMonAn;
}

    public int addMonAn(MonAnDTO monAn) throws SQLException {
        String sql = "INSERT INTO mon_an (ten_sp, gia_sp, trang_thai) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, monAn.getTenSp());
            statement.setInt(2, monAn.getGiaSp());
            statement.setInt(3, monAn.getTrangThai());

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
                throw new SQLException("Failed to insert mon an, no rows affected.");
            }

        }
    }

    public boolean updateMonAn(MonAnDTO monAn) throws SQLException {
        String sql = "UPDATE mon_an SET ten_sp = ?, gia_sp = ?, trang_thai = ? WHERE sp_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, monAn.getTenSp());
            statement.setInt(2, monAn.getGiaSp());
            statement.setInt(3, monAn.getTrangThai());
            statement.setInt(4, monAn.getSpId());

            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        }
    }

    public boolean deleteMonAn(int spId) throws SQLException {
        String sql = "UPDATE mon_an SET trang_thai = ? WHERE sp_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, 0);
            statement.setInt(2, spId);

            return statement.executeUpdate() > 0;
        }
    }

    private MonAnDTO mapResultSetToMonAnDTO(ResultSet resultSet) throws SQLException {
        MonAnDTO mon_an = new MonAnDTO();
        mon_an.setSpId(resultSet.getInt("sp_id"));
        mon_an.setTenSp(resultSet.getString("ten_sp"));
        mon_an.setGiaSp(resultSet.getInt("gia_sp"));
        mon_an.setTrangThai(resultSet.getInt("trang_thai"));
        return mon_an;
    }
}
