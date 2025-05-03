package com.example.retaurant.BUS;

import com.example.retaurant.DAO.KhuyenMaiDAO;
import com.example.retaurant.DTO.KhuyenMaiDTO;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KhuyenMaiBUS {

    private KhuyenMaiDAO khuyenMaiDAO;

    public KhuyenMaiBUS() {
        this.khuyenMaiDAO = new KhuyenMaiDAO();
    }
    public boolean checkKhuyenMaiHienTai() {
        Date currentDay = new Date();
        
        return false;
    }
    public KhuyenMaiDTO getKhuyenMaiById(Integer kmId) throws SQLException {
        return khuyenMaiDAO.getKhuyenMaiById(kmId);
    }

    public List<KhuyenMaiDTO> getAllKhuyenMais(){
        try {
            return khuyenMaiDAO.getAllKhuyenMais();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(MonAnBUS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean addKhuyenMai(KhuyenMaiDTO khuyenMai){
        try {
            return khuyenMaiDAO.addKhuyenMai(khuyenMai);
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(MonAnBUS.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean updateKhuyenMai(KhuyenMaiDTO khuyenMai){     
        try {
            return khuyenMaiDAO.updateKhuyenMai(khuyenMai);
        } catch (SQLException e) {
            System.out.println("Lỗi khi cập nhật khuyến mãi: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteKhuyenMai(Integer kmId) {
        try {
            return khuyenMaiDAO.deleteKhuyenMai(kmId);
        } catch (SQLException e) {
            System.out.println("Lỗi khi cập nhật khuyến mãi: " + e.getMessage());
            return false;
        }
    }
}