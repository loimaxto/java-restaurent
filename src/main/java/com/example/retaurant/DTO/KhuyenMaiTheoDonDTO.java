/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.retaurant.DTO;

/**
 *
 * @author Administrator
 */
public class KhuyenMaiTheoDonDTO {
    private Integer kmId;
    private Integer phanTram;

    public KhuyenMaiTheoDonDTO() {
    }

    public KhuyenMaiTheoDonDTO(Integer kmId, Integer phanTram) {
        this.kmId = kmId;
        this.phanTram = phanTram;
    }

    public Integer getKmId() {
        return kmId;
    }

    public void setKmId(Integer kmId) {
        this.kmId = kmId;
    }

    public Integer getPhanTram() {
        return phanTram;
    }

    public void setPhanTram(Integer phanTram) {
        this.phanTram = phanTram;
    }
    
    @Override
    public String toString(){
        return "MonAnDTO{"+"kmId="+kmId+", phanTram="+phanTram+"}";
    }
    
}
