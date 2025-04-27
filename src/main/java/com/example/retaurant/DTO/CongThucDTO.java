/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.retaurant.DTO;

/**
 *
 * @author Administrator
 */
public class CongThucDTO {
    private Integer spid;
    private Integer nlid;
    private Integer soluong;

    public CongThucDTO() {
    }

    public CongThucDTO(Integer spid, Integer nlid, Integer soluong) {
        this.spid = spid;
        this.nlid = nlid;
        this.soluong = soluong;
    }

    public Integer getSpid() {
        return spid;
    }

    public void setSpid(Integer spid) {
        this.spid = spid;
    }

    public Integer getNlid() {
        return nlid;
    }

    public void setNlid(Integer nlid) {
        this.nlid = nlid;
    }

    public Integer getSoluong() {
        return soluong;
    }

    public void setSoluong(Integer soluong) {
        this.soluong = soluong;
    }
    
    
   @Override
    public String toString() {
        return "CongThucDTO{" + "spid=" + spid + ", nlid=" + nlid + ", soluong=" + soluong + '}';
    }
}
