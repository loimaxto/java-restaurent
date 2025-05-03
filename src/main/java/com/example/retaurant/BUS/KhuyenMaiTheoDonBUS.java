 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.retaurant.BUS;

import com.example.retaurant.DAO.KhuyenMaiTheoDonDAO;
import com.example.retaurant.DTO.KhuyenMaiTheoDonDTO;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class KhuyenMaiTheoDonBUS {
    private KhuyenMaiTheoDonDAO khuyenMaiTheoDonDAO;

    public KhuyenMaiTheoDonBUS() {
        this.khuyenMaiTheoDonDAO = new KhuyenMaiTheoDonDAO();
    }
    
    public List<KhuyenMaiTheoDonDTO> getAllKhuyenMaiTheoDon() {
        try {
            return khuyenMaiTheoDonDAO.getAllKhuyenMaiTheoDon();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean addKhuyenMaiTheoDon(KhuyenMaiTheoDonDTO kmTheoDon) {
        try {
            return khuyenMaiTheoDonDAO.addKhuyenMaiTheoDon(kmTheoDon);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateKhuyenMaiTheoDon(KhuyenMaiTheoDonDTO kmTheoDon) {
        try {
            return khuyenMaiTheoDonDAO.updateKhuyenMaiTheoDon(kmTheoDon);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteKhuyenMaiTheoDon(Integer kmId) {
        try {
            return khuyenMaiTheoDonDAO.deleteKhuyenMaiTheoMon(kmId);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
}
