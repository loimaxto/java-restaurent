/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.retaurant.BUS;

import com.example.retaurant.DAO.NhanVienDAO;
import com.example.retaurant.DTO.NhanVien;
import com.example.retaurant.MyCustom.MyDialog;
import java.util.ArrayList;
public class NhanVienBUS {
    private NhanVienDAO nvDAO = new NhanVienDAO();
    private ArrayList<NhanVien> listNhanVien = null;

    public NhanVienBUS() {
        docDanhSach();
    }

    public void docDanhSach() {
        this.listNhanVien = nvDAO.getDanhSachNhanVien();
    }

    public ArrayList<NhanVien> getDanhSachNhanVien() {
        if (this.listNhanVien == null)
            docDanhSach();
        return this.listNhanVien;
    }

    public boolean themNhanVien(String hoTen,String gioiTinh, String chucVu ,String maTK) {
        hoTen = hoTen.trim();
        gioiTinh = gioiTinh.trim();
        chucVu = chucVu.trim();
        int maTaiKhoan = Integer.parseInt(maTK); 
        if (hoTen.equals("")) {
            new MyDialog("Tên không được để trống!", MyDialog.ERROR_DIALOG);
            return false;
        }
        if (chucVu.equals("")) {
            new MyDialog("Chức vụ không được để trống!", MyDialog.ERROR_DIALOG);
            return false;
        }
        NhanVien nv = new NhanVien();
        nv.setHoTen(hoTen);
        nv.setGioiTinh(gioiTinh);
        nv.setChucVu(chucVu);
        nv.setMaTaiKhoan(maTaiKhoan);
        boolean flag = nvDAO.themNhanVien(nv);
        if (!flag) {
            new MyDialog("Thêm thất bại!", MyDialog.ERROR_DIALOG);
        } else {
            new MyDialog("Thêm thành công!", MyDialog.SUCCESS_DIALOG);
        }
        return flag;
    }

    public boolean updateNhanVien(String ma, String hoTen, String gioiTinh, String chucVu, String maTK) {
        int maNV = Integer.parseInt(ma);
        hoTen = hoTen.trim();
        gioiTinh = gioiTinh.trim();
        chucVu = chucVu.trim();
        int maTaiKhoan = Integer.parseInt(maTK);
        if (hoTen.equals("")) {
            new MyDialog("Tên không được để trống!", MyDialog.ERROR_DIALOG);
            return false;
        }
        if (chucVu.equals("")) {
            new MyDialog("Chức vụ không được để trống!", MyDialog.ERROR_DIALOG);
            return false;
        }
        NhanVien nv = new NhanVien();
        nv.setMaNhanvien(maNV);
        nv.setHoTen(hoTen);
        nv.setGioiTinh(gioiTinh);
        nv.setChucVu(chucVu);
        nv.setMaTaiKhoan(maTaiKhoan);
        boolean flag = nvDAO.updateNhanVien(nv);
        if (!flag) {
            new MyDialog("Cập nhập thất bại!", MyDialog.ERROR_DIALOG);
        } else {
            new MyDialog("Cập nhập thành công!", MyDialog.SUCCESS_DIALOG);
        }
        return flag;
    }

    public ArrayList<NhanVien> timNhanVien(String tuKhoa) {
        tuKhoa = tuKhoa.toLowerCase();
        ArrayList<NhanVien> dsnv = new ArrayList<>();
        for (NhanVien nv : listNhanVien) {
            if (nv.getHoTen().toLowerCase().contains(tuKhoa) || nv.getGioiTinh().toLowerCase().contains(tuKhoa) ||
                    nv.getChucVu().toLowerCase().contains(tuKhoa) ) {
                dsnv.add(nv);
            }
        }
        return dsnv;
    }

    public boolean xoaNhanVien(String ma) {
        try {
            int maNV = Integer.parseInt(ma);
            MyDialog dlg = new MyDialog("Bạn có chắc chắn muốn xoá?", MyDialog.WARNING_DIALOG);
            boolean flag = false;
            if (dlg.getAction() == MyDialog.OK_OPTION) {
                flag = nvDAO.deleteNhanVien(maNV);
                if (flag) {
                    new MyDialog("Xoá thành công!", MyDialog.SUCCESS_DIALOG);
                } else {
                    new MyDialog("Xoá thất bại!", MyDialog.ERROR_DIALOG);
                }
            }
            return flag;
        } catch (Exception e) {
            new MyDialog("Chưa chọn nhân viên!", MyDialog.ERROR_DIALOG);
        }
        return false;
    }

    public boolean nhapExcel(String hoTen, String gioiTinh, String chucVu, int maTaiKhoan) {
        NhanVien nv = new NhanVien();
        nv.setHoTen(hoTen);
        nv.setGioiTinh(gioiTinh);
        nv.setChucVu(chucVu);
        nv.setMaTaiKhoan(maTaiKhoan);
        boolean flag = nvDAO.nhapExcel(nv);
        return flag;
    }
}
