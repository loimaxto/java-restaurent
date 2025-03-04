/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.retaurant.DTO;

import java.sql.Timestamp;

/**
 *
 * @author light
 */
public class LogDTO {
    private int logID;
    private String logContent;
    private int logUserID;
    private String logExCode;
    private Timestamp logDate;

    public LogDTO() {
    }

    public LogDTO(int logID, String logContent, int logUserID, String logExCode, Timestamp logDate) {
        this.logID = logID;
        this.logContent = logContent;
        this.logUserID = logUserID;
        this.logExCode = logExCode;
        this.logDate = logDate;
    }

    public int getLogID() {
        return logID;
    }

    public void setLogID(int logID) {
        this.logID = logID;
    }

    public String getLogContent() {
        return logContent;
    }

    public void setLogContent(String logContent) {
        this.logContent = logContent;
    }

    public int getLogUserID() {
        return logUserID;
    }

    public void setLogUserID(int logUserID) {
        this.logUserID = logUserID;
    }

    public String getLogExCode() {
        return logExCode;
    }

    public void setLogExCode(String logExCode) {
        this.logExCode = logExCode;
    }

    public Timestamp getLogDate() {
        return logDate;
    }

    public void setLogDate(Timestamp logDate) {
        this.logDate = logDate;
    }

    @Override
    public String toString() {
        return "LogDTO{" +
                "logID=" + logID +
                ", logContent='" + logContent + '\'' +
                ", logUserID=" + logUserID +
                ", logExCode='" + logExCode + '\'' +
                ", logDate=" + logDate +
                '}';
    }
}

