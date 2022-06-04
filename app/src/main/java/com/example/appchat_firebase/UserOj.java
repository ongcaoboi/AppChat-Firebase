package com.example.appchat_firebase;

public class UserOj {
    private  String id,firstName,lastName,sdt;
    private boolean gioiTinh,trangThai;

    public UserOj(String id, String firstName, String lastName, String sdt, boolean gioiTinh, boolean trangThai) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sdt = sdt;
        this.gioiTinh = gioiTinh;
        this.trangThai = trangThai;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }
}