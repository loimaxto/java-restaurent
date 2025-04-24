package com.example.retaurant.DTO;

import java.util.Date;
import java.util.List;

public class PhieuNhapDTO {
    private int pnId;
    private Date ngayNhap;
    private int nccId;
    private int nguoiNhapId;
    private long tongTien;
    private List<ChiTietPhieuNhapDTO> chiTietPhieuNhap;

    // Constructors
    public PhieuNhapDTO() {}
    
    public PhieuNhapDTO(int pnId, Date ngayNhap, int nccId, int nguoiNhapId, long tongTien) {
        this.pnId = pnId;
        this.ngayNhap = ngayNhap;
        this.nccId = nccId;
        this.nguoiNhapId = nguoiNhapId;
        this.tongTien = tongTien;
    }

    // Getters and Setters
    public int getPnId() { return pnId; }
    public void setPnId(int pnId) { this.pnId = pnId; }
    
    public Date getNgayNhap() { return ngayNhap; }
    public void setNgayNhap(Date ngayNhap) { this.ngayNhap = ngayNhap; }
    
    public int getNccId() { return nccId; }
    public void setNccId(int nccId) { this.nccId = nccId; }
    
    public int getNguoiNhapId() { return nguoiNhapId; }
    public void setNguoiNhapId(int nguoiNhapId) { this.nguoiNhapId = nguoiNhapId; }
    
    public long getTongTien() { return tongTien; }
    public void setTongTien(long tongTien) { this.tongTien = tongTien; }
    
    public List<ChiTietPhieuNhapDTO> getChiTietPhieuNhap() { return chiTietPhieuNhap; }
    public void setChiTietPhieuNhap(List<ChiTietPhieuNhapDTO> chiTietPhieuNhap) { 
        this.chiTietPhieuNhap = chiTietPhieuNhap; 
    }
}
