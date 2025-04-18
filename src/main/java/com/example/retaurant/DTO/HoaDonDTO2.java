/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.retaurant.DTO;

import java.sql.Timestamp;

/**
 *
 * @author light
 */
public class HoaDonDTO2 extends HoaDonDTO{
    
    private String hotenNv;
    private String hoKh;
    private String sdt;
    @Override
    public String toString() {
        return "HoaDonDTO2{" + "hotenNv=" + hotenNv + ", hoKh=" + hoKh + ", tenKh=" + tenKh + '}';
    }
    private String tenKh;

    public HoaDonDTO2() {
    }

    public HoaDonDTO2(String hotenNv, String hoKh, String sdt, String tenKh, Integer hdId, Timestamp thoiGian, Byte ghiChu, Integer tongGia, Integer khId, Integer banId, Integer nguoiLapId, Integer kmId) {
        super(hdId, thoiGian, ghiChu, tongGia, khId, banId, nguoiLapId, kmId);
        this.hotenNv = hotenNv;
        this.hoKh = hoKh;
        this.sdt = sdt;
        this.tenKh = tenKh;
    }

    public String getHotenNv() {
        return hotenNv;
    }

    public void setHotenNv(String hotenNv) {
        this.hotenNv = hotenNv;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getHoTenNv() {
        return hotenNv;
    }

    public void setHoTenNv(String tenNv) {
        this.hotenNv = tenNv;
    }

    public String getHoKh() {
        return hoKh;
    }

    public void setHoKh(String hoKh) {
        this.hoKh = hoKh;
    }

    public String getTenKh() {
        return tenKh;
    }

    public void setTenKh(String tenKh) {
        this.tenKh = tenKh;
    }
    
}
