package com.example.retaurant.DTO;

import java.time.LocalDateTime;

public class TestDTO {
    private int testID;
    private String testCode;
    private String testTitle;
    private int testTime;
    private int tpID;
    private int num_easy;
    private int num_medium;
    private int num_diff;
    private int testLimit;
    private LocalDateTime testDate;
    private int testStatus;

    public TestDTO() {
    }

    public TestDTO(int testID, String testCode, String testTitle, int testTime, int tpID, int num_easy, int num_medium,
            int num_diff, int testLimit, LocalDateTime testDate, int testStatus) {
        this.testID = testID;
        this.testCode = testCode;
        this.testTitle = testTitle;
        this.testTime = testTime;
        this.tpID = tpID;
        this.num_easy = num_easy;
        this.num_medium = num_medium;
        this.num_diff = num_diff;
        this.testLimit = testLimit;
        this.testDate = testDate;
        this.testStatus = testStatus;
    }

    public int getTestID() {
        return testID;
    }

    public void setTestID(int testID) {
        this.testID = testID;
    }

    public String getTestCode() {
        return testCode;
    }

    public void setTestCode(String testCode) {
        this.testCode = testCode;
    }

    public String getTestTitle() {
        return testTitle;
    }

    public void setTestTitle(String testTitle) {
        this.testTitle = testTitle;
    }

    public int getTestTime() {
        return testTime;
    }

    public void setTestTime(int testTime) {
        this.testTime = testTime;
    }

    public int getTpID() {
        return tpID;
    }

    public void setTpID(int tpID) {
        this.tpID = tpID;
    }

    public int getNum_easy() {
        return num_easy;
    }

    public void setNum_easy(int num_easy) {
        this.num_easy = num_easy;
    }

    public int getNum_medium() {
        return num_medium;
    }

    public void setNum_medium(int num_medium) {
        this.num_medium = num_medium;
    }

    public int getNum_diff() {
        return num_diff;
    }

    public void setNum_diff(int num_diff) {
        this.num_diff = num_diff;
    }

    public int getTestLimit() {
        return testLimit;
    }

    public void setTestLimit(int testLimit) {
        this.testLimit = testLimit;
    }

    public LocalDateTime getTestDate() {
        return testDate;
    }

    public void setTestDate(LocalDateTime testDate) {
        this.testDate = testDate;
    }

    public int getTestStatus() {
        return testStatus;
    }

    public void setTestStatus(int testStatus) {
        this.testStatus = testStatus;
    }

    @Override
    public String toString() {
        return "TestDTO{" + "testID=" + testID + ", testCode=" + testCode + ", testTitle=" + testTitle + ", testTime=" + testTime + ", tpID=" + tpID + ", num_easy=" + num_easy + ", num_medium=" + num_medium + ", num_diff=" + num_diff + ", testLimit=" + testLimit + ", testDate=" + testDate + ", testStatus=" + testStatus + '}';
    }
    
}
