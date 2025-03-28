package com.example.retaurant.BUS;

import com.example.retaurant.DAO.NguyenLieuDAO;
import com.example.retaurant.DTO.NguyenLieuDTO;
import com.example.retaurant.utils.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class NguyenLieuBUS {
    
    private Connection connection = DBConnection.getConnection();
    private NguyenLieuDAO nguyenLieuDAO;
    
    public NguyenLieuBUS() {
        nguyenLieuDAO = new NguyenLieuDAO(connection);
    }
    
    public List<NguyenLieuDTO> getAllNguyenLieu() {
        try {
            return nguyenLieuDAO.getAllNguyenLieu();
        } catch (SQLException ex) {
            Logger.getLogger(NguyenLieuBUS.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public NguyenLieuDTO getNguyenLieuById(int nlId) {
        try {
            return nguyenLieuDAO.getNguyenLieuById(nlId);
        } catch (SQLException e) {
            System.out.println("Lỗi lấy nguyên liệu theo ID: " + e.getMessage());
            return null;
        }
    }
    
    public boolean addNguyenLieu(NguyenLieuDTO nguyenLieu) {
        try {
            return nguyenLieuDAO.addNguyenLieu(nguyenLieu) > 0;
        } catch (SQLException e) {
            System.out.println("Lỗi khi thêm nguyên liệu: " + e.getMessage());
            return false;
        }
    }
    
    public boolean updateNguyenLieu(NguyenLieuDTO nguyenLieu) {
        try {
            return nguyenLieuDAO.updateNguyenLieu(nguyenLieu);
        } catch (SQLException e) {
            System.out.println("Lỗi khi cập nhật nguyên liệu: " + e.getMessage());
            return false;
        }
    }
    
    public boolean deleteNguyenLieu(int nlId) {
        try {
            return nguyenLieuDAO.deleteNguyenLieu(nlId);
        } catch (SQLException e) {
            System.out.println("Lỗi khi xóa nguyên liệu: " + e.getMessage());
            return false;
        }
    }
    
    public List<NguyenLieuDTO> searchNguyenLieuByName(String name) {
        try {
            return nguyenLieuDAO.searchNguyenLieuByName(name);
        } catch (SQLException e) {
            System.out.println("Lỗi khi tìm kiếm nguyên liệu: " + e.getMessage());
            return null;
        }
    }
    
    public static void main(String[] args) {
        NguyenLieuBUS nguyenLieuBUS = new NguyenLieuBUS();
        List<NguyenLieuDTO> dsNguyenLieu = nguyenLieuBUS.getAllNguyenLieu();
        
        if (dsNguyenLieu != null) {
            for (NguyenLieuDTO nl : dsNguyenLieu) {
                System.out.println("ID: " + nl.getNlId() + 
                                 ", Tên nguyên liệu: " + nl.getTenNl() + 
                                 ", Đơn vị: " + nl.getDonVi() + 
                                 ", Số lượng: " + nl.getSoLuong());
            }
        } else {
            System.out.println("Không thể lấy danh sách nguyên liệu");
        }
    }
}