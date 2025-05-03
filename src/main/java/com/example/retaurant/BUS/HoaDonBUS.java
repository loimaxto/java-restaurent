/**
 *
 * @author light
 */
package com.example.retaurant.BUS;

import com.example.retaurant.DAO.HoaDonDAO;
import com.example.retaurant.DTO.HoaDonDTO;
import com.example.retaurant.DTO.HoaDonDTO2;
import com.example.retaurant.DTO.SearchCriteria;
import com.example.retaurant.MyCustom.MyDialog;

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

    public HoaDonDTO2 getBillDTO2ById(Integer hdId) {
        return hoaDonDAO.getBillDTO2ById(hdId);
    }

    public List<HoaDonDTO2> getBills() {
        return hoaDonDAO.getAllBills();
    }

    public int addDefaultHoaDon(int tableId, int creatorId) {
        return hoaDonDAO.addDefaultHoaDon(tableId, creatorId);
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
        return hoaDonDAO.updateBill(bill);
    }

    public boolean deleteBill(Integer hdId) {
        try {
            return hoaDonDAO.deleteBill(hdId);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<HoaDonDTO2> searchHoaDon(SearchCriteria dataSearch) {
        //date start > date end

        if (dataSearch.getStartDate() != null && dataSearch.getEndDate() != null
            && dataSearch.getStartDate().compareTo(dataSearch.getEndDate()) > 0) {
            new MyDialog("Ngày bắt đầu > ngày kết thúc", 0);
            return null;
        }
        return hoaDonDAO.getListHoaDonByCriteria(dataSearch);
    }
}
