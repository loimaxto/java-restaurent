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

    // Search by all fields (simple search)
    public List<NhaCungCapDTO> searchByAll(String keyword) throws SQLException {
        List<NhaCungCapDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM nha_cung_cap WHERE " +
                     "ncc_id LIKE ? OR " +
                     "ten_ncc LIKE ? OR " +
                     "sdt LIKE ? OR " +
                     "dchi LIKE ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            String searchPattern = "%" + keyword + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            stmt.setString(3, searchPattern);
            stmt.setString(4, searchPattern);
            
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

    // Advanced search with multiple criteria
    public List<NhaCungCapDTO> advancedSearch(
            Integer id, String idOperator,
            String tenNCC, String tenNCCOperator,
            String sdt, String sdtOperator,
            String diaChi, String diaChiOperator) throws SQLException {
        
        List<NhaCungCapDTO> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM nha_cung_cap WHERE 1=1");
        List<Object> params = new ArrayList<>();

        // ID condition
        if (id != null && idOperator != null) {
            sql.append(" AND ncc_id ").append(convertOperator(idOperator)).append(" ?");
            params.add(id);
        }

        // Name condition
        if (tenNCC != null && !tenNCC.isEmpty() && tenNCCOperator != null) {
            sql.append(" AND ten_ncc ").append(convertOperator(tenNCCOperator));
            if (tenNCCOperator.equals("LIKE")) {
                sql.append(" ?");
                params.add("%" + tenNCC + "%");
            } else {
                sql.append(" ?");
                params.add(tenNCC);
            }
        }

        // Phone condition
        if (sdt != null && !sdt.isEmpty() && sdtOperator != null) {
            sql.append(" AND sdt ").append(convertOperator(sdtOperator));
            if (sdtOperator.equals("LIKE")) {
                sql.append(" ?");
                params.add("%" + sdt + "%");
            } else {
                sql.append(" ?");
                params.add(sdt);
            }
        }

        // Address condition
        if (diaChi != null && !diaChi.isEmpty() && diaChiOperator != null) {
            sql.append(" AND dchi ").append(convertOperator(diaChiOperator));
            if (diaChiOperator.equals("LIKE")) {
                sql.append(" ?");
                params.add("%" + diaChi + "%");
            } else {
                sql.append(" ?");
                params.add(diaChi);
            }
        }

        try (PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            
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

    // Helper method to convert operator symbols to SQL operators
    private String convertOperator(String operator) {
        switch (operator) {
            case "=": return "=";
            case "<>": return "<>";
            case ">": return ">";
            case ">=": return ">=";
            case "<": return "<";
            case "<=": return "<=";
            case "LIKE": return "LIKE";
            case "NOT LIKE": return "NOT LIKE";
            default: return "=";
        }
    }

    // Combined search with AND/OR logic
    public List<NhaCungCapDTO> combinedSearch(List<SearchCriteria> criteriaList, String logicOperator) throws SQLException {
        List<NhaCungCapDTO> list = new ArrayList<>();
        if (criteriaList == null || criteriaList.isEmpty()) {
            return getAllNhaCungCap();
        }

        StringBuilder sql = new StringBuilder("SELECT * FROM nha_cung_cap WHERE ");
        List<Object> params = new ArrayList<>();

        for (int i = 0; i < criteriaList.size(); i++) {
            SearchCriteria criteria = criteriaList.get(i);
            if (i > 0) {
                sql.append(" ").append(logicOperator).append(" ");
            }
            
            sql.append(criteria.getFieldName())
               .append(" ")
               .append(convertOperator(criteria.getOperator()));
            
            if (criteria.getOperator().equals("LIKE") || criteria.getOperator().equals("NOT LIKE")) {
                sql.append(" ?");
                params.add("%" + criteria.getValue() + "%");
            } else {
                sql.append(" ?");
                params.add(criteria.getValue());
            }
        }

        try (PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            
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

    // Inner class for search criteria
    public static class SearchCriteria {
        private String fieldName;
        private String operator;
        private Object value;

        public SearchCriteria(String fieldName, String operator, Object value) {
            this.fieldName = fieldName;
            this.operator = operator;
            this.value = value;
        }

        // Getters and setters
        public String getFieldName() { return fieldName; }
        public String getOperator() { return operator; }
        public Object getValue() { return value; }
        public void setFieldName(String fieldName) { this.fieldName = fieldName; }
        public void setOperator(String operator) { this.operator = operator; }
        public void setValue(Object value) { this.value = value; }
    }
}