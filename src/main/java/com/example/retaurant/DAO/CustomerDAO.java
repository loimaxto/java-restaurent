/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.retaurant.DAO;

/**
 *
 * @author light
 */
import com.example.retaurant.DTO.CustomerDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class CustomerDAO {

    private Connection connection;

    public CustomerDAO(Connection connection) {
        this.connection = connection;
    }

    public CustomerDTO getCustomerById(Integer khId) throws SQLException {
        String sql = "SELECT * FROM khach_hang WHERE kh_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, khId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToCustomerDTO(resultSet);
                }
                return null;
            }
        }
    }

    public List<CustomerDTO> getAllCustomers() throws SQLException {
        String sql = "SELECT * FROM khach_hang";
        List<CustomerDTO> customers = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                customers.add(mapResultSetToCustomerDTO(resultSet));
            }
            return customers;
        }
    }

    public Integer addCustomer(CustomerDTO customer) throws SQLException {
        String sql = "INSERT INTO khach_hang (sdt, ten_kh) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, customer.getSdt());
            statement.setString(2, customer.getTenKh());
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Failed to retrieve generated key.");
                    }
                }
            } else {
                throw new SQLException("Failed to insert customer, no rows affected.");
            }
        }
    }

    public boolean updateCustomer(CustomerDTO customer) throws SQLException {
        String sql = "UPDATE khach_hang SET sdt = ?, ten_kh = ? WHERE kh_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, customer.getSdt());
            statement.setString(2, customer.getTenKh());
            statement.setObject(3, customer.getKhId());
            return statement.executeUpdate() > 0;
        }
    }

    public boolean deleteCustomer(Integer khId) throws SQLException {
        String sql = "DELETE FROM khach_hang WHERE kh_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, khId);
            return statement.executeUpdate() > 0;
        }
    }

    private CustomerDTO mapResultSetToCustomerDTO(ResultSet resultSet) throws SQLException {
        CustomerDTO customer = new CustomerDTO();
        customer.setKhId(resultSet.getObject("kh_id", Integer.class));
        customer.setSdt(resultSet.getString("sdt"));
        customer.setTenKh(resultSet.getString("ten_kh"));
        return customer;
    }
}
