package com.mxlapps.app.gearspopguide.Model;

public class StrengthWeaknessModel {
    // type 1 = strength
    // type 2 = weakness
    private Integer type;
    private Integer hero_id;
    private String autor;
    private String desc;

    public Integer getType() {
        return type;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getHero_id() {
        return hero_id;
    }

    public void setHero_id(Integer hero_id) {
        this.hero_id = hero_id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
