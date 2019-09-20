package com.mxlapps.app.gearspopguide.Model;

public class ContributorModel {
    private String name;
    private String type_contribution;
    private String desc;
    private String link;
    private String imagelink;
    private String buttonText;

    public String getButtontext() {
        return buttonText;
    }

    public void setButtontext(String buttonText) {
        this.buttonText = buttonText;
    }

    public String getImagelink() {
        return imagelink;
    }

    public void setImagelink(String imagelink) {
        this.imagelink = imagelink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType_contribution() {
        return type_contribution;
    }

    public void setType_contribution(String type_contribution) {
        this.type_contribution = type_contribution;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
