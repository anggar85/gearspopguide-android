package com.mxlapps.app.gearspopguide.Model;

public class SkillModel {

    private Integer id;
    private String skill;
    private Integer skillOrder;
    private String desc;
    private String lvlUpgrades;
    private String name;
    private String skillIcon;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public Integer getSkillOrder() {
        return skillOrder;
    }

    public void setSkillOrder(Integer skillOrder) {
        this.skillOrder = skillOrder;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLvlUpgrades() {
        return lvlUpgrades;
    }

    public void setLvlUpgrades(String lvlUpgrades) {
        this.lvlUpgrades = lvlUpgrades;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSkillIcon() {
        return skillIcon;
    }

    public void setSkillIcon(String skillIcon) {
        this.skillIcon = skillIcon;
    }
}
