/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.retaurant.DTO;

import java.sql.Date;

/**
 *
 * @author light
 */
public class SearchCriteria {
    private String logicChon;
    private String textCreator;
    private String textCustomer;
    private Date startDate;
    private Date endDate;

    // Constructor (optional, but good practice)
    public SearchCriteria(String logicChon, String textCreator, String textCustomer, Date startDate, Date endDate) {
        this.logicChon = logicChon;
        this.textCreator = textCreator;
        this.textCustomer = textCustomer;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters (important for accessing the values)
    public String getLogicChon() {
        return logicChon;
    }

    public String getTextCreator() {
        return textCreator;
    }

    public String getTextCustomer() {
        return textCustomer;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    // Setters (optional, depending on whether you need to modify the criteria later)
    public void setLogicChon(String logicChon) {
        this.logicChon = logicChon;
    }

    public void setTextCreator(String textCreator) {
        this.textCreator = textCreator;
    }

    public void setTextCustomer(String textCustomer) {
        this.textCustomer = textCustomer;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "SearchCriteria{" +
               "logicChon='" + logicChon + '\'' +
               ", textCreator='" + textCreator + '\'' +
               ", textCustomer='" + textCustomer + '\'' +
               ", startDate=" + startDate +
               ", endDate=" + endDate +
               '}';
    }
}
