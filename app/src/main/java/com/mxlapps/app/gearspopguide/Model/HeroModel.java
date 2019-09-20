package com.mxlapps.app.gearspopguide.Model;

import java.util.ArrayList;

public class HeroModel {

    private Integer id;
    private String name;
    private String desc;
    private String introduction;
    private String group;
    private String race_name;
    private Integer race;
    private String role;
    private String lore;
    private String synergy;
    private boolean isFood;
    private String position;
    private String artifact;
    private String union;
    private String type;
    private String classe;
    private String avatar;
    private String smallImage;
    private String iconImage;
    private String bigImage;
    private String rarity;
    private String section;
    private TierDataModel early;
    private TierDataModel mid;
    private TierDataModel late;
    private ArrayList<SkillModel> skills;
    private ArrayList<StrengthWeaknessModel> strengths;
    private ArrayList<StrengthWeaknessModel> weaknesses;

    public String getLore() {
        return lore;
    }

    public void setLore(String lore) {
        this.lore = lore;
    }

    public boolean isFood() {
        return isFood;
    }

    public void setFood(boolean food) {
        isFood = food;
    }

    public ArrayList<StrengthWeaknessModel> getStrengths() {
        return strengths;
    }

    public void setStrengths(ArrayList<StrengthWeaknessModel> strengths) {
        this.strengths = strengths;
    }

    public ArrayList<StrengthWeaknessModel> getWeaknesses() {
        return weaknesses;
    }

    public void setWeaknesses(ArrayList<StrengthWeaknessModel> weaknesses) {
        this.weaknesses = weaknesses;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIconImage() {
        return iconImage;
    }

    public void setIconImage(String iconImage) {
        this.iconImage = iconImage;
    }

    public TierDataModel getEarly() {
        return early;
    }

    public void setEarly(TierDataModel early) {
        this.early = early;
    }

    public TierDataModel getMid() {
        return mid;
    }

    public void setMid(TierDataModel mid) {
        this.mid = mid;
    }

    public TierDataModel getLate() {
        return late;
    }

    public void setLate(TierDataModel late) {
        this.late = late;
    }

    public ArrayList<SkillModel> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<SkillModel> skills) {
        this.skills = skills;
    }

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

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getRace_name() {
        return race_name;
    }

    public void setRace_name(String race_name) {
        this.race_name = race_name;
    }

    public Integer getRace() {
        return race;
    }

    public void setRace(Integer race) {
        this.race = race;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSynergy() {
        return synergy;
    }

    public void setSynergy(String synergy) {
        this.synergy = synergy;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getArtifact() {
        return artifact;
    }

    public void setArtifact(String artifact) {
        this.artifact = artifact;
    }

    public String getUnion() {
        return union;
    }

    public void setUnion(String union) {
        this.union = union;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSmallImage() {
        return smallImage;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public void setSmallImage(String smallImage) {
        this.smallImage = smallImage;
    }

    public String getBigImage() {
        return bigImage;
    }

    public void setBigImage(String bigImage) {
        this.bigImage = bigImage;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }
}
