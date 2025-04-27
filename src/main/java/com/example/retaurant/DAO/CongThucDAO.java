/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.retaurant.DAO;


import com.example.retaurant.DTO.CongThucDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class CongThucDAO {
    
    private Connection connection;

    public CongThucDAO(Connection connection) {
        this.connection = connection;
    }
    
    public List<CongThucDTO> getAllDsCongThuc() throws SQLException {
        String sql = "SELECT * FROM cong_thuc";
        List<CongThucDTO> dscongthuc = new ArrayList<>();
        try( Statement statement = connection.createStatement(); ResultSet resultset = statement.executeQuery(sql)){
            while (resultset.next()){
                dscongthuc.add(mapResultSetToCongThucDTO(resultset));
            }
        }
        return dscongthuc;
        
    }
    public int addCongThuc(CongThucDTO congthuc) throws SQLException{
        String sql ="INSERT INTO Cong_thuc (sp_id, nl_id, soLuong) VALUES (?, ?, ?)";
        try(PreparedStatement statement= connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
             statement.setInt(1, congthuc.getSpid());
             statement.setInt(2, congthuc.getNlid());
             statement.setInt(3, congthuc.getSoluong());
             
             int affectedRows = statement.executeUpdate();
             if(affectedRows>0){
                 try (ResultSet generatedKeys = statement.getGeneratedKeys()){
                     if(generatedKeys.next()){
                         return generatedKeys.getInt(1);
                     }
                     else{
                         throw new SQLException("Failed to retrieve generated key.");
                     }
                         
                 }
             }
             else{
                 throw new SQLException("ailed to insert congthuc, no rows affected.");
             }
             
        }
    }
    public boolean deleteCongThuc(int sp_Id,int nl_Id) throws SQLException{
        String sql = "DELETE FROM cong_thuc WHERE sp_id = ? AND nl_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, sp_Id);
            statement.setInt(2, nl_Id);
            return statement.executeUpdate() > 0;
        }
    }
    
    public boolean updateCongThuc(CongThucDTO congthuc) throws SQLException {
        String sql = "UPDATE cong_thuc SET soLuong = ? WHERE sp_id = ? AND nl_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, congthuc.getSoluong());
            statement.setInt(2, congthuc.getSpid());
            statement.setInt(3, congthuc.getNlid());

            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        }
    }
        
    
    private CongThucDTO mapResultSetToCongThucDTO(ResultSet resultSet) throws SQLException {
        CongThucDTO ct = new CongThucDTO();
        ct.setSpid(resultSet.getInt("sp_id"));
        ct.setNlid(resultSet.getInt("nl_id"));
        ct.setSoluong(resultSet.getInt("soLuong"));
        return ct;
    }
    
}
    
  
    
