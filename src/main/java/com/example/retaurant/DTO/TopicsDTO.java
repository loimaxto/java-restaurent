package com.example.retaurant.DTO;

public class TopicsDTO {
    private int tpID;
    private String tpTitle;
    private Integer tpParent;
    private int tpStatus;

    // Cóntructor không tham số
    public TopicsDTO() {
    }

    // Constructor có tham số
    public TopicsDTO(int tpID, String tpTitle, int tpParent, int tpStatus) {
        this.tpID = tpID;
        this.tpTitle = tpTitle;
        this.tpParent = tpParent;
        this.tpStatus = tpStatus;
    }

    // Getter và Setter cho từng trường
    public int getTpID() {
        return tpID;
    }

    public void setTpID(int tpID) {
        this.tpID = tpID;
    }

    public String getTpTitle() {
        return tpTitle;
    }

    public void setTpTitle(String tpTitle) {
        this.tpTitle = tpTitle;
    }

    public Integer getTpParent() {
        return tpParent;
    }

    public void setTpParent(Integer tpParent) {
        this.tpParent = tpParent;
    }

    public int getTpStatus() {
        return tpStatus;
    }

    public void setTpStatus(int tpStatus) {
        this.tpStatus = tpStatus;
    }
}
