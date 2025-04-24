package com.example.retaurant.DTO;

public class ChiTietPhieuNhapDTO {
    private int pnId;
    private int nlId;
    private String tenSp;
    private long donGiaMoiDonVi;
    private long soLuong;
    private long tongTien;

    // Constructors
    public ChiTietPhieuNhapDTO() {}
    
    public ChiTietPhieuNhapDTO(int pnId, int nlId, String tenSp, long donGiaMoiDonVi, long soLuong, long tongTien) {
        this.pnId = pnId;
        this.nlId = nlId;
        this.tenSp = tenSp;
        this.donGiaMoiDonVi = donGiaMoiDonVi;
        this.soLuong = soLuong;
        this.tongTien = tongTien;
    }

    // Getters and Setters
    public int getPnId() { return pnId; }
    public void setPnId(int pnId) { this.pnId = pnId; }
    
    public int getNlId() { return nlId; }
    public void setNlId(int nlId) { this.nlId = nlId; }
    
    public String getTenSp() { return tenSp; }
    public void setTenSp(String tenSp) { this.tenSp = tenSp; }
    
    public long getDonGiaMoiDonVi() { return donGiaMoiDonVi; }
    public void setDonGiaMoiDonVi(long donGiaMoiDonVi) { this.donGiaMoiDonVi = donGiaMoiDonVi; }
    
    public long getSoLuong() { return soLuong; }
    public void setSoLuong(long soLuong) { this.soLuong = soLuong; }
    
    public long getTongTien() { return tongTien; }
    public void setTongTien(long tongTien) { this.tongTien = tongTien; }
}
