
package com.example.retaurant.DTO;

import java.util.List;

public class ExamDTO {
    private String testCode;
    private String exOrder;
    private String exCode;
    private String[] ex_quesIDs;
    private List<Integer> quesIDList;
 // query quesIDS tra ve type List<int> ma DTO thi lai String[],cha biet co dung khong :))
    public ExamDTO() {
    }

    public ExamDTO(String testCode, String exOrder, String exCode, String[] ex_quesIDs) {
        this.testCode = testCode;
        this.exOrder = exOrder;
        this.exCode = exCode;
        this.ex_quesIDs = ex_quesIDs;
    }
    public ExamDTO(String testCode, String exOrder, String exCode, List<Integer> ex_quesIDs) {
        this.testCode = testCode;
        this.exOrder = exOrder;
        this.exCode = exCode;
        this.quesIDList = ex_quesIDs;
    }
    public String getTestCode() {
        return testCode;
    }

    public void setTestCode(String testCode) {
        this.testCode = testCode;
    }

    public String getExOrder() {
        return exOrder;
    }

    public void setExOrder(String exOrder) {
        this.exOrder = exOrder;
    }

    public String getExCode() {
        return exCode;
    }

    public void setExCode(String exCode) {
        this.exCode = exCode;
    }

    public String[] getEx_quesIDs() {
        return ex_quesIDs;
    }

    public void setEx_quesIDs(String[] ex_quesIDs) {
        this.ex_quesIDs = ex_quesIDs;
    }
    
    public List<Integer> getQuesIDList() {
        return this.quesIDList;
    }
}