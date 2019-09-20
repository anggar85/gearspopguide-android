package com.mxlapps.app.gearspopguide.Model;

public class NewsModel {
    private String title;
    private String date;
    private String imageLink;
    private String desc;
    private String buttonText;
    private boolean buttonEnabled;
    private String buttonlink;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public boolean isButtonEnabled() {
        return buttonEnabled;
    }

    public void setButtonEnabled(boolean buttonEnabled) {
        this.buttonEnabled = buttonEnabled;
    }

    public String getButtonlink() {
        return buttonlink;
    }

    public void setButtonlink(String buttonlink) {
        this.buttonlink = buttonlink;
    }
}
