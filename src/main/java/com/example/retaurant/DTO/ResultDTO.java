/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.retaurant.DTO;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 *
 * @author light
 */
public class ResultDTO {
    private int rsNum;
    private int userID;
    private String exCode;
    private String rsAnswers;
    private BigDecimal rsMark;
    private Timestamp rsDate;

    public ResultDTO() {
    }

    public ResultDTO(int rsNum, int userID, String exCode, String rsAnswers, BigDecimal rsMark, Timestamp rsDate) {
        this.rsNum = rsNum;
        this.userID = userID;
        this.exCode = exCode;
        this.rsAnswers = rsAnswers;
        this.rsMark = rsMark;
        this.rsDate = rsDate;
    }

    public int getRsNum() {
        return rsNum;
    }

    public void setRsNum(int rsNum) {
        this.rsNum = rsNum;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getExCode() {
        return exCode;
    }

    public void setExCode(String exCode) {
        this.exCode = exCode;
    }

    public String getRsAnswers() {
        return rsAnswers;
    }

    public void setRsAnswers(String rsAnswers) {
        this.rsAnswers = rsAnswers;
    }

    public BigDecimal getRsMark() {
        return rsMark;
    }

    public void setRsMark(BigDecimal rsMark) {
        this.rsMark = rsMark;
    }

    public Timestamp getRsDate() {
        return rsDate;
    }

    public void setRsDate(Timestamp rsDate) {
        this.rsDate = rsDate;
    }

    @Override
    public String toString() {
        return "ResultDTO{" +
                "rsNum=" + rsNum +
                ", userID=" + userID +
                ", exCode='" + exCode + '\'' +
                ", rsAnswers='" + rsAnswers + '\'' +
                ", rsMark=" + rsMark +
                ", rsDate=" + rsDate +
                '}';
    }
}