package com.example.retaurant.BUS;

import com.example.retaurant.DAO.NhaCungCapDAO;
import com.example.retaurant.DTO.NhaCungCapDTO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class NhaCungCapBUS {
    private NhaCungCapDAO nhaCungCapDAO;

    public NhaCungCapBUS(Connection connection) {
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
        return nhaCungCapDAO.addNhaCungCap(ncc);
    }

    // Update supplier
    public boolean updateNhaCungCap(NhaCungCapDTO ncc) throws SQLException {
        return nhaCungCapDAO.updateNhaCungCap(ncc);
    }

    // Delete supplier
    public boolean deleteNhaCungCap(int id) throws SQLException {
        return nhaCungCapDAO.deleteNhaCungCap(id);
    }

    // Search suppliers by name
    public List<NhaCungCapDTO> searchNhaCungCapByName(String name) throws SQLException {
        return nhaCungCapDAO.searchNhaCungCapByName(name);
    }

    // Validate supplier data before adding/updating
    public String validateNhaCungCap(NhaCungCapDTO ncc) {
        if (ncc.getTen_ncc() == null || ncc.getTen_ncc().trim().isEmpty()) {
            return "Tên nhà cung cấp không được để trống";
        }
        
        if (ncc.getSdt() == null || ncc.getSdt().trim().isEmpty()) {
            return "Số điện thoại không được để trống";
        }
        
        if (!ncc.getSdt().matches("\\d{10,11}")) {
            return "Số điện thoại không hợp lệ";
        }
        
        return null; // No errors
    }
}
