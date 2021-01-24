package com.example.belajar_android_pengenalan_material_design.model;

public class Gejala {
    private  String key;
    private String kode_gejala;
    private String nama_gejala;
    private double bobot_nilai_cf;

    public Gejala(String key){
        this.key = key;
    }

    public Gejala(){

    }

    public Gejala( String key,String kode_gejala ,String nama_gejala, double bobot_nilai_cf) {
        this.key = key;
        this.kode_gejala = kode_gejala;
        this.nama_gejala = nama_gejala;
        this.bobot_nilai_cf = bobot_nilai_cf;
    }
    public Gejala( String kode_gejala ,String nama_gejala, double bobot_nilai_cf) {
        this.kode_gejala = kode_gejala;
        this.nama_gejala = nama_gejala;
        this.bobot_nilai_cf = bobot_nilai_cf;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKode_gejala() {
        return kode_gejala;
    }

    public void setKode_gejala(String kode_gejala) {
        this.kode_gejala = kode_gejala;
    }

    public String getNama_gejala() {
        return nama_gejala;
    }

    public void setNama_gejala(String nama_gejala) {
        this.nama_gejala = nama_gejala;
    }

    public double getBobot_nilai_cf() {
        return bobot_nilai_cf;
    }

    public void setBobot_nilai_cf(double bobot_nilai_cf) {
        this.bobot_nilai_cf = bobot_nilai_cf;
    }
}
