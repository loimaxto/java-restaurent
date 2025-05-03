package com.example.retaurant.BUS;

import com.example.retaurant.DAO.KhuyenMaiDAO;
import com.example.retaurant.DTO.KhuyenMaiDTO;

import java.sql.SQLException;
import java.util.ArrayList;
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
    public boolean checkMaKhuyenMai(Integer id_km){
        List<KhuyenMaiDTO> dskm = getAllKhuyenMais();
        
        for(KhuyenMaiDTO km: dskm){
            if(id_km == km.getKmId())
            {
                return true;
            }
        }
        return false;
    }
    
    public List<KhuyenMaiDTO> searchKhuyenMaiTheoDon(String keyword) {
        List<KhuyenMaiDTO> allList = getAllKhuyenMais();
        List<KhuyenMaiDTO> result = new ArrayList<>();

        if (allList != null) {
            for (KhuyenMaiDTO km : allList) {
                if (km.getTenKm().toLowerCase().contains(keyword.toLowerCase())) {
                    result.add(km);
                }
            }
        }

        return result;
    }
    
    public boolean checktimeKhuyenMaiDTO(KhuyenMaiDTO km){
        if(km.getNgayKt().after(km.getNgayBd()))
        {
            return true;
        }
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