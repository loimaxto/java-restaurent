package com.example.retaurant.BUS;

import com.example.retaurant.DTO.*;
import com.example.retaurant.DAO.PhieuXuatDAO;
import com.example.retaurant.utils.DBConnection;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class PhieuXuatBUS {
    private PhieuXuatDAO phieuXuatDAO;
    private Connection connection;

    public PhieuXuatBUS() {
        this.connection = DBConnection.getConnection();
        this.phieuXuatDAO = new PhieuXuatDAO(connection);
    }

    public int createPhieuXuat(PhieuXuatDTO phieuXuat, List<CTPhieuXuatDTO> chiTietList) {
        try {
            connection.setAutoCommit(false);
            
            if (phieuXuat.getPxId() <= 0) {
                throw new SQLException("ID must be a positive number");
            }
            
            if (phieuXuatDAO.isIdExists(phieuXuat.getPxId())) {
                throw new SQLException("ID already exists");
            }
            
            int pxId = phieuXuatDAO.createPhieuXuat(phieuXuat);
            
            for (CTPhieuXuatDTO chiTiet : chiTietList) {
                chiTiet.setPxId(pxId);
                if (!phieuXuatDAO.createChiTietPhieuXuat(chiTiet)) {
                    connection.rollback();
                    return -1;
                }
            }
            
            connection.commit();
            return pxId;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return -1;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<PhieuXuatDTO> getAllPhieuXuat() {
        try {
            List<PhieuXuatDTO> list = phieuXuatDAO.getAllPhieuXuat();
            for (PhieuXuatDTO px : list) {
                px.setChiTietPhieuXuat(phieuXuatDAO.getChiTietByPhieuXuat(px.getPxId()));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<CTPhieuXuatDTO> getChiTietByPhieuXuat(int pxId) {
        try {
            return phieuXuatDAO.getChiTietByPhieuXuat(pxId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isIdExists(int pxId) {
        try {
            return phieuXuatDAO.isIdExists(pxId);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<PhieuXuatDTO> advancedSearch(Map<String, String> filters) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT * FROM phieu_xuat WHERE 1=1");
        List<Object> params = new ArrayList<>();
        String logicOp = "AND";

        for (Map.Entry<String, String> entry : filters.entrySet()) {
            String field = entry.getKey();
            String condition = entry.getValue();

            if (field.equals("logic")) {
                logicOp = condition;
                continue;
            }

            if (condition == null || condition.trim().isEmpty()) {
                continue;
            }

            String[] parts = condition.split(" ", 2);
            if (parts.length != 2) continue;

            String operator = parts[0];
            String value = parts[1];

            sql.append(" ").append(logicOp).append(" ").append(field).append(" ").append(operator).append(" ?");
            params.add(parseValue(field, value));
        }

        sql.append(" ORDER BY ngay_xuat DESC");

        try (PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            List<PhieuXuatDTO> result = new ArrayList<>();
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    PhieuXuatDTO px = new PhieuXuatDTO(
                        rs.getInt("px_id"),
                        rs.getTimestamp("ngay_xuat"),
                        rs.getInt("nguoi_xuat_id")
                    );
                    result.add(px);
                }
            }
            return result;
        }
    }

    private Object parseValue(String field, String value) {
        try {
            if (field.equals("px_id") || field.equals("nguoi_xuat_id")) {
                return Integer.parseInt(value);
            }
        } catch (Exception e) {
            // Fall through to return string
        }
        return value;
    }
}