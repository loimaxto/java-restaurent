package com.example.retaurant.BUS;

import com.example.retaurant.DTO.PhieuNhapDTO;
import com.example.retaurant.DTO.ChiTietPhieuNhapDTO;
import com.example.retaurant.DTO.NhaCungCapDTO;
import com.example.retaurant.DAO.PhieuNhapDAO;
import com.example.retaurant.utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PhieuNhapBUS {
    private PhieuNhapDAO phieuNhapDAO;
    private Connection connection;

    public PhieuNhapBUS() {
        this.connection = DBConnection.getConnection();
        this.phieuNhapDAO = new PhieuNhapDAO(connection);
    }

    public int createPhieuNhap(PhieuNhapDTO phieuNhap, List<ChiTietPhieuNhapDTO> chiTietList) {
        try {
            connection.setAutoCommit(false);
            
            // Calculate total
            long tongTien = chiTietList.stream().mapToLong(ChiTietPhieuNhapDTO::getTongTien).sum();
            phieuNhap.setTongTien(tongTien);
            
            // Validate ID is provided
            if (phieuNhap.getPnId() <= 0) {
                throw new SQLException("ID must be a positive number");
            }
            
            int pnId = phieuNhapDAO.createPhieuNhap(phieuNhap);
            
            for (ChiTietPhieuNhapDTO chiTiet : chiTietList) {
                chiTiet.setPnId(pnId);
                if (!phieuNhapDAO.createChiTietPhieuNhap(chiTiet)) {
                    connection.rollback();
                    return -1;
                }
            }
            
            connection.commit();
            return pnId;
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

    public List<PhieuNhapDTO> getAllPhieuNhap() {
        try {
            List<PhieuNhapDTO> list = phieuNhapDAO.getAllPhieuNhap();
            for (PhieuNhapDTO pn : list) {
                pn.setChiTietPhieuNhap(phieuNhapDAO.getChiTietByPhieuNhap(pn.getPnId()));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<ChiTietPhieuNhapDTO> getChiTietByPhieuNhap(int pnId) {
        try {
            return phieuNhapDAO.getChiTietByPhieuNhap(pnId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<NhaCungCapDTO> getAllNhaCungCap() {
        try {
            return phieuNhapDAO.getAllNhaCungCap();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isIdExists(int pnId) {
        try {
            return phieuNhapDAO.isIdExists(pnId);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    

    public List<PhieuNhapDTO> advancedSearch(Map<String, String> filters) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT * FROM phieu_nhap WHERE 1=1");
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

        sql.append(" ORDER BY ngay_nhap DESC");

        try (PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            List<PhieuNhapDTO> result = new ArrayList<>();
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    PhieuNhapDTO pn = new PhieuNhapDTO(
                        rs.getInt("pn_id"),
                        rs.getTimestamp("ngay_nhap"),
                        rs.getInt("ncc_id"),
                        rs.getInt("nguoi_nhap_id"),
                        rs.getLong("tong_tien")
                    );
                    result.add(pn);
                }
            }
            return result;
        }
    }
       public List<PhieuNhapDTO> searchPhieuNhap(String condition, String[] values) {
    List<PhieuNhapDTO> results = new ArrayList<>();
    StringBuilder sql = new StringBuilder("SELECT * FROM phieu_nhap WHERE ");

    sql.append(condition); // condition example: "ngay_nhap BETWEEN ? AND ?" or "tong_tien > ? AND tong_tien < ?"

    try (PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
        for (int i = 0; i < values.length; i++) {
            stmt.setString(i + 1, values[i]);
        }

        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                PhieuNhapDTO pn = new PhieuNhapDTO(
                    rs.getInt("pn_id"),
                    rs.getTimestamp("ngay_nhap"),
                    rs.getInt("ncc_id"),
                    rs.getInt("nguoi_nhap_id"),
                    rs.getLong("tong_tien")
                );
                results.add(pn);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return results;
}



    private Object parseValue(String field, String value) {
        try {
            if (field.equals("ngay_nhap")) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                return new java.sql.Date(sdf.parse(value).getTime());
            } else if (field.equals("tong_tien") || field.equals("ncc_id") || field.equals("nguoi_nhap_id")) {
                return Long.parseLong(value);
            }
        } catch (Exception e) {
            // Fall through to return string
        }
        return value;
    }
}