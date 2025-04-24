package com.example.retaurant.BUS;

import com.example.retaurant.DTO.PhieuNhapDTO;
import com.example.retaurant.DTO.ChiTietPhieuNhapDTO;
import com.example.retaurant.DTO.NhaCungCapDTO; // Ensure this import is correct and the class exists
import com.example.retaurant.DAO.PhieuNhapDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PhieuNhapBUS {
    private PhieuNhapDAO phieuNhapDAO;
    private Connection connection;

    public PhieuNhapBUS(Connection connection) {
        this.connection = connection;
        this.phieuNhapDAO = new PhieuNhapDAO(connection);
    }

    public int createPhieuNhap(PhieuNhapDTO phieuNhap, List<ChiTietPhieuNhapDTO> chiTietList) {
        try {
            connection.setAutoCommit(false);
            
            // Calculate total
            long tongTien = chiTietList.stream().mapToLong(ChiTietPhieuNhapDTO::getTongTien).sum();
            phieuNhap.setTongTien(tongTien);
            
            int pnId = phieuNhapDAO.createPhieuNhap(phieuNhap);
            
            for (ChiTietPhieuNhapDTO chiTiet : chiTietList) {
                chiTiet.setPnId(pnId);
                if (!phieuNhapDAO.createChiTietPhieuNhap(chiTiet)) {
                    connection.rollback();
                    return -1;
                }
            }
            
            connection.commit();
            return pnId;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return -1;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<PhieuNhapDTO> getAllPhieuNhap() {
        try {
            List<PhieuNhapDTO> list = phieuNhapDAO.getAllPhieuNhap();
            for (PhieuNhapDTO pn : list) {
                pn.setChiTietPhieuNhap(phieuNhapDAO.getChiTietByPhieuNhap(pn.getPnId()));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<ChiTietPhieuNhapDTO> getChiTietByPhieuNhap(int pnId) {
        try {
            return phieuNhapDAO.getChiTietByPhieuNhap(pnId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<NhaCungCapDTO> getAllNhaCungCap() {
        try {
            return phieuNhapDAO.getAllNhaCungCap();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
