
/**
 *
 * @author light
 */
package com.example.retaurant.BUS;

import com.example.retaurant.DAO.HoaDonDAO;
import com.example.retaurant.DTO.HoaDonDTO;
import com.example.retaurant.DTO.HoaDonDTO2;

import java.sql.SQLException;
import java.util.List;

public class HoaDonBUS {

    private HoaDonDAO hoaDonDAO;
    
    public HoaDonBUS() {
        this.hoaDonDAO = new HoaDonDAO();
    }

    public HoaDonDTO getBillById(Integer hdId) {
        try {
            return hoaDonDAO.getBillById(hdId);
        } catch (SQLException e) {
            e.printStackTrace(); 
            return null;
        }
    }

    public List<HoaDonDTO2> getBills() {
        try {
            return hoaDonDAO.getAllBills();
        } catch (SQLException e) {
            e.printStackTrace(); 
            return null; 
        }
    }
    public int addDefaultHoaDon(int tableId, int creatorId) {
        return hoaDonDAO.addDefaultHoaDon(tableId,creatorId);
    }
    public int addBill(HoaDonDTO bill) {
        try {
            // return id of hoa don moi tao
            return hoaDonDAO.addBill(bill); 
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; 
        }
    }

    public boolean updateBill(HoaDonDTO bill) {
        try {
            return hoaDonDAO.updateBill(bill);
        } catch (SQLException e) {
            e.printStackTrace(); 
            return false;
        }
    }

    public boolean deleteBill(Integer hdId) {
        try {
            return hoaDonDAO.deleteBill(hdId);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
