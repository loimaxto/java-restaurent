/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.retaurant.DTO;

/**
 *
 * @author Administrator
 */
public class MonAnDTO {
    
    
    private Integer spId;
    private String tenSp;
    private Integer giaSp;
    private Integer trangThai;

    public MonAnDTO() {
    }

    public MonAnDTO(Integer spId, String tenSp, Integer giaSp, Integer trangThai) {
        this.spId = spId;
        this.tenSp = tenSp;
        this.giaSp = giaSp;
        this.trangThai = trangThai;
    }

    public Integer getSpId() {
        return spId;
    }

    public void setSpId(Integer spId) {
        this.spId = spId;
    }

    public String getTenSp() {
        return tenSp;
    }

    public void setTenSp(String tenSp) {
        this.tenSp = tenSp;
    }

    public Integer getGiaSp() {
        return giaSp;
    }

    public void setGiaSp(Integer giasp) {
        this.giaSp = giaSp;
    }

    public Integer getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Integer trangThai) {
        this.trangThai = trangThai;
    }

    
    @Override
    public String toString(){
        return "MonAnDTO{"+"spId="+spId +", tenSp="+tenSp+", giaSp="+giaSp+", trangThai="+trangThai+"}";
    }
}

