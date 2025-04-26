package com.example.retaurant.DAO;

import com.example.retaurant.DTO.CTPhieuXuatDTO;
import com.example.retaurant.DTO.PhieuXuatDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhieuXuatDAO {
    private Connection connection;

    public PhieuXuatDAO(Connection connection) {
        this.connection = connection;
    }

    public int createPhieuXuat(PhieuXuatDTO phieuXuat) throws SQLException {
        String sql = "INSERT INTO phieu_xuat (ngay_xuat, nguoi_xuat_id) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setTimestamp(1, new Timestamp(phieuXuat.getNgayXuat().getTime()));
            stmt.setInt(2, phieuXuat.getNguoiXuatId());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating phieu xuat failed, no rows affected.");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating phieu xuat failed, no ID obtained.");
                }
            }
        }
    }

    public boolean createChiTietPhieuXuat(CTPhieuXuatDTO chiTiet) throws SQLException {
        String sql = "INSERT INTO ct_phieu_xuat (px_id, nl_id, ten_sp, soLuong) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, chiTiet.getPxId());
            stmt.setInt(2, chiTiet.getNlId());
            stmt.setString(3, chiTiet.getTenSp());
            stmt.setFloat(4, chiTiet.getSoLuong());
            
            return stmt.executeUpdate() > 0;
        }
    }

    public List<PhieuXuatDTO> getAllPhieuXuat() throws SQLException {
        List<PhieuXuatDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM phieu_xuat ORDER BY ngay_xuat DESC";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                PhieuXuatDTO px = new PhieuXuatDTO(
                    rs.getInt("px_id"),
                    rs.getTimestamp("ngay_xuat"),
                    rs.getInt("nguoi_xuat_id")
                );
                list.add(px);
            }
        }
        return list;
    }

    public List<CTPhieuXuatDTO> getChiTietByPhieuXuat(int pxId) throws SQLException {
        List<CTPhieuXuatDTO> list = new ArrayList<>();
        String sql = "SELECT ct.*, nl.don_vi FROM ct_phieu_xuat ct " +
                     "JOIN nguyen_lieu nl ON ct.nl_id = nl.nl_id " +
                     "WHERE ct.px_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, pxId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CTPhieuXuatDTO ct = new CTPhieuXuatDTO(
                        rs.getInt("px_id"),
                        rs.getInt("nl_id"),
                        rs.getString("ten_sp"),
                        rs.getFloat("soLuong")
                    );
                    ct.setDonVi(rs.getString("don_vi"));
                    list.add(ct);
                }
            }
        }
        return list;
    }
}