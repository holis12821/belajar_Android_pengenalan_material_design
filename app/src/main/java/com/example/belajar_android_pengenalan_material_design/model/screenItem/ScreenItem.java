package com.example.belajar_android_pengenalan_material_design.model.screenItem;

public class ScreenItem {
    private String  Title, Description;
    private int ScreenImg;

    /*Create Constructor*/
    public ScreenItem(String title, String description, int screenImg){
        this.Title = title;
        this.Description = description;
        this.ScreenImg = screenImg;
    }

    public String getTitle() {
        return this.Title;
    }

    public String getDescription() {
        return this.Description;
    }

    public int getScreenImg() {
        return this.ScreenImg;
    }


}
