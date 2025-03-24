/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.retaurant.BUS;

import com.example.retaurant.DAO.BanDAO;
import com.example.retaurant.DTO.BanDTO;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author light
 */
public class BanBUS {
    static private BanDAO banDAO;

    public BanBUS() {
        banDAO = new BanDAO();
    }

    public List<BanDTO> getAllBans() {
        try {
            return banDAO.getAllBans();
        } catch (SQLException ex) {
            Logger.getLogger(BanBUS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public BanDTO getBanById(int banId) {
        try {
            return banDAO.getBanById(banId);
        } catch (SQLException e) {
            System.out.println("Error getting ban by ID: " + e.getMessage());
            return null;
        }
    }

    public boolean addBan(BanDTO ban) {
        try {
            return banDAO.addBan(ban) > 0;
        } catch (SQLException e) {
            System.out.println("Error adding ban: " + e.getMessage());
            return false;
        }
    }

    public boolean updateBan(BanDTO ban) {
        try {
            return banDAO.updateBan(ban);
        } catch (SQLException e) {
            System.out.println("Error updating ban: " + e.getMessage());
            return false;
        }
    }
    public boolean updateBanDangDuocDat(BanDTO ban, int hoaDonId) {
        if (ban.getTinhTrangSuDung() == 0) { // chua co ai dat ban
            ban.setTinhTrangSuDung(1-ban.getTinhTrangSuDung());
            ban.setIdHoaDonHienTai(hoaDonId);
        } else {
            //huy ban or thanh toan xong
            ban.setTinhTrangSuDung(1-ban.getTinhTrangSuDung()); 
            ban.setIdHoaDonHienTai(null);
        }
        
        try {
            return banDAO.updateBan(ban);
        } catch (SQLException e) {
            System.out.println("Error updating ban: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteBan(int banId) {
        try {
            return banDAO.deleteBan(banId);
        } catch (SQLException e) {
            System.out.println("Error deleting ban: " + e.getMessage());
            return false;
        }
    }
}
