/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.retaurant.DTO;

/**
 *
 * @author light
 */
public class CustomerDTO {
    private Integer khId; // Keep khId as int for auto-incremented primary key
    private String sdt;
    private String hoKh;
    private String tenKh;

    public CustomerDTO() {
    }

    public CustomerDTO(Integer khId, String sdt, String tenKh, String hoKh) {
        this.khId = khId;
        this.sdt = sdt;
        this.tenKh = tenKh;
        this.hoKh = hoKh;
    }

    public String getHoKh() {
        return hoKh;
    }

    public void setHoKh(String hoKh) {
        this.hoKh = hoKh;
    }

    public Integer getKhId() {
        return khId;
    }

    public void setKhId(Integer khId) {
        this.khId = khId;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getTenKh() {
        return tenKh;
    }

    public void setTenKh(String tenKh) {
        this.tenKh = tenKh;
    }

    @Override
    public String toString() {
        return "CustomerDTO{" +
                "khId=" + khId +
                ", sdt='" + sdt + '\'' +
                ", tenKh='" + tenKh + '\'' +
                '}';
    }
}
