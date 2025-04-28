/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.retaurant.BUS;

import com.example.retaurant.DAO.CustomerDAO;
import com.example.retaurant.DTO.CustomerDTO;
import com.example.retaurant.MyCustom.MyDialog;
import com.example.retaurant.utils.DBConnection;
import com.example.retaurant.utils.Validation;
import java.sql.Connection;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author light
 */
public class CustomerBUS {

    static Connection connection;
    static CustomerDAO daoCustomer;
    MyDialog dialog ;
    
    public CustomerBUS() {
        connection = DBConnection.getConnection();
        daoCustomer = new CustomerDAO(connection);
    }

    public List<CustomerDTO> getAllCustomer() {
        try {
            return daoCustomer.getAllCustomers();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    public List<CustomerDTO> getSearchCustomersByPhone(String keyword) {
        try {
            return daoCustomer.getSearchCustomersByPhone(keyword);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    public CustomerDTO getCustomerByPhone(String phone) {
        try {
            if (!Validation.isValidPhone(phone)) {
              new  MyDialog("Nhập sai định dạng số điện thoại!",0);
              return null;
            }
            return daoCustomer.getCustomerByPhone(phone);
        } catch (SQLDataException ex) {
            Logger.getLogger(CustomerBUS.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    public CustomerDTO getCustomerById(int id ) {
        try {
            return daoCustomer.getCustomerById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public int addCustomer(CustomerDTO cust) {
        try {
            
            if( !isValidateCustomer(cust,true)) { 
                System.out.println("valie err");
                return -1;
            }   else {
                return daoCustomer.addCustomer(cust);
            }
            
           
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(CustomerBUS.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    public boolean updateCustomer(CustomerDTO cust) {
        try {
            if( isValidateCustomer(cust,false)) return daoCustomer.updateCustomer(cust);
            return false;
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(CustomerBUS.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public void delateCustomer(int idCust) {
        try {
            daoCustomer.deleteCustomer(idCust);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private boolean isValidateCustomer(CustomerDTO cust, boolean isAddAction) {
        String errMessage = "";
        if ( !Validation.isValidPlainString(cust.getHoKh())) {
            errMessage = "Sai định dạng họ";
            dialog = new MyDialog(errMessage, 1);
            return false;
        }
        if ( !Validation.isValidPlainString(cust.getTenKh())) {
            errMessage = "Sai định dạng tên";
            dialog = new MyDialog(errMessage, 1);
            return false;
        }
        if ( !Validation.isValidPhone(cust.getSdt())) {
            errMessage = "Điện thoại phải có 9 số";
            dialog = new MyDialog(errMessage, 0);
            return false;
        }
         
        try {
            Integer existCustomerId = daoCustomer.getIdOfExistPhone(cust.getSdt());
            if (existCustomerId != null && isAddAction){
                System.out.println(existCustomerId);
                errMessage = "Điện thoại thêm bị trùng";
                dialog = new MyDialog(errMessage, 1);
                return false;
            } else if ( existCustomerId != null && existCustomerId != cust.getKhId() && !isAddAction ){
                System.out.println(existCustomerId);
                errMessage = "Điện thoại sửa bị trùng với sdt khác";
                dialog = new MyDialog(errMessage, 1);
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Logger.getLogger(CustomerBUS.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }    
        return true;
    }
//    public static void main(String[] args) {
//        CustomerDTO t = new CustomerDTO(6, "123123111", "w", "t");
//        CustomerBUS bus = new CustomerBUS();
////        System.out.println("update: " + bus.updateCustomer(t));
//        System.out.println("add" + bus.addCustomer(t));
//    }
}
