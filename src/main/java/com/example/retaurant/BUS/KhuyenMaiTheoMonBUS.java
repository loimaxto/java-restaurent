/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.retaurant.BUS;

import com.example.retaurant.DAO.KhuyenMaiTheoMonDAO;
import com.example.retaurant.DTO.KhuyenMaiTheoMonDTO;
import com.example.retaurant.DTO.MonAnDTO;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class KhuyenMaiTheoMonBUS {
    private KhuyenMaiTheoMonDAO khuyenMaiTheoMonDAO;

    public KhuyenMaiTheoMonBUS() {
        this.khuyenMaiTheoMonDAO = new KhuyenMaiTheoMonDAO();
    }
    
    public boolean ktraSoTien(KhuyenMaiTheoMonDTO khuyenMaiTheoMonDTO){
        MonAnBUS monAnBUS = new MonAnBUS();
        List<MonAnDTO> dsma = monAnBUS.getAllMonAn();
        for(MonAnDTO ma: dsma){
            if(khuyenMaiTheoMonDTO.getMaSp() == ma.getSpId())
            {
                if(khuyenMaiTheoMonDTO.getSoTien() > 0 && khuyenMaiTheoMonDTO.getSoTien() < ma.getGiaSp())
                {
                    return true;
                }
            }
        }
        return false;
    }
    
    public List<KhuyenMaiTheoMonDTO> getAllKhuyenMaiTheoMon(){
        try {
            return khuyenMaiTheoMonDAO.getAllKhuyenMaiTheoMon();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean addKhuyenmaiTheoMon(KhuyenMaiTheoMonDTO kmTheoMon) {
        try {
            return khuyenMaiTheoMonDAO.addKhuyenMaiTheoMon(kmTheoMon);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateKhuuenMaiTheoMon(KhuyenMaiTheoMonDTO kmTheoMon) { 
        try {
            return khuyenMaiTheoMonDAO.updateKhuyenMaiTheoMon(kmTheoMon);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteKhuyenMaiTheoMon(Integer kmId) {
        try {
            return khuyenMaiTheoMonDAO.deleteKhuyenMaiTheoMon(kmId);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
}
