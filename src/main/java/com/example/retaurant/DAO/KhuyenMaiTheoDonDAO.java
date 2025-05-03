/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.retaurant.DAO;

import com.example.retaurant.DTO.KhuyenMaiTheoDonDTO;
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
public class KhuyenMaiTheoDonDAO {
    private Connection connection;

    public KhuyenMaiTheoDonDAO() {
        this.connection = DBConnection.getConnection();
    }
    
    
    public List<KhuyenMaiTheoDonDTO> getAllKhuyenMaiTheoDon() throws SQLException{
        String sql = "SELECT * FROM km_hoa_don";
        List<KhuyenMaiTheoDonDTO> dskmdon = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultset = statement.executeQuery(sql)){
            while (resultset.next()){
                dskmdon.add(mapResultSetToKhuyenMaiTheoDonDTO(resultset));
            }
            return dskmdon;
        } 
    }
    
    public boolean addKhuyenMaiTheoDon(KhuyenMaiTheoDonDTO kmTheoDon) throws SQLException {
        String sql = "INSERT INTO km_hoa_don (km_id, phan_tram) VALUE (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, kmTheoDon.getKmId());
            statement.setInt(2, kmTheoDon.getPhanTram());
            return statement.executeUpdate() > 0;
        }
    }
    
    public boolean  updateKhuyenMaiTheoDon(KhuyenMaiTheoDonDTO kmTheoDon) throws SQLException{
        String sql = "UPDATE km_hoa_don SET phan_tram = ? WHERE km_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(0, 0);
            statement.setInt(0, 0);
            return statement.executeUpdate() >0;
        }
    }
    
    public boolean deleteKhuyenMaiTheoMon(Integer kmId) throws SQLException {
        String sql = "DELETE FROM km_hoa_don where km_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, kmId);
            return statement.executeUpdate() > 0;
        }
    }
    
    private KhuyenMaiTheoDonDTO mapResultSetToKhuyenMaiTheoDonDTO(ResultSet resultSet) throws SQLException {
        KhuyenMaiTheoDonDTO kmTheoDon = new KhuyenMaiTheoDonDTO();
        kmTheoDon.setKmId(resultSet .getInt("km_id"));
        kmTheoDon.setPhanTram(resultSet.getInt("phan_tram"));
        return kmTheoDon;
    }
    
}
