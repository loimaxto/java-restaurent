package com.example.retaurant.DAO;

import com.example.retaurant.DTO.CongThucDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CongThucDAO {
    private Connection connection;
    
    public CongThucDAO(Connection connection) {
        this.connection = connection;
    }
    
    public boolean addCongThuc(CongThucDTO congThuc) throws SQLException {
        String sql = "INSERT INTO cong_thuc (sp_id, nl_id, so_luong) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, congThuc.getSpId());
            pstmt.setInt(2, congThuc.getNlId());
            pstmt.setInt(3, congThuc.getSoLuong());
            return pstmt.executeUpdate() > 0;
        }
    }
    
    public boolean updateCongThuc(CongThucDTO congThuc) throws SQLException {
        String sql = "UPDATE cong_thuc SET so_luong = ? WHERE sp_id = ? AND nl_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, congThuc.getSoLuong());
            pstmt.setInt(2, congThuc.getSpId());
            pstmt.setInt(3, congThuc.getNlId());
            return pstmt.executeUpdate() > 0;
        }
    }
    
    public boolean deleteCongThuc(Integer spId, Integer nlId) throws SQLException {
        String sql = "DELETE FROM cong_thuc WHERE sp_id = ? AND nl_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, spId);
            pstmt.setInt(2, nlId);
            return pstmt.executeUpdate() > 0;
        }
    }
    
    public List<CongThucDTO> getCongThucByMonAn(Integer spId) throws SQLException {
        List<CongThucDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM cong_thuc WHERE sp_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, spId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(new CongThucDTO(
                    rs.getInt("sp_id"),
                    rs.getInt("nl_id"),
                    rs.getInt("so_luong")
                ));
            }
        }
        return list;
    }
    
    public List<CongThucDTO> getAllCongThuc() throws SQLException {
        List<CongThucDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM cong_thuc";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new CongThucDTO(
                    rs.getInt("sp_id"),
                    rs.getInt("nl_id"),
                    rs.getInt("so_luong")
                ));
            }
        }
        return list;
    }
    
    public boolean exists(Integer spId, Integer nlId) throws SQLException {
        String sql = "SELECT 1 FROM cong_thuc WHERE sp_id = ? AND nl_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, spId);
            pstmt.setInt(2, nlId);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        }
    }
}