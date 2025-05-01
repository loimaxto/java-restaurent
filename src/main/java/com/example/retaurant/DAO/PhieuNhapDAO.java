package com.example.retaurant.DAO;

import com.example.retaurant.DTO.NhaCungCapDTO;
import com.example.retaurant.DTO.PhieuNhapDTO;
import com.example.retaurant.DTO.ChiTietPhieuNhapDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhieuNhapDAO {
    private Connection connection;

    public PhieuNhapDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean isIdExists(int pnId) throws SQLException {
        String sql = "SELECT pn_id FROM phieu_nhap WHERE pn_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, pnId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public int createPhieuNhap(PhieuNhapDTO phieuNhap) throws SQLException {
        // First check if ID exists
        if (isIdExists(phieuNhap.getPnId())) {
            throw new SQLException("ID already exists");
        }

        String sql = "INSERT INTO phieu_nhap (pn_id, ngay_nhap, ncc_id, nguoi_nhap_id, tong_tien) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, phieuNhap.getPnId());
            stmt.setTimestamp(2, new Timestamp(phieuNhap.getNgayNhap().getTime()));
            stmt.setInt(3, phieuNhap.getNccId());
            stmt.setInt(4, phieuNhap.getNguoiNhapId());
            stmt.setLong(5, phieuNhap.getTongTien());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating phieu nhap failed, no rows affected.");
            }
            
            return phieuNhap.getPnId();
        }
    }

    public boolean createChiTietPhieuNhap(ChiTietPhieuNhapDTO chiTiet) throws SQLException {
        String sql = "INSERT INTO ct_phieu_nhap (pn_id, nl_id, ten_sp, donGiaMoiDonVi, soLuong, tong_tien) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, chiTiet.getPnId());
            stmt.setInt(2, chiTiet.getNlId());
            stmt.setString(3, chiTiet.getTenSp());
            stmt.setLong(4, chiTiet.getDonGiaMoiDonVi());
            stmt.setLong(5, chiTiet.getSoLuong());
            stmt.setLong(6, chiTiet.getTongTien());
            
            return stmt.executeUpdate() > 0;
        }
    }

    public List<PhieuNhapDTO> getAllPhieuNhap() throws SQLException {
        List<PhieuNhapDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM phieu_nhap ORDER BY ngay_nhap DESC";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                PhieuNhapDTO pn = new PhieuNhapDTO(
                    rs.getInt("pn_id"),
                    rs.getTimestamp("ngay_nhap"),
                    rs.getInt("ncc_id"),
                    rs.getInt("nguoi_nhap_id"),
                    rs.getLong("tong_tien")
                );
                list.add(pn);
            }
        }
        return list;
    }

    public List<ChiTietPhieuNhapDTO> getChiTietByPhieuNhap(int pnId) throws SQLException {
        List<ChiTietPhieuNhapDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM ct_phieu_nhap WHERE pn_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, pnId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ChiTietPhieuNhapDTO ct = new ChiTietPhieuNhapDTO(
                        rs.getInt("pn_id"),
                        rs.getInt("nl_id"),
                        rs.getString("ten_sp"),
                        rs.getLong("donGiaMoiDonVi"),
                        rs.getLong("soLuong"),
                        rs.getLong("tong_tien")
                    );
                    list.add(ct);
                }
            }
        }
        return list;
    }

    public List<NhaCungCapDTO> getAllNhaCungCap() throws SQLException {
        List<NhaCungCapDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM nha_cung_cap";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
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
}