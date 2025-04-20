package com.example.retaurant.DTO;

public class NhaCungCapDTO {
    private int ncc_id;
    private String ten_ncc;
    private String sdt;
    private String dchi;

    public NhaCungCapDTO() {
    }

    public NhaCungCapDTO(int ncc_id, String ten_ncc, String sdt, String dchi) {
        this.ncc_id = ncc_id;
        this.ten_ncc = ten_ncc;
        this.sdt = sdt;
        this.dchi = dchi;
    }

    // Getters and Setters
    public int getNcc_id() {
        return ncc_id;
    }

    public void setNcc_id(int ncc_id) {
        this.ncc_id = ncc_id;
    }

    public String getTen_ncc() {
        return ten_ncc;
    }

    public void setTen_ncc(String ten_ncc) {
        this.ten_ncc = ten_ncc;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDchi() {
        return dchi;
    }

    public void setDchi(String dchi) {
        this.dchi = dchi;
    }

    @Override
    public String toString() {
        return "NhaCungCapDTO{" +
                "ncc_id=" + ncc_id +
                ", ten_ncc='" + ten_ncc + '\'' +
                ", sdt='" + sdt + '\'' +
                ", dchi='" + dchi + '\'' +
                '}';
    }
}