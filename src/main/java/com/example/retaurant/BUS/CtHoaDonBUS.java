package com.example.retaurant.BUS;

import com.example.retaurant.DAO.CtHoaDonDAO;
import com.example.retaurant.DTO.CtHoaDonDTO;
import com.example.retaurant.DTO.CtSanPhamThanhToanDTO;
import java.sql.SQLException;
import java.util.List;

public class CtHoaDonBUS {

    private CtHoaDonDAO ctHoaDonDAO;

    public CtHoaDonBUS() {
        this.ctHoaDonDAO = new CtHoaDonDAO();
    }


    public List<CtHoaDonDTO> getAllCtHoaDonsByHoaDonId(int hoadonId) {
        
        try {
            return ctHoaDonDAO.getAllCtHoaDonByHoaDonId(hoadonId);
        } catch (SQLException e) {
            System.err.println("Error getting all CtHoaDons: " + e.getMessage());
            e.printStackTrace();
            return null; // Or throw, log, etc.
        }
    }
    public List<CtSanPhamThanhToanDTO> getCtSanPhanThanhToanByHdId(int hoadonId) {
        
        try {
            return ctHoaDonDAO.getAllCtHoaDonByHoaDonIdCoTenSp(hoadonId);
        } catch (SQLException e) {
            System.err.println("Error getting all CtHoaDons: " + e.getMessage());
            e.printStackTrace();
            return null; // Or throw, log, etc.
        }
    }
    public boolean addCtHoaDon(CtHoaDonDTO ctHoaDon) {
        try {
            ctHoaDon.setTongTienCt(ctHoaDon.getSoLuong()*ctHoaDon.getGiaTaiLucDat());
            return ctHoaDonDAO.addCtHoaDon(ctHoaDon);
        } catch (SQLException e) {
            System.err.println("Error adding CtHoaDon: " + e.getMessage());
            return false; // Or throw, log, etc.
        }
    }

    public boolean updateCtHoaDon(CtHoaDonDTO ctHoaDon) {
        try {
            ctHoaDon.setTongTienCt(ctHoaDon.getSoLuong()*ctHoaDon.getGiaTaiLucDat());
            return ctHoaDonDAO.updateCtHoaDonThemMonVaoHoaDon(ctHoaDon);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error updating CtHoaDon: " + e.getMessage());
            return false; // Or throw, log, etc.
        }
    }

    public boolean deleteCtHoaDon(Integer hdId, Integer spdId) {
        if (hdId == null || spdId == null) {
            throw new IllegalArgumentException("hdId and spdId cannot be null.");
        }
        try {
            return ctHoaDonDAO.deleteCtHoaDon(hdId, spdId);
        } catch (SQLException e) {
            System.err.println("Error deleting CtHoaDon: " + e.getMessage());
            return false; // Or throw, log, etc.
        }
    }
}