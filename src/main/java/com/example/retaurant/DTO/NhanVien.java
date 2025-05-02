/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.retaurant.DTO;

/**
 *
 * @author ASUS
 */
public class NhanVien {
    private int maNhanvien;
    private String hoTen;
    private String gioiTinh;
    private String chucVu ;
    private int maTaiKhoan; 

    public NhanVien() {
    }
    
    public NhanVien(int maNhanvien, String hoTen, String gioiTinh, String chucVu, int maTaiKhoan) {
        this.maNhanvien = maNhanvien;
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        this.chucVu = chucVu;
        this.maTaiKhoan = maTaiKhoan;
    }

    public int getMaNhanvien() {
        return maNhanvien;
    }

    public void setMaNhanvien(int maNhanvien) {
        this.maNhanvien = maNhanvien;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    public int getMaTaiKhoan() {
        return maTaiKhoan;
    }

    public void setMaTaiKhoan(int maTaiKhoan) {
        this.maTaiKhoan = maTaiKhoan;
    }

    @Override
    public String toString() {
        return "NhanVien{" + "maNhanvien=" + maNhanvien + ", hoTen=" + hoTen + ", gioiTinh=" + gioiTinh + ", chucVu=" + chucVu + ", maTaiKhoan=" + maTaiKhoan + '}';
    }
}
