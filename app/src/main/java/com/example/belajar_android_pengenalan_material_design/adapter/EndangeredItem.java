package com.example.belajar_android_pengenalan_material_design.adapter;

public class EndangeredItem {
    private String mName;
    private int mThumbnail;

    /*Define Method getter Name to retrieve from the name value*/
    public String getName(){
        return mName;
    }
    /*Define Method Setter name to provide the value of the name*/
    public void setName(String name){
        this.mName = name;
    }
    /*Define Method Getter Thumbnail to retrieve from the name value*/
    public int getThumbnail() {
        return mThumbnail;
    }
    /*Define Method Setter name to provide  the value of the name*/
    public void setThumbnail(int thumbnail){
        this.mThumbnail = thumbnail;
    }
}
