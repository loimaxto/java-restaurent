/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.retaurant.BUS;

import com.example.retaurant.DAO.ThongKeDAO;
import com.example.retaurant.DTO.MonAnDTO;
import com.example.retaurant.DTO.ThongKe;
import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class ThongKeBUS {

    public ThongKeDAO thongKeDAO = new ThongKeDAO();
    private ArrayList<Double> doanhThuThang;

    public ThongKe thongKe() {
        return thongKeDAO.getThongKe();
    }

    public double getDoanhThuThang(int thang, int nam) {
        return thongKeDAO.getDoanhThuThang(thang, nam);
    }

    public int getDoanhThuNgay(int ngay, int thang, int nam) {
        ThongKeDAO dao = new ThongKeDAO();
        return dao.getDoanhThuNgay(ngay, thang, nam);
    }

    public int getDoanhThuKhoangNgay(String tuNgay, String denNgay) {
        ThongKeDAO dao = new ThongKeDAO();
        return dao.getDoanhThuKhoangNgay(tuNgay, denNgay);
    }

    public ArrayList<MonAnDTO> getTopMonAn() {
        return thongKeDAO.getTopBanChay();
    }

    public ArrayList<MonAnDTO> getTopBanChayTheoThang(int thang, int nam) {
        return thongKeDAO.getTopBanChayTheoThang(thang, nam);
    }

    public ArrayList<MonAnDTO> getTopBanChayTheoQuy(int quy) {
        int startMonth = (quy - 1) * 3 + 1;
        int endMonth = startMonth + 2;
        return thongKeDAO.getTopBanChayTheoKhoangThang(startMonth, endMonth);
    }
}
