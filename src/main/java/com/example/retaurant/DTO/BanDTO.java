/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.retaurant.DTO;

/**
 *
 * @author light
 */

public class BanDTO {
    private Integer banId;
    private String tenBan;
    private Integer tinhTrangSuDung; //1 dang dat \ 2 trống
    private Integer trangThaiBan; // 1: dung \ 2 xoa
    private Integer idHoaDonHienTai;
    public BanDTO() {
    }

    public BanDTO(Integer banId, String tenBan, Integer tinhTrangSuDung, Integer trangThaiBan,Integer idHoaDonHienTai) {
        this.banId = banId;
        this.tenBan = tenBan;
        this.idHoaDonHienTai = idHoaDonHienTai;
        this.tinhTrangSuDung = tinhTrangSuDung;
        this.trangThaiBan = trangThaiBan;
    }

    public Integer getIdHoaDonHienTai() {
        return idHoaDonHienTai;
    }

    public void setIdHoaDonHienTai(Integer idHoaDonHienTai) {
        this.idHoaDonHienTai = idHoaDonHienTai;
    }
    
    public Integer getBanId() {
        return banId;
    }

    public void setBanId(Integer banId) {
        this.banId = banId;
    }

    public String getTenBan() {
        return tenBan;
    }

    public void setTenBan(String tenBan) {
        this.tenBan = tenBan;
    }

    public Integer getTinhTrangSuDung() {
        return tinhTrangSuDung;
    }

    public void setTinhTrangSuDung(Integer tinhTrangSuDung) {
        this.tinhTrangSuDung = tinhTrangSuDung;
    }

    public Integer getTrangThaiBan() {
        return trangThaiBan;
    }

    public void setTrangThaiBan(Integer trangThaiBan) {
        this.trangThaiBan = trangThaiBan;
    }

    @Override
    public String toString() {
        return "BanDTO{" +
                "banId=" + banId +
                ", tenBan='" + tenBan + '\'' +
                ", tinhTrangSuDung=" + tinhTrangSuDung +
                ", trangThaiBan=" + trangThaiBan +
                ", current bill=" + idHoaDonHienTai +
                '}';
    }
}
