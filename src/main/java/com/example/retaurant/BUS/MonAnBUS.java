/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.retaurant.BUS;

import com.example.retaurant.DAO.MonAnDAO;
import com.example.retaurant.DTO.MonAnDTO;
import com.example.retaurant.utils.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class MonAnBUS {
    
    private Connection connection = DBConnection.getConnection();
    private MonAnDAO monAnDAO ;
    public MonAnBUS() {
        monAnDAO = new MonAnDAO(connection);
    }

   public List<MonAnDTO> getAllMonAn() {
        try {
            return monAnDAO.getAllDsMonAn();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(MonAnBUS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public List<MonAnDTO> searchMonAnByName(String keyword) {
        try {
            return monAnDAO.searchMonAnByName(keyword);
        } catch (SQLException ex) {
            ex.printStackTrace();
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
            if(monAn.getGiaSp()<=0){
            return false;
        }
        List<MonAnDTO> danhSach = getAllMonAn();
        for (MonAnDTO mon : danhSach) {
            if(monAn.getSpId()== mon.getSpId()||monAn.getSpId()<0){
                return false;
            }
        }
            return monAnDAO.addMonAn(monAn) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
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
    
    public static void main(String[] args) {
        MonAnBUS monAnBUS = new MonAnBUS();
        List<MonAnDTO> dsMonAn = monAnBUS.getAllMonAn();
        for (MonAnDTO monAn : dsMonAn) {
            System.out.println("ID: " + monAn.getSpId() + ", Tên món ăn: " + monAn.getTenSp() + ", Gia : " + monAn.getGiaSp() + ", Trang thái: " + monAn.getTrangThai());        
        }
    }
}
