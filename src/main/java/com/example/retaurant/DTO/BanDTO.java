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
    private Integer tinhTrangSuDung;
    private Integer trangThaiBan;

    public BanDTO() {
    }

    public BanDTO(Integer banId, String tenBan, Integer tinhTrangSuDung, Integer trangThaiBan) {
        this.banId = banId;
        this.tenBan = tenBan;
        this.tinhTrangSuDung = tinhTrangSuDung;
        this.trangThaiBan = trangThaiBan;
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
                '}';
    }
}
