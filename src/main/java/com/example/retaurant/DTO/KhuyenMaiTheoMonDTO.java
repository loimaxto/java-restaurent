/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.retaurant.DTO;

/**
 *
 * @author Administrator
 */
public class KhuyenMaiTheoMonDTO {
    private Integer kmId;
    private Integer maSp;
    private Integer soTien;

    public KhuyenMaiTheoMonDTO() {
    }

    public KhuyenMaiTheoMonDTO(Integer kmId, Integer maSp, Integer soTien) {
        this.kmId = kmId;
        this.maSp = maSp;
        this.soTien = soTien;
    }

    public Integer getKmId() {
        return kmId;
    }

    public void setKmId(Integer kmId) {
        this.kmId = kmId;
    }

    public Integer getMaSp() {
        return maSp;
    }

    public void setMaSp(Integer maSp) {
        this.maSp = maSp;
    }

    public Integer getSoTien() {
        return soTien;
    }

    public void setSoTien(Integer soTien) {
        this.soTien = soTien;
    }
    
    @Override
    public String toString(){
        return "KhuyenMaiTheoMonDTO{"+"kmId="+kmId+", maSp="+maSp+", soTien="+soTien+"}";
    }
    
    
}
