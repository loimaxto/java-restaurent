package com.example.retaurant.DTO;

public class CTPhieuXuatDTO {
    private int pxId;
    private int nlId;
    private String tenSp;
    private float soLuong;
    private String donVi;
    
    // Constructors
    public CTPhieuXuatDTO() {}
    
    public CTPhieuXuatDTO(int pxId, int nlId, String tenSp, float soLuong) {
        this.pxId = pxId;
        this.nlId = nlId;
        this.tenSp = tenSp;
        this.soLuong = soLuong;
    }

    // Getters and Setters
    public int getPxId() { return pxId; }
    public void setPxId(int pxId) { this.pxId = pxId; }
    
    public int getNlId() { return nlId; }
    public void setNlId(int nlId) { this.nlId = nlId; }
    
    public String getTenSp() { return tenSp; }
    public void setTenSp(String tenSp) { this.tenSp = tenSp; }
    
    public float getSoLuong() { return soLuong; }
    public void setSoLuong(float soLuong) { this.soLuong = soLuong; }
    
    public String getDonVi() { return donVi; }
    public void setDonVi(String donVi) { this.donVi = donVi; }
}