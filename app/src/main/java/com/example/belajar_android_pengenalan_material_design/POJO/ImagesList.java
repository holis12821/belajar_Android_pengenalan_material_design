package com.example.belajar_android_pengenalan_material_design.POJO;

public class ImagesList {
    /*Define Field from class imagesList*/
    private String imageUrl;

    /*Define Constructor*/
    public ImagesList(String imageUrl){
        this.imageUrl = imageUrl;
    }
    /*Define Constructor Null*/
    public ImagesList(){

    }
    public String getImageUrl(){
        return this.imageUrl;
    }
    public void setImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
    }
}
