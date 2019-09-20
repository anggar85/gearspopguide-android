package com.mxlapps.app.gearspopguide.Model;

import com.google.gson.annotations.SerializedName;

public class ItemsModel {

    @SerializedName("image")
    private String item_image;
    @SerializedName("title")
    private String item_title;

    private String desc;

    public String getItem_title() {
        return item_title;
    }

    public void setItem_title(String item_title) {
        this.item_title = item_title;
    }

    public String getItem_image() {
        return item_image;
    }

    public void setItem_image(String item_image) {
        this.item_image = item_image;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
