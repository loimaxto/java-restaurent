/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.retaurant.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author light
 */
public class Validation {
    public static boolean isValidPhone(String number) {
        String regex = "^\\d{9}"; // 9 number is contain
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }
    
    public static boolean isValidPlainString(String s) {
        String regex = "^[a-zA-Z]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);
        return matcher.matches();
    }
    
}
