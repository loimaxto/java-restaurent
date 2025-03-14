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
    private String tenKh;

    public CustomerDTO() {
    }

    public CustomerDTO(Integer khId, String sdt, String tenKh) {
        this.khId = khId;
        this.sdt = sdt;
        this.tenKh = tenKh;
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
