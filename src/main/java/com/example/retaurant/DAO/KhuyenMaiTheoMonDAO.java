/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.retaurant.DAO;

import com.example.retaurant.DTO.KhuyenMaiTheoMonDTO;
import com.example.retaurant.utils.DBConnection;
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
public class KhuyenMaiTheoMonDAO {
    
    private Connection connection;

    public KhuyenMaiTheoMonDAO() {
        this.connection = DBConnection.getConnection();
    }

    
        
    public List<KhuyenMaiTheoMonDTO> getAllKhuyenMaiTheoMon() throws SQLException{
        String sql ="SELECT * FROM km_mon";
        List<KhuyenMaiTheoMonDTO> dskmmon = new ArrayList<>();
        try (Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)){
            
            while (resultSet.next()) {
               dskmmon.add(mapResultSetToKhuyenMaiTheoMonDTO(resultSet));
            }
            
            return dskmmon;
        }
    }
    
    public boolean addKhuyenMaiTheoMon(KhuyenMaiTheoMonDTO kmTheoMon) throws SQLException{
        String sql = "INSERT INTO km_mon (km_id, ma_sp, so_tien) VALUE (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, kmTheoMon.getKmId());
            statement.setInt(2, kmTheoMon.getMaSp());
            statement.setInt(3, kmTheoMon.getSoTien());
            return statement.executeUpdate()>0;
        }
        
    }
    
    public boolean updateKhuyenMaiTheoMon(KhuyenMaiTheoMonDTO kmTheoMon) throws SQLException{
        String sql = "UPDATE km_mon SET ma_sp = ?, so_tien= ? WHERE km_id = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, kmTheoMon.getMaSp());
            statement.setInt(2, kmTheoMon.getSoTien());
            statement.setInt(3, kmTheoMon.getKmId());
            return statement.executeUpdate() > 0;
        }
    }
    
    public boolean deleteKhuyenMaiTheoMon(Integer kmId) throws SQLException{
        String sql ="DELETE FROM km_mon WHERE km_id = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, kmId);
            return statement.executeUpdate() > 0;
        }
    }
    
    private KhuyenMaiTheoMonDTO mapResultSetToKhuyenMaiTheoMonDTO(ResultSet resultSet) throws SQLException {
        KhuyenMaiTheoMonDTO kmTheoMon = new KhuyenMaiTheoMonDTO();
        kmTheoMon.setKmId(resultSet.getInt("km_id"));
        kmTheoMon.setMaSp(resultSet.getInt("ma_sp"));
        kmTheoMon.setSoTien(resultSet.getInt("so_tien"));
        return kmTheoMon;
    }
    
}
