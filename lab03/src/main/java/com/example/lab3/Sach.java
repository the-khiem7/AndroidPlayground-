package com.example.lab3;


public class Sach {
    private String Ten;
    private String TheLoai;
    private int Hinh;

    public Sach(String ten, String theLoai, int hinh) {
        Ten = ten;
        TheLoai = theLoai;
        Hinh = hinh;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public String getTheLoai() {
        return TheLoai;
    }

    public void setTheLoai(String theLoai) {
        TheLoai = theLoai;
    }

    public int getHinh() {
        return Hinh;
    }

    public void setHinh(int hinh) {
        Hinh = hinh;
    }
}

