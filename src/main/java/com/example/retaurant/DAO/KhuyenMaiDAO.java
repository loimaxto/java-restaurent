package com.example.retaurant.DAO;

import com.example.retaurant.DTO.KhuyenMaiDTO;
import com.example.retaurant.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KhuyenMaiDAO {

    private Connection connection;

    public KhuyenMaiDAO() {
        this.connection = DBConnection.getConnection();
    }

    public KhuyenMaiDTO getKhuyenMaiById(Integer kmId) throws SQLException {
        String sql = "SELECT * FROM khuyen_mai WHERE km_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, kmId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToKhuyenMaiDTO(resultSet);
                }
                return null;
            }
        }
    }

    public List<KhuyenMaiDTO> getAllKhuyenMais() throws SQLException {
        String sql = "SELECT * FROM khuyen_mai";
        List<KhuyenMaiDTO> khuyenMais = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                khuyenMais.add(mapResultSetToKhuyenMaiDTO(resultSet));
            }
            return khuyenMais;
        }
    }

    public boolean addKhuyenMai(KhuyenMaiDTO khuyenMai) throws SQLException {
        String sql = "INSERT INTO khuyen_mai (km_id, ten_km, ngay_bd, ngay_kt, loai_km) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, khuyenMai.getKmId());
            statement.setString(2, khuyenMai.getTenKm());
            statement.setDate(3, khuyenMai.getNgayBd());
            statement.setDate(4, khuyenMai.getNgayKt());
            statement.setInt(5, khuyenMai.getLoaiKm());
            return statement.executeUpdate() > 0;
        }
    }

    public boolean updateKhuyenMai(KhuyenMaiDTO khuyenMai) throws SQLException {
        String sql = "UPDATE khuyen_mai SET ten_km = ?, ngay_bd = ?, ngay_kt = ?, loai_km = ? WHERE km_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, khuyenMai.getTenKm());
            statement.setDate(2, khuyenMai.getNgayBd());
            statement.setDate(3, khuyenMai.getNgayKt());
            statement.setInt(4, khuyenMai.getLoaiKm());
            statement.setInt(5, khuyenMai.getKmId());
            return statement.executeUpdate() > 0;
        }
    }

    public boolean deleteKhuyenMai(Integer kmId) throws SQLException {
        String sql = "DELETE FROM khuyen_mai WHERE km_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, kmId);
            return statement.executeUpdate() > 0;
        }
    }

    private KhuyenMaiDTO mapResultSetToKhuyenMaiDTO(ResultSet resultSet) throws SQLException {
        KhuyenMaiDTO khuyenMai = new KhuyenMaiDTO();
        khuyenMai.setKmId(resultSet.getInt("km_id"));
        khuyenMai.setTenKm(resultSet.getString("ten_km"));
        khuyenMai.setNgayBd(resultSet.getDate("ngay_bd"));
        khuyenMai.setNgayKt(resultSet.getDate("ngay_kt"));
        khuyenMai.setLoaiKm(resultSet.getInt("loai_km"));
        return khuyenMai;
    }
}