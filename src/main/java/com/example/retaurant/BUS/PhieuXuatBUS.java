package com.example.retaurant.BUS;

import com.example.retaurant.DTO.CTPhieuXuatDTO;
import com.example.retaurant.DTO.PhieuXuatDTO;
import com.example.retaurant.DAO.PhieuXuatDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PhieuXuatBUS {
    private PhieuXuatDAO phieuXuatDAO;
    private Connection connection;

    public PhieuXuatBUS(Connection connection) {
        this.connection = connection;
        this.phieuXuatDAO = new PhieuXuatDAO(connection);
    }

    public int createPhieuXuat(PhieuXuatDTO phieuXuat, List<CTPhieuXuatDTO> chiTietList) {
        try {
            connection.setAutoCommit(false);
            
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
    public List<PhieuXuatDTO> searchPhieuXuat(String condition, String[] values) {
        try {
            String sql = "SELECT * FROM phieu_xuat WHERE " + condition;
            List<PhieuXuatDTO> results = new ArrayList<>();
            
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                for (int i = 0; i < values.length; i++) {
                    stmt.setString(i + 1, values[i]);
                }
                
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        PhieuXuatDTO px = new PhieuXuatDTO(
                            rs.getInt("px_id"),
                            rs.getTimestamp("ngay_xuat"),
                            rs.getInt("nguoi_xuat_id")
                        );
                        results.add(px);
                    }
                }
            }
            return results;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<PhieuXuatDTO> advancedSearch(Map<String, String> filters) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT * FROM phieu_xuat WHERE 1=1");
        List<Object> params = new ArrayList<>();

        for (Map.Entry<String, String> entry : filters.entrySet()) {
            String field = entry.getKey();
            String condition = entry.getValue();

            if (condition == null || condition.trim().isEmpty()) {
                continue;
            }

            String[] parts = condition.split(" ", 2);
            if (parts.length != 2) continue;

            String operator = parts[0];
            String value = parts[1];

            switch (operator.toUpperCase()) {
                case "AND":
                    sql.append(" AND ");
                    break;
                case "OR":
                    sql.append(" OR ");
                    break;
                case "NOT":
                    sql.append(" NOT ");
                    break;
                default:
                    sql.append(" AND ").append(field).append(" ");
                    switch (operator) {
                        case ">":
                        case ">=":
                        case "<":
                        case "<=":
                        case "<>":
                        case "=":
                            sql.append(operator).append(" ?");
                            break;
                        default:
                            sql.append("= ?");
                            operator = "=";
                    }
                    params.add(parseValue(field, value));
            }
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
            if (field.equals("ngay_xuat")) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                return new java.sql.Date(sdf.parse(value).getTime());
            } else if (field.equals("px_id") || field.equals("nguoi_xuat_id")) {
                return Integer.parseInt(value);
            }
        } catch (Exception e) {
            // Fall through to return string
        }
        return value;
    }
}