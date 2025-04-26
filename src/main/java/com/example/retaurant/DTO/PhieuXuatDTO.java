package com.example.retaurant.DTO;

import java.util.Date;
import java.util.List;

public class PhieuXuatDTO {
    private int pxId;
    private Date ngayXuat;
    private int nguoiXuatId;
    private List<CTPhieuXuatDTO> chiTietPhieuXuat;

    // Constructors
    public PhieuXuatDTO() {}
    
    public PhieuXuatDTO(int pxId, Date ngayXuat, int nguoiXuatId) {
        this.pxId = pxId;
        this.ngayXuat = ngayXuat;
        this.nguoiXuatId = nguoiXuatId;
    }

    // Getters and Setters
    public int getPxId() { return pxId; }
    public void setPxId(int pxId) { this.pxId = pxId; }
    
    public Date getNgayXuat() { return ngayXuat; }
    public void setNgayXuat(Date ngayXuat) { this.ngayXuat = ngayXuat; }
    
    public int getNguoiXuatId() { return nguoiXuatId; }
    public void setNguoiXuatId(int nguoiXuatId) { this.nguoiXuatId = nguoiXuatId; }
    
    public List<CTPhieuXuatDTO> getChiTietPhieuXuat() { return chiTietPhieuXuat; }
    public void setChiTietPhieuXuat(List<CTPhieuXuatDTO> chiTietPhieuXuat) { 
        this.chiTietPhieuXuat = chiTietPhieuXuat; 
    }
}