package com.example.retaurant.BUS;

import com.example.retaurant.DAO.CtHoaDonDAO;
import com.example.retaurant.DTO.CtHoaDonDTO;
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

    public boolean addCtHoaDon(CtHoaDonDTO ctHoaDon) {
        if (ctHoaDon == null) {
            throw new IllegalArgumentException("CtHoaDonDTO cannot be null.");
        }
        if (ctHoaDon.getHdId() == null || ctHoaDon.getSpdId() == null ) {
            throw new IllegalArgumentException("All fields in CtHoaDonDTO must be set.");
        }
        try {
            return ctHoaDonDAO.addCtHoaDon(ctHoaDon);
        } catch (SQLException e) {
            System.err.println("Error adding CtHoaDon: " + e.getMessage());
            return false; // Or throw, log, etc.
        }
    }

    public boolean updateCtHoaDon(CtHoaDonDTO ctHoaDon) {
        if (ctHoaDon == null) {
            throw new IllegalArgumentException("CtHoaDonDTO cannot be null.");
        }
        try {
            return ctHoaDonDAO.updateCtHoaDon(ctHoaDon);
        } catch (SQLException e) {
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