/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.retaurant.utils;

/**
 *
 * @author light
 */
import java.text.Normalizer;
import java.util.regex.Pattern;

public class RemoveVn {
    public static String removeDiacritics(String input) {
        // Chuẩn hóa chuỗi theo dạng NFD
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        // Loại bỏ các ký tự dấu (thuộc tổ hợp Unicode)
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("");
    }
}
