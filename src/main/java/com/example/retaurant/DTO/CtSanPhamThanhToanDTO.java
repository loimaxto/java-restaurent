/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.retaurant.DTO;

/**
 *
 * @author light
 */
public class CtSanPhamThanhToanDTO extends CtHoaDonDTO {
    private String tenSp;
    public CtSanPhamThanhToanDTO(Integer hdId, Integer spdId, Integer soLuong, Integer giaTaiLucDat,Integer tongTienCt,String tenSp) {
        super(hdId, spdId, soLuong, giaTaiLucDat, tongTienCt);
        this.tenSp = tenSp;
    }

    public String getTenSp() {
        return tenSp;
    }

    public void setTenSp(String tenSp) {
        this.tenSp = tenSp;
    }
    
    
}
