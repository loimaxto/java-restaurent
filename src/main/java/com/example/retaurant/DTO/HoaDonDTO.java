/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.retaurant.DTO;

/**
 *
 * @author light
 */
import java.sql.Timestamp;

public class HoaDonDTO {
    private Integer hdId; // Converted to Integer
    private Timestamp thoiGian;
    private Byte ghiChu;
    private Integer tongGia; // Converted to Integer
    private Integer khId; // Converted to Integer (nullable)
    private Integer banId; // Converted to Integer (nullable)
    private Integer nguoiLapId; // Converted to Integer (nullable)
    private Integer kmId; // Converted to Integer (nullable)

    public HoaDonDTO() {
    }

    public HoaDonDTO(Integer hdId, Timestamp thoiGian, Byte ghiChu, Integer tongGia, Integer khId, Integer banId, Integer nguoiLapId, Integer kmId) {
        this.hdId = hdId;
        this.thoiGian = thoiGian;
        this.ghiChu = ghiChu;
        this.tongGia = tongGia;
        this.khId = khId;
        this.banId = banId;
        this.nguoiLapId = nguoiLapId;
        this.kmId = kmId;
    }

    public Integer getHdId() {
        return hdId;
    }

    public void setHdId(Integer hdId) {
        this.hdId = hdId;
    }

    public Timestamp getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(Timestamp thoiGian) {
        this.thoiGian = thoiGian;
    }

    public Byte getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(Byte ghiChu) {
        this.ghiChu = ghiChu;
    }

    public Integer getTongGia() {
        return tongGia;
    }

    public void setTongGia(Integer tongGia) {
        this.tongGia = tongGia;
    }

    public Integer getKhId() {
        return khId;
    }

    public void setKhId(Integer khId) {
        this.khId = khId;
    }

    public Integer getBanId() {
        return banId;
    }

    public void setBanId(Integer banId) {
        this.banId = banId;
    }

    public Integer getNguoiLapId() {
        return nguoiLapId;
    }

    public void setNguoiLapId(Integer nguoiLapId) {
        this.nguoiLapId = nguoiLapId;
    }

    public Integer getKmId() {
        return kmId;
    }

    public void setKmId(Integer kmId) {
        this.kmId = kmId;
    }

    @Override
    public String toString() {
        return "BillDTO{" +
                "hdId=" + hdId +
                ", thoiGian=" + thoiGian +
                ", ghiChu=" + ghiChu +
                ", tongGia=" + tongGia +
                ", khId=" + khId +
                ", banId=" + banId +
                ", nguoiLapId=" + nguoiLapId +
                ", kmId=" + kmId +
                '}';
    }
}
