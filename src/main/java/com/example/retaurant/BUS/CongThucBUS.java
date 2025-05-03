/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.retaurant.BUS;

/**
 *
 * @author Administrator
 */
import com.example.retaurant.DAO.CongThucDAO;
import com.example.retaurant.DTO.CongThucDTO;
import com.example.retaurant.utils.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CongThucBUS {

    private Connection connection;
    private CongThucDAO congThucDAO;

    public CongThucBUS() {
        this.connection = DBConnection.getConnection();
        this.congThucDAO = new CongThucDAO(this.connection);
    }
    
    
    public CongThucBUS(Connection connection) {
        congThucDAO = new CongThucDAO(connection);
        
    }

    public boolean checkSoluong(CongThucDTO ct){
        if(ct.getSoluong()>0)
               return true;
        return false;
    }
    
    public boolean checkCongThuc(CongThucDTO ct) throws SQLException{
        List<CongThucDTO> dsct = getAllCongThuc();
        for(CongThucDTO cts: dsct){
            if(cts.getNlid()==ct.getNlid() && cts.getSpid()==ct.getSpid())
                return true;
        }
        return false;
    }
    
    public List<CongThucDTO> getAllCongThuc() throws SQLException {
        try {
            return congThucDAO.getAllDsCongThuc();
        } catch (SQLException e) {
            // Xử lý lỗi tại đây
            throw new SQLException("Error fetching list of recipes", e);
        }
    }
    public List<CongThucDTO> getCongThucByMonAn(int monAnId) {
            return congThucDAO.getAllDsCongThuc(monAnId);
    }
    // Thêm một công thức mới
    public int addCongThuc(CongThucDTO congThuc) throws SQLException {
        
        try {
            return congThucDAO.addCongThuc(congThuc);
        } catch (SQLException e) {
            throw new SQLException("Error adding recipe", e);
        }
    }

    public boolean deleteCongThuc(int sp_Id, int nl_Id) throws SQLException {
        try {
            return congThucDAO.deleteCongThuc(sp_Id, nl_Id);
        } catch (SQLException e) {
            throw new SQLException("Error deleting recipe", e);
        }
    }

    public boolean updateCongThuc(CongThucDTO congThuc)  {
        try {
            return congThucDAO.updateCongThuc(congThuc);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static void main(String[] args) throws SQLException {
        CongThucBUS ctbus = new CongThucBUS();
        List<CongThucDTO> dsct = ctbus.getAllCongThuc();
        for (CongThucDTO ct : dsct) {
            System.out.println(ct.getSpid()+"      "+ct.getNlid()+"    "+ ct.getSoluong());
            
        }
         
    }
}
