package com.example.appchat_firebase;

public class UserOj {
    private  String id;
    private String email;
    private String firstName;
    private String lastName;
    private String sdt;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;
    private boolean gioiTinh,trangThai;

    public UserOj(){}

    public UserOj(String id, String email, String firstName, String lastName, String sdt, String password, boolean gioiTinh, boolean trangThai) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sdt = sdt;
        this.password = password;
        this.gioiTinh = gioiTinh;
        this.trangThai = trangThai;
    }

    public String getId() {
        return id;
    }

    public String getEmail() { return email; }

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

    public void setEmail(String email){
        this.email = email;
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
