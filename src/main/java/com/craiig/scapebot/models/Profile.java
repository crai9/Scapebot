
package com.craiig.scapebot.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Profile {

    @SerializedName("magic")
    @Expose
    private Integer magic;
    @SerializedName("questsstarted")
    @Expose
    private Integer questsstarted;
    @SerializedName("totalskill")
    @Expose
    private Integer totalskill;
    @SerializedName("questscomplete")
    @Expose
    private Integer questscomplete;
    @SerializedName("questsnotstarted")
    @Expose
    private Integer questsnotstarted;
    @SerializedName("totalxp")
    @Expose
    private Long totalxp;
    @SerializedName("ranged")
    @Expose
    private Integer ranged;
    @SerializedName("activities")
    @Expose
    private List<Activity> activities = null;
    @SerializedName("skillvalues")
    @Expose
    private List<Skillvalue> skillvalues = null;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("rank")
    @Expose
    private Object rank;
    @SerializedName("melee")
    @Expose
    private Long melee;
    @SerializedName("combatlevel")
    @Expose
    private Integer combatlevel;

    public Integer getMagic() {
        return magic;
    }

    public void setMagic(Integer magic) {
        this.magic = magic;
    }

    public Integer getQuestsstarted() {
        return questsstarted;
    }

    public void setQuestsstarted(Integer questsstarted) {
        this.questsstarted = questsstarted;
    }

    public Integer getTotalskill() {
        return totalskill;
    }

    public void setTotalskill(Integer totalskill) {
        this.totalskill = totalskill;
    }

    public Integer getQuestscomplete() {
        return questscomplete;
    }

    public void setQuestscomplete(Integer questscomplete) {
        this.questscomplete = questscomplete;
    }

    public Integer getQuestsnotstarted() {
        return questsnotstarted;
    }

    public void setQuestsnotstarted(Integer questsnotstarted) {
        this.questsnotstarted = questsnotstarted;
    }

    public Long getTotalxp() {
        return totalxp;
    }

    public void setTotalxp(Long totalxp) {
        this.totalxp = totalxp;
    }

    public Integer getRanged() {
        return ranged;
    }

    public void setRanged(Integer ranged) {
        this.ranged = ranged;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    public List<Skillvalue> getSkillvalues() {
        return skillvalues;
    }

    public void setSkillvalues(List<Skillvalue> skillvalues) {
        this.skillvalues = skillvalues;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Object getRank() {
        return rank;
    }

    public void setRank(Object rank) {
        this.rank = rank;
    }

    public Long getMelee() {
        return melee;
    }

    public void setMelee(Long melee) {
        this.melee = melee;
    }

    public Integer getCombatlevel() {
        return combatlevel;
    }

    public void setCombatlevel(Integer combatlevel) {
        this.combatlevel = combatlevel;
    }

}
