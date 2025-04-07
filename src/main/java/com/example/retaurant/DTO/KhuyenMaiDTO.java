/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.retaurant.DTO;

/**
 *
 * @author light
 */

import java.sql.Date;

public class KhuyenMaiDTO {
    private Integer kmId;
    private String tenKm;
    private Date ngayBd;
    private Date ngayKt;
    private Integer loaiKm;

    public KhuyenMaiDTO() {}

    public KhuyenMaiDTO(Integer kmId, String tenKm, Date ngayBd, Date ngayKt, Integer loaiKm) {
        this.kmId = kmId;
        this.tenKm = tenKm;
        this.ngayBd = ngayBd;
        this.ngayKt = ngayKt;
        this.loaiKm = loaiKm;
    }

    public Integer getKmId() {
        return kmId;
    }

    public void setKmId(Integer kmId) {
        this.kmId = kmId;
    }

    public String getTenKm() {
        return tenKm;
    }

    public void setTenKm(String tenKm) {
        this.tenKm = tenKm;
    }

    public Date getNgayBd() {
        return ngayBd;
    }

    public void setNgayBd(Date ngayBd) {
        this.ngayBd = ngayBd;
    }

    public Date getNgayKt() {
        return ngayKt;
    }

    public void setNgayKt(Date ngayKt) {
        this.ngayKt = ngayKt;
    }

    public Integer getLoaiKm() {
        return loaiKm;
    }

    public void setLoaiKm(Integer loaiKm) {
        this.loaiKm = loaiKm;
    }

    @Override
    public String toString() {
        return "PromotionsDTO{" +
                "kmId=" + kmId +
                ", tenKm='" + tenKm + '\'' +
                ", ngayBd=" + ngayBd +
                ", ngayKt=" + ngayKt +
                ", loaiKm=" + loaiKm +
                '}';
    }
}
