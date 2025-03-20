/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.retaurant.BUS;

import com.example.retaurant.DAO.MonAnDAO;
import com.example.retaurant.DTO.MonAnDTO;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class MonAnBUS {
    private MonAnDAO monAnDAO;

    public MonAnBUS(MonAnDAO monAnDAO) {
        this.monAnDAO = monAnDAO;
    }

   public List<MonAnDTO> getAllMonAn() {
        try {
            return monAnDAO.getAllDsMonAn();
        } catch (SQLException ex) {
            Logger.getLogger(MonAnBUS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public MonAnDTO getMonAnById(int spId) {
        try {
            return monAnDAO.getMonAnById(spId);
        } catch (SQLException e) {
            System.out.println("Lỗi lấy món ăn theo ID: " + e.getMessage());
            return null;
        }
    }
    
    
    public boolean addMonAn(MonAnDTO monAn) {
        try {
            return monAnDAO.addMonAn(monAn) > 0;
        } catch (SQLException e) {
            System.out.println("Lỗi khi thêm món ăn: " + e.getMessage());
            return false;
        }
    }
    
    public boolean updateMonAn(MonAnDTO monAn) {
        try {
            return monAnDAO.updateMonAn(monAn);
        } catch (SQLException e) {
            System.out.println("Lỗi khi cập nhật món ăn: " + e.getMessage());
            return false;
        }
    }
    
    public boolean deleteMonAn(int spId) {
        try {
            return monAnDAO.deleteMonAn(spId);
        } catch (SQLException e) {
            System.out.println("Lỗi khi xóa món ăn: " + e.getMessage());
            return false;
        }
    }
    
}
