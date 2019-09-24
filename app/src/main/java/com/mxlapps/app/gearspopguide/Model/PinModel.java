package com.mxlapps.app.gearspopguide.Model;


import java.util.ArrayList;

public class PinModel {


    private Integer id;
    private String name;
    private String desc;
    private String race;
    private String role;
    private String type;
    private String skillName;
    private String skillDesc;
    private Integer cost;
    private ArrayList<String> strong;
    private ArrayList<String> weak;
    private Boolean coverable;
    private String smallImage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getSkillDesc() {
        return skillDesc;
    }

    public void setSkillDesc(String skillDesc) {
        this.skillDesc = skillDesc;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public ArrayList<String> getStrong() {
        return strong;
    }

    public void setStrong(ArrayList<String> strong) {
        this.strong = strong;
    }

    public ArrayList<String> getWeak() {
        return weak;
    }

    public void setWeak(ArrayList<String> weak) {
        this.weak = weak;
    }

    public Boolean getCoverable() {
        return coverable;
    }

    public void setCoverable(Boolean coverable) {
        this.coverable = coverable;
    }

    public String getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(String smallImage) {
        this.smallImage = smallImage;
    }
}
