package com.example.retaurant.DTO;

public class QuestionsDTO {
    private int qID;
    private String qContent;
    private String qPicture;
    private int qTopicID;
    private int qLevel;
    private int qStatus;


    // Contrstor không tham số
    public QuestionsDTO() {
    };

    // Constructor có tham số
    public QuestionsDTO(int qID, String qContent, String qPicture, int qTopicID, int qLevel, int qStatus) {
        this.qID = qID;
        this.qContent = qContent;
        this.qPicture = qPicture;
        this.qTopicID = qTopicID;
        this.qLevel = qLevel;
        this.qStatus = qStatus;
    }

    // Getter và Setter cho từng trường
    public int getqID() {
        return qID;
    }

    public void setqID(int qID) {
        this.qID = qID;
    }

    public String getqContent() {
        return qContent;
    }

    public void setqContent(String qContent) {
        this.qContent = qContent;
    }

    public String getqPicture() {
        return qPicture;
    }

    public void setqPicture(String qPicture) {
        this.qPicture = qPicture;
    }

    public int getqTopicID() {
        return qTopicID;
    }

    public void setqTopicID(int qTopicID) {
        this.qTopicID = qTopicID;
    }

    public int getqLevel() {
        return qLevel;
    }

    public void setqLevel(int qLevel) {
        this.qLevel = qLevel;
    }

    public int getqStatus() {
        return qStatus;
    }

    public void setqStatus(int qStatus) {
        this.qStatus = qStatus;
    }

    @Override
    public String toString() {
        return "QuestionsDTO{" + "qID=" + qID + ", qContent=" + qContent + ", qPicture=" + qPicture + ", qTopicID=" + qTopicID + ", qLevel=" + qLevel + ", qStatus=" + qStatus + '}';
    }
    
}
