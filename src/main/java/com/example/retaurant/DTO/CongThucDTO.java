package com.example.retaurant.DTO;

/**
 *
 * @author Administrator
 */
public class CongThucDTO {
    
    private Integer spId;
    private Integer nlId;
    private Integer soLuong;

    public CongThucDTO() {
    }

    public CongThucDTO(Integer spId, Integer nlId, Integer soLuong) {
        this.spId = spId;
        this.nlId = nlId;
        this.soLuong = soLuong;
    }

    public Integer getSpId() {
        return spId;
    }

    public void setSpId(Integer spId) {
        this.spId = spId;
    }

    public Integer getNlId() {
        return nlId;
    }

    public void setNlId(Integer nlId) {
        this.nlId = nlId;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }
    
    @Override
    public String toString() {
        return "CongThucDTO{" + "spId=" + spId + ", nlId=" + nlId + 
               ", soLuong=" + soLuong + "}";
    }
}