package com.example.retaurant.DTO;

public class UserDTO {
  private int userID;
  private String userPassword;
  private String userName;
  private String userEmail;
  private String userFullName;
  private int isAdmin;

  public UserDTO() {
  }

  public UserDTO(int id, String username, String password, String email, String fullName, int isAdmin) {
    this.userID = id;
    this.userName = username;
    this.userPassword = password;
    this.userEmail = email;
    this.userFullName = fullName;
    this.isAdmin = isAdmin;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getUserPassword() {
    return userPassword;
  }

  public void setUserPassword(String userPassword) {
    this.userPassword = userPassword;
  }

  public int getUserID() {
    return userID;
  }

  public void setUserID(int userID) {
    this.userID = userID;
  }

  public String getUserEmail() {
    return userEmail;
  }

  public void setUserEmail(String userEmail) {
    this.userEmail = userEmail;
  }

  public String getUserFullName() {
    return userFullName;
  }

  public void setUserFullName(String userFullName) {
    this.userFullName = userFullName;
  }

  public int isAdmin() {
    return isAdmin;
  }

  public void setIsAdmin(int admin) {
    isAdmin = admin;
  }

  @Override
  public String toString() {
    // json
    return "{" +
        "\"userID\":" + userID +
        ", \"userPassword\":\"" + userPassword + '\"' +
        ", \"userName\":\"" + userName + '\"' +
        ", \"userEmail\":\"" + userEmail + '\"' +
        ", \"userFullName\":\"" + userFullName + '\"' +
        ", \"isAdmin\":" + isAdmin +
        '}';
  }
}
