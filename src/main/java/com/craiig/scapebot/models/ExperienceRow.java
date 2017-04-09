package com.craiig.scapebot.models;

/**
 * Created by Craig on 15/03/2017, 17:50.
 */
public class ExperienceRow {

    private String xp;
    private String skill;

    public ExperienceRow(String xp, String skill){
        this.skill = skill;
        this.xp = xp;
    }

    public String getSkill(){
        return this.skill;
    }

    public String getXp(){
        return this.xp;
    }

    public void getSkill(String skill){
        this.skill = skill;
    }

    public void setXp(String xp){
        this.xp = xp;
    }

}
