package com.example.retaurant.DTO;

/**
 *
 * @author Administrator
 */
public class NguyenLieuDTO {
    
    private Integer nlId;
    private String tenNl;
    private String donVi;
    private Integer soLuong;

    public NguyenLieuDTO() {
    }

    public NguyenLieuDTO(Integer nlId, String tenNl, String donVi, Integer soLuong) {
        this.nlId = nlId;
        this.tenNl = tenNl;
        this.donVi = donVi;
        this.soLuong = soLuong;
    }

    public Integer getNlId() {
        return nlId;
    }

    public void setNlId(Integer nlId) {
        this.nlId = nlId;
    }

    public String getTenNl() {
        return tenNl;
    }

    public void setTenNl(String tenNl) {
        this.tenNl = tenNl;
    }

    public String getDonVi() {
        return donVi;
    }

    public void setDonVi(String donVi) {
        this.donVi = donVi;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }
    
    @Override
    public String toString() {
        return "NguyenLieuDTO{" + "nlId=" + nlId + ", tenNl=" + tenNl + 
               ", donVi=" + donVi + ", soLuong=" + soLuong + "}";
    }
}