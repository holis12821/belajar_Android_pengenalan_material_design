package com.example.belajar_android_pengenalan_material_design.POJO;

import android.os.Parcel;
import android.os.Parcelable;

public class UserDiagnosa implements Parcelable {
    private String key;
    private String npm;
    private String nama;
    private String jenis_kelamin;
    private String jurusan;
    private String universitas;
    private String tingkat_stres;
    private String solusi;
    private String persentase;

    public UserDiagnosa(String key, String npm, String nama, String jenis_kelamin, String jurusan, String universitas,
                        String tingkat_stres, String solusi, String persentase) {
        this.key = key;
        this.npm = npm;
        this.nama = nama;
        this.jenis_kelamin = jenis_kelamin;
        this.jurusan = jurusan;
        this.universitas = universitas;
        this.tingkat_stres = tingkat_stres;
        this.solusi = solusi;
        this.persentase = persentase;
    }

    public UserDiagnosa(){
        /*Empty Constructor*/
    }

    protected UserDiagnosa(Parcel in) {
        key = in.readString();
        npm = in.readString();
        nama = in.readString();
        jenis_kelamin = in.readString();
        jurusan = in.readString();
        universitas = in.readString();
        tingkat_stres = in.readString();
        solusi = in.readString();
        persentase = in.readString();
    }

    public static final Creator<UserDiagnosa> CREATOR = new Creator<UserDiagnosa>() {
        @Override
        public UserDiagnosa createFromParcel(Parcel in) {
            return new UserDiagnosa(in);
        }

        @Override
        public UserDiagnosa[] newArray(int size) {
            return new UserDiagnosa[size];
        }
    };

    public String getKey(){
        return this.key;
    }
    public String getNpm() {
        return npm;
    }

    public String getNama() {
        return nama;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public String getJurusan() {
        return jurusan;
    }

    public String getUniversitas() {
        return universitas;
    }

    public String getTingkat_stres() {
        return tingkat_stres;
    }

    public String getPersentase() {
        return persentase;
    }

    public String getSolusi() {
        return solusi;
    }

    public void setNpm(String npm) {
        this.npm = npm;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

    public void setUniversitas(String universitas) {
        this.universitas = universitas;
    }

    public void setTingkat_stres(String tingkat_stres) {
        this.tingkat_stres = tingkat_stres;
    }

    public void setPersentase(String persentase) {
        this.persentase = persentase;
    }

    public void setSolusi(String solusi) {
        this.solusi = solusi;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
        dest.writeString(npm);
        dest.writeString(nama);
        dest.writeString(jenis_kelamin);
        dest.writeString(jurusan);
        dest.writeString(universitas);
        dest.writeString(tingkat_stres);
        dest.writeString(solusi);
        dest.writeString(persentase);
    }
}
