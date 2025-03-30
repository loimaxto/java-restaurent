package com.example.retaurant.BUS;

import com.example.retaurant.DAO.CongThucDAO;
import com.example.retaurant.DTO.CongThucDTO;
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
public class CongThucBUS {
    
    private Connection connection = DBConnection.getConnection();
    private CongThucDAO congThucDAO;
    private MonAnBUS monAnBUS;
    private NguyenLieuBUS nguyenLieuBUS;
    
    public CongThucBUS() {
        this.congThucDAO = new CongThucDAO(connection);
        this.monAnBUS = new MonAnBUS();
        this.nguyenLieuBUS = new NguyenLieuBUS();
    }
    
    public boolean addCongThuc(CongThucDTO congThuc) {
        try {
            // Validate dish exists
            if (monAnBUS.getMonAnById(congThuc.getSpId()) == null) {
                System.out.println("Món ăn không tồn tại!");
                return false;
            }
            
            // Validate ingredient exists
            if (nguyenLieuBUS.getNguyenLieuById(congThuc.getNlId()) == null) {
                System.out.println("Nguyên liệu không tồn tại!");
                return false;
            }
            
            // Validate quantity
            if (congThuc.getSoLuong() <= 0) {
                System.out.println("Số lượng phải lớn hơn 0!");
                return false;
            }
            
            return congThucDAO.addCongThuc(congThuc);
        } catch (SQLException ex) {
            Logger.getLogger(CongThucBUS.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean updateCongThuc(CongThucDTO congThuc) {
        try {
            // Validate existence
            if (!congThucDAO.exists(congThuc.getSpId(), congThuc.getNlId())) {
                System.out.println("Công thức không tồn tại!");
                return false;
            }
            
            // Validate quantity
            if (congThuc.getSoLuong() <= 0) {
                System.out.println("Số lượng phải lớn hơn 0!");
                return false;
            }
            
            return congThucDAO.updateCongThuc(congThuc);
        } catch (SQLException ex) {
            Logger.getLogger(CongThucBUS.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean deleteCongThuc(Integer spId, Integer nlId) {
        try {
            if (!congThucDAO.exists(spId, nlId)) {
                System.out.println("Công thức không tồn tại!");
                return false;
            }
            return congThucDAO.deleteCongThuc(spId, nlId);
        } catch (SQLException ex) {
            Logger.getLogger(CongThucBUS.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public List<CongThucDTO> getCongThucByMonAn(Integer spId) {
        try {
            return congThucDAO.getCongThucByMonAn(spId);
        } catch (SQLException ex) {
            Logger.getLogger(CongThucBUS.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public List<CongThucDTO> getAllCongThuc() {
        try {
            return congThucDAO.getAllCongThuc();
        } catch (SQLException ex) {
            Logger.getLogger(CongThucBUS.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public boolean exists(Integer spId, Integer nlId) {
        try {
            return congThucDAO.exists(spId, nlId);
        } catch (SQLException ex) {
            Logger.getLogger(CongThucBUS.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    // Helper methods for GUI
    public String getTenMonAn(Integer spId) {
        return monAnBUS.getMonAnById(spId) != null ? 
               monAnBUS.getMonAnById(spId).getTenSp() : "Unknown";
    }
    
    public String getTenNguyenLieu(Integer nlId) {
        return nguyenLieuBUS.getNguyenLieuById(nlId) != null ? 
               nguyenLieuBUS.getNguyenLieuById(nlId).getTenNl() : "Unknown";
    }
    
    public String getDonViNguyenLieu(Integer nlId) {
        return nguyenLieuBUS.getNguyenLieuById(nlId) != null ? 
               nguyenLieuBUS.getNguyenLieuById(nlId).getDonVi() : "";
    }
    
    public static void main(String[] args) {
        CongThucBUS bus = new CongThucBUS();
        
        // Test getAll
        List<CongThucDTO> dsCongThuc = bus.getAllCongThuc();
        if (dsCongThuc != null) {
            for (CongThucDTO ct : dsCongThuc) {
                System.out.println(ct.toString());
            }
        }
        
        // Test add
        CongThucDTO newCT = new CongThucDTO(1, 1, 2);
        if (bus.addCongThuc(newCT)) {
            System.out.println("Thêm công thức thành công!");
        }
    }
}