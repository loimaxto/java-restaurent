package com.example.retaurant.BUS;

import com.example.retaurant.DAO.KhuyenMaiDAO;
import com.example.retaurant.DTO.KhuyenMaiDTO;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

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

    public List<KhuyenMaiDTO> getAllKhuyenMais() throws SQLException {
        return khuyenMaiDAO.getAllKhuyenMais();
    }

    public boolean addKhuyenMai(KhuyenMaiDTO khuyenMai) throws SQLException {
        return khuyenMaiDAO.addKhuyenMai(khuyenMai);
    }

    public boolean updateKhuyenMai(KhuyenMaiDTO khuyenMai) throws SQLException {
        return khuyenMaiDAO.updateKhuyenMai(khuyenMai);
    }

    public boolean deleteKhuyenMai(Integer kmId) throws SQLException {
        return khuyenMaiDAO.deleteKhuyenMai(kmId);
    }
}