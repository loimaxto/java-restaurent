/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.retaurant.DTO;

/**
 *
 * @author light
 */
public class CtHoaDonDTO {
    private Integer hdId;
    private Integer spdId;
    private Integer soLuong;
    private Integer giaTaiLucDat;
    private Integer tongTienCt;

    
    
    public CtHoaDonDTO(Integer hdId, Integer spdId, Integer soLuong, Integer giaTaiLucDat) {
        this.hdId = hdId;
        this.spdId = spdId;
        this.soLuong = soLuong;
        this.giaTaiLucDat = giaTaiLucDat;
    }
    public CtHoaDonDTO(Integer hdId, Integer spdId, Integer soLuong, Integer giaTaiLucDat,Integer tongTienCt) {
        this.hdId = hdId;
        this.spdId = spdId;
        this.soLuong = soLuong;
        this.giaTaiLucDat = giaTaiLucDat;
        this.tongTienCt = tongTienCt;
    }
    public Integer getHdId() {
        return hdId;
    }

    public void setHdId(Integer hdId) {
        this.hdId = hdId;
    }

    public Integer getSpdId() {
        return spdId;
    }

    public void setSpdId(Integer spdId) {
        this.spdId = spdId;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public Integer getGiaTaiLucDat() {
        return giaTaiLucDat;
    }

    public void setGiaTaiLucDat(Integer giaTaiLucDat) {
        this.giaTaiLucDat = giaTaiLucDat;
    }
    public Integer getTongTienCt() {
        return tongTienCt;
    }

    public void setTongTienCt(Integer tongTienCt) {
        this.tongTienCt = tongTienCt;
    }
    public CtHoaDonDTO() {
    }
    @Override
    public String toString() {
        return "CtHoaDonDTO{" +
                "hdId=" + hdId +
                ", spdId=" + spdId +
                ", soLuong=" + soLuong +
                ", giaTaiLucDat=" + giaTaiLucDat +
                '}';
    }
}
