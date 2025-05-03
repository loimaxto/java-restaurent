/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.retaurant.BUS;

import com.example.retaurant.DAO.BanDAO;
import com.example.retaurant.DTO.BanDTO;
import com.example.retaurant.MyCustom.MyDialog;
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
            if ( banDAO.isUniqueId(ban.getBanId())) {
                return banDAO.addBan(ban) > 0;
            } else {
                new MyDialog("Mã bàn bị trùng", 0);
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
    public boolean updateBanDangDuocDat(BanDTO ban, Integer hoaDonId) {
        if (ban.getTinhTrangSuDung() == 0) { // chua co ai dat ban
           
            ban.setTinhTrangSuDung(1);
            ban.setIdHoaDonHienTai(hoaDonId);
        } else {
            //huy ban or thanh toan xong
            ban.setTinhTrangSuDung(0); 
            ban.setIdHoaDonHienTai(null);
        }
        
        try {
            return banDAO.updateBan(ban);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error updating ban: " + e.getMessage());
            return false;
        }
    }
    public boolean isUniqueTableId(int tableId ) {
        return banDAO.isUniqueId(tableId);
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
