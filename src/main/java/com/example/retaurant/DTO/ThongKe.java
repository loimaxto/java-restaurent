/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.retaurant.DTO;

import java.util.ArrayList;

public class ThongKe {
    public int soLuongMonAn ; 
    public int soLuongKH ; 
    public int tongDoanhThu ; 
    public ArrayList<MonAnDTO> topMonAnBanChay ;
    
    public ThongKe(){
        
    }

    public ThongKe(int soLuongMonAn, int soLuongKH, int tongDoanhThu, ArrayList<MonAnDTO> topMonAnBanChay) {
        this.soLuongMonAn = soLuongMonAn;
        this.soLuongKH = soLuongKH;
        this.tongDoanhThu = tongDoanhThu;
        this.topMonAnBanChay = topMonAnBanChay;
    }

    public int getSoLuongMonAn() {
        return soLuongMonAn;
    }

    public void setSoLuongMonAn(int soLuongMonAn) {
        this.soLuongMonAn = soLuongMonAn;
    }

    public int getSoLuongKH() {
        return soLuongKH;
    }

    public void setSoLuongKH(int soLuongKH) {
        this.soLuongKH = soLuongKH;
    }

    public int getTongDoanhThu() {
        return tongDoanhThu;
    }

    public void setTongDoanhThu(int tongDoanhThu) {
        this.tongDoanhThu = tongDoanhThu;
    }

    public ArrayList<MonAnDTO> getTopMonAnBanChay() {
        return topMonAnBanChay;
    }

    public void setTopMonAnBanChay(ArrayList<MonAnDTO> topMonAnBanChay) {
        this.topMonAnBanChay = topMonAnBanChay;
    }
       
}
