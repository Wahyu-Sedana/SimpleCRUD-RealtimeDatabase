package com.example.belajarcrud;

import android.widget.EditText;

public class Biodata {
    private String nama;
    private String no;
    private String alamat;
    private String key;

    public Biodata(){

    }

    public Biodata(String nama, String no, String alamat){
        this.nama = nama;
        this.no = no;
        this.alamat = alamat;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key){
        this.key = key;
    }
}
