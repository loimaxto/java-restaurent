package com.example.retaurant.BUS;

import com.example.retaurant.DAO.NhaCungCapDAO;
import com.example.retaurant.DTO.NhaCungCapDTO;
import com.example.retaurant.utils.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class NhaCungCapBUS {
    private NhaCungCapDAO nhaCungCapDAO;
    private Connection connection;

    public NhaCungCapBUS() {
        this.connection = DBConnection.getConnection();
        this.nhaCungCapDAO = new NhaCungCapDAO(connection);
    }

    // Get all suppliers
    public List<NhaCungCapDTO> getAllNhaCungCap() throws SQLException {
        return nhaCungCapDAO.getAllNhaCungCap();
    }

    // Get supplier by ID
    public NhaCungCapDTO getNhaCungCapById(int id) throws SQLException {
        return nhaCungCapDAO.getNhaCungCapById(id);
    }

    // Add new supplier
    public boolean addNhaCungCap(NhaCungCapDTO ncc) throws SQLException {
    // Check if ID already exists
    if (ncc.getNcc_id() <= 0) {
        throw new IllegalArgumentException("ID phải là số nguyên dương");
    }
    
    if (nhaCungCapDAO.getNhaCungCapById(ncc.getNcc_id()) != null) {
        throw new IllegalArgumentException("ID đã tồn tại");
    }
    
    String validationError = validateNhaCungCap(ncc);
    if (validationError != null) {
        throw new IllegalArgumentException(validationError);
    }
    
    return nhaCungCapDAO.addNhaCungCap(ncc);
}

    // Update supplier
    public boolean updateNhaCungCap(NhaCungCapDTO ncc) throws SQLException {
        String validationError = validateNhaCungCap(ncc);
        if (validationError != null) {
            throw new IllegalArgumentException(validationError);
        }
        return nhaCungCapDAO.updateNhaCungCap(ncc);
    }

    // Delete supplier
    public boolean deleteNhaCungCap(int id) throws SQLException {
        return nhaCungCapDAO.deleteNhaCungCap(id);
    }

    // Search by all fields (simple search)
    public List<NhaCungCapDTO> searchByAll(String keyword) throws SQLException {
        return nhaCungCapDAO.searchByAll(keyword);
    }

    // Advanced search with multiple criteria
    public List<NhaCungCapDTO> advancedSearch(
            Integer id, String idOperator,
            String tenNCC, String tenNCCOperator,
            String sdt, String sdtOperator,
            String diaChi, String diaChiOperator) throws SQLException {
        
        return nhaCungCapDAO.advancedSearch(
            id, idOperator,
            tenNCC, tenNCCOperator,
            sdt, sdtOperator,
            diaChi, diaChiOperator
        );
    }

    // Combined search with AND/OR logic
    public List<NhaCungCapDTO> combinedSearch(
            List<NhaCungCapDAO.SearchCriteria> criteriaList, 
            String logicOperator) throws SQLException {
        return nhaCungCapDAO.combinedSearch(criteriaList, logicOperator);
    }

    // Validate supplier data before adding/updating
    public String validateNhaCungCap(NhaCungCapDTO ncc) {
        if (ncc.getTen_ncc() == null || ncc.getTen_ncc().trim().isEmpty()) {
            return "Tên nhà cung cấp không được để trống";
        }
        
        if (ncc.getTen_ncc().length() > 100) {
            return "Tên nhà cung cấp không được vượt quá 100 ký tự";
        }
        
        if (ncc.getSdt() == null || ncc.getSdt().trim().isEmpty()) {
            return "Số điện thoại không được để trống";
        }
        
        if (!ncc.getSdt().matches("\\d{10,11}")) {
            return "Số điện thoại phải có 10-11 chữ số";
        }
        
        if (ncc.getDchi() == null || ncc.getDchi().trim().isEmpty()) {
            return "Địa chỉ không được để trống";
        }
        
        if (ncc.getDchi().length() > 200) {
            return "Địa chỉ không được vượt quá 200 ký tự";
        }
        
        return null; // No errors
    }

    // Create search criteria object (helper method)
    public NhaCungCapDAO.SearchCriteria createSearchCriteria(
            String fieldName, String operator, Object value) {
        return new NhaCungCapDAO.SearchCriteria(fieldName, operator, value);
    }
}