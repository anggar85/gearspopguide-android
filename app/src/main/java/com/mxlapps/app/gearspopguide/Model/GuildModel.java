package com.mxlapps.app.gearspopguide.Model;

public class GuildModel {


    private Integer user_id;
    private Integer guild_id;
    private String guild_name;
    private String user;
    private Integer lvl;
    private String desc;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getGuild_id() {
        return guild_id;
    }

    public void setGuild_id(Integer guild_id) {
        this.guild_id = guild_id;
    }

    public String getGuild_name() {
        return guild_name;
    }

    public void setGuild_name(String guild_name) {
        this.guild_name = guild_name;
    }

    public Integer getLvl() {
        return lvl;
    }

    public void setLvl(Integer lvl) {
        this.lvl = lvl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
