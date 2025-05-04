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
    public NhanVien getNhanVienByTaiKhoanId(int tkId) {
        return nvDAO.getNhanVienBYMaTaiKhoanNhanVien(tkId);
    }
    public ArrayList<NhanVien> getDanhSachNhanVien() {
        if (this.listNhanVien == null) {
            docDanhSach();
        }
        return this.listNhanVien;
    }

    public boolean themNhanVien(String hoTen, String gioiTinh, String chucVu, String maTK) {
        hoTen = hoTen.trim();
        gioiTinh = gioiTinh.trim();
        chucVu = chucVu.trim();

        Integer maTaiKhoanInt = Integer.parseInt(maTK);
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
        nv.setMaTaiKhoan(maTaiKhoanInt);
        System.out.println("bus: " + nv.toString());
        boolean flag = nvDAO.themNhanVien(nv);
        if (!flag) {
            new MyDialog("Thêm thất bại!", MyDialog.ERROR_DIALOG);
        } else {
            new MyDialog("Thêm thành công!", MyDialog.SUCCESS_DIALOG);
        }
        return flag;
    }

    public boolean updateNhanVien(String ma, String hoTen, String gioiTinh, String chucVu, String maTK) {
        int maNV;
        int maTaiKhoan;

        try {
            maNV = Integer.parseInt(ma.trim());
            maTaiKhoan = Integer.parseInt(maTK.trim());
        } catch (NumberFormatException e) {
            new MyDialog("Mã nhân viên hoặc mã tài khoản "
                    + "không đúng định dạng!", MyDialog.ERROR_DIALOG);
            return false;
        }

        hoTen = hoTen.trim();
        gioiTinh = gioiTinh.trim();
        chucVu = chucVu.trim();

        if (hoTen.isEmpty()) {
            new MyDialog("Tên không được để trống!", MyDialog.ERROR_DIALOG);
            return false;
        }

        if (chucVu.isEmpty()) {
            new MyDialog("Chức vụ không được để trống!", MyDialog.ERROR_DIALOG);
            return false;
        }

        if (!gioiTinh.equalsIgnoreCase("Nam") && !gioiTinh.equalsIgnoreCase("Nữ")) {
            new MyDialog("Giới tính phải là 'Nam' hoặc 'Nữ'!", MyDialog.ERROR_DIALOG);
            return false;
        }

        NhanVien nv = new NhanVien();
        nv.setMaNhanvien(maNV);
        nv.setHoTen(hoTen);
        nv.setGioiTinh(gioiTinh);
        nv.setChucVu(chucVu);
        nv.setMaTaiKhoan(maTaiKhoan);

        boolean updated = nvDAO.updateNhanVien(nv);

        if (updated) {
            new MyDialog("Cập nhật thành công!", MyDialog.SUCCESS_DIALOG);
        } else {
            new MyDialog("Cập nhật thất bại! Kiểm tra lại cơ sở dữ liệu.", MyDialog.ERROR_DIALOG);
        }

        return updated;
    }

    public ArrayList<NhanVien> timNhanVien(String tuKhoa) {
        tuKhoa = tuKhoa.toLowerCase();
        ArrayList<NhanVien> dsnv = new ArrayList<>();
        for (NhanVien nv : listNhanVien) {
            if (nv.getHoTen().toLowerCase().contains(tuKhoa) || nv.getGioiTinh().toLowerCase().contains(tuKhoa)
                    || nv.getChucVu().toLowerCase().contains(tuKhoa)) {
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

    public boolean nhapExcel(String hoTen, String gioiTinh, String chucVu) {
        NhanVien nv = new NhanVien();
        nv.setHoTen(hoTen);
        nv.setGioiTinh(gioiTinh);
        nv.setChucVu(chucVu);
        boolean flag = nvDAO.nhapExcel(nv);
        return flag;
    }
}
