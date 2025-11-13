package com.example.lab9;

public class CongViec {
    private int id;
    private String ten;
    private String noiDung;

    public CongViec() {}

    public CongViec(int id, String ten, String noiDung) {
        this.id = id;
        this.ten = ten;
        this.noiDung = noiDung;
    }

    public CongViec(String ten, String noiDung) {
        this.ten = ten;
        this.noiDung = noiDung;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTen() { return ten; }
    public void setTen(String ten) { this.ten = ten; }

    public String getNoiDung() { return noiDung; }
    public void setNoiDung(String noiDung) { this.noiDung = noiDung; }
}