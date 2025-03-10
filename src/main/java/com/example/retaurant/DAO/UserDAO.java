package com.example.retaurant.DAO;

import com.example.retaurant.DTO.UserDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.example.retaurant.interfaces.CrudInterface;
import com.example.retaurant.utils.DBConnection;

public class UserDAO implements CrudInterface<UserDTO> {

  public UserDTO getByUserEmail(String email) throws SQLException {
    String sql = "SELECT * FROM users WHERE userEmail = ?";
    try (Connection connection = DBConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql);) {

      ps.setString(1, email);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        UserDTO user = new UserDTO(rs.getInt("userID"), rs.getString("userName"), rs.getString("userPassword"),
            rs.getString("userEmail"), rs.getString("userFullName"), rs.getInt("isAdmin"));
        return user;
      }
      return null;
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    }
  }

  @Override
  public int create(UserDTO user) throws SQLException {
    String sql = "INSERT INTO users (userName, userPassword, userFullName, userEmail, isAdmin) VALUES (?, ?, ?, ?, ?)";
    try (Connection connection = DBConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql);) {

      ps.setString(1, user.getUserName());
      ps.setString(2, user.getUserPassword());
      ps.setString(3, user.getUserFullName());
      ps.setString(4, user.getUserEmail());
      ps.setInt(5, user.isAdmin());
      return ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    }
  }

  @Override
  public int update(UserDTO user) throws SQLException {

    String sql = "UPDATE users SET userPassword = ?, userFullName = ?, userEmail = ?, isAdmin = ? WHERE userID = ?";
    try (Connection connection = DBConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql);) {
      ps.setString(1, user.getUserPassword());
      ps.setString(2, user.getUserFullName());
      ps.setString(3, user.getUserEmail());
      ps.setInt(4, user.isAdmin());
      ps.setInt(5, user.getUserID());
      return ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    }
  }

  @Override
  public int delete(int id) throws SQLException {

    String sql = "DELETE FROM users WHERE userID = ?";
    try (Connection connection = DBConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql);) {
      ps.setInt(1, id);
      return ps.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    }
  }

  @Override
  public UserDTO read(int id) throws SQLException {

    String sql = "SELECT * FROM users WHERE userID=?";
    try (Connection connection = DBConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql);) {
      ps.setInt(1, id);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        return new UserDTO(rs.getInt("userID"), rs.getString("userName"), rs.getString("userPassword"),
            rs.getString("userEmail"), rs.getString("userFullName"), rs.getInt("isAdmin"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    }
    return null;
  }

  public ArrayList<UserDTO> getAll() throws SQLException {

    String sql = "SELECT * FROM users";
    try (Connection connection = DBConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql);) {
      ResultSet rs = ps.executeQuery();
      ArrayList<UserDTO> users = new ArrayList<>();
      while (rs.next()) {
        users.add(
            new UserDTO(rs.getInt("userID"), rs.getString("userName"), rs.getString("userPassword"),
                rs.getString("userEmail"), rs.getString("userFullName"), rs.getInt("isAdmin")));
      }
      return users;
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    }
  }

  public int updateUserInfo(String userID, String email, String fullName) {
    String sql = "UPDATE users SET userEmail = ?, userFullName = ? WHERE userID = ?";
    try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
      ps.setString(1, email);
      ps.setString(2, fullName);
      ps.setString(3, userID);
      return ps.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return 0;
  }

  public int updatePassword(String userID, String password) {
    String sql = "UPDATE users SET userPassword = ? WHERE userID = ?";
    try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
      ps.setString(1, password);
      ps.setString(2, userID);
      return ps.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return 0;
  }

  public ArrayList<ArrayList<String>> getLichSuLamBai(String userID) {
    // rs_num, exCode, title, rs_mark, rs_date, topic
    ArrayList<ArrayList<String>> list = new ArrayList<>();
    String sql = "SELECT r.*, t.testTitle AS title, tp.tpTitle AS topic\r\n" + //
        "FROM result r, exams e, test t, topics tp\r\n" + //
        "WHERE e.testCode = t.testCode AND r.exCode = e.exCode AND t.tpID = tp.tpID AND r.userID = ?" +
        "ORDER BY rs_date DESC";
    try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
      ps.setString(1, userID);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        ArrayList<String> row = new ArrayList<>();
        row.add(rs.getString("rs_num"));
        row.add(rs.getString("exCode"));
        row.add(rs.getString("title"));
        row.add(rs.getString("rs_mark"));
        row.add(rs.getString("rs_date"));
        row.add(rs.getString("topic"));
        list.add(row);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return list;
  }

  public ArrayList<ArrayList<String>> searchLichSuLamBai(String userID, String keyword) {
    // rs_num, exCode, title, rs_mark, rs_date, topic
    ArrayList<ArrayList<String>> list = new ArrayList<>();
    String sql = "SELECT r.*, t.testTitle AS title, tp.tpTitle AS topic\r\n" + //
        "FROM result r, exams e, test t, topics tp\r\n" + //
        "WHERE e.testCode = t.testCode AND r.exCode = e.exCode AND t.tpID = tp.tpID AND r.userID = ? AND (t.testTitle LIKE ? OR tp.tpTitle LIKE ?)";
    try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
      ps.setString(1, userID);
      ps.setString(2, "%" + keyword + "%");
      ps.setString(3, "%" + keyword + "%");
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        ArrayList<String> row = new ArrayList<>();
        row.add(rs.getString("rs_num"));
        row.add(rs.getString("exCode"));
        row.add(rs.getString("title"));
        row.add(rs.getString("rs_mark"));
        row.add(rs.getString("rs_date"));
        row.add(rs.getString("topic"));
        list.add(row);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return list;
  }
}
