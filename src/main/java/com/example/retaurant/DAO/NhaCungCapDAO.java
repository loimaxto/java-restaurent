package com.example.retaurant.DAO;

import com.example.retaurant.DTO.NhaCungCapDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NhaCungCapDAO {
    private Connection connection;

    public NhaCungCapDAO(Connection connection) {
        this.connection = connection;
    }

    // Get all suppliers
    public List<NhaCungCapDTO> getAllNhaCungCap() throws SQLException {
        List<NhaCungCapDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM nha_cung_cap";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                NhaCungCapDTO ncc = new NhaCungCapDTO(
                    rs.getInt("ncc_id"),
                    rs.getString("ten_ncc"),
                    rs.getString("sdt"),
                    rs.getString("dchi")
                );
                list.add(ncc);
            }
        }
        return list;
    }

    // Get supplier by ID
    public NhaCungCapDTO getNhaCungCapById(int id) throws SQLException {
        String sql = "SELECT * FROM nha_cung_cap WHERE ncc_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new NhaCungCapDTO(
                        rs.getInt("ncc_id"),
                        rs.getString("ten_ncc"),
                        rs.getString("sdt"),
                        rs.getString("dchi")
                    );
                }
            }
        }
        return null;
    }

    // Add new supplier
    public boolean addNhaCungCap(NhaCungCapDTO ncc) throws SQLException {
        String sql = "INSERT INTO nha_cung_cap (ten_ncc, sdt, dchi) VALUES (?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, ncc.getTen_ncc());
            stmt.setString(2, ncc.getSdt());
            stmt.setString(3, ncc.getDchi());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        ncc.setNcc_id(generatedKeys.getInt(1));
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // Update supplier
    public boolean updateNhaCungCap(NhaCungCapDTO ncc) throws SQLException {
        String sql = "UPDATE nha_cung_cap SET ten_ncc = ?, sdt = ?, dchi = ? WHERE ncc_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, ncc.getTen_ncc());
            stmt.setString(2, ncc.getSdt());
            stmt.setString(3, ncc.getDchi());
            stmt.setInt(4, ncc.getNcc_id());
            
            return stmt.executeUpdate() > 0;
        }
    }

    // Delete supplier
    public boolean deleteNhaCungCap(int id) throws SQLException {
        String sql = "DELETE FROM nha_cung_cap WHERE ncc_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    // Search suppliers by name
    public List<NhaCungCapDTO> searchNhaCungCapByName(String name) throws SQLException {
        List<NhaCungCapDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM nha_cung_cap WHERE ten_ncc LIKE ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + name + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    NhaCungCapDTO ncc = new NhaCungCapDTO(
                        rs.getInt("ncc_id"),
                        rs.getString("ten_ncc"),
                        rs.getString("sdt"),
                        rs.getString("dchi")
                    );
                    list.add(ncc);
                }
            }
        }
        return list;
    }
}
