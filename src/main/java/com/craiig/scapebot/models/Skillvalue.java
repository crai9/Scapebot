
package com.craiig.scapebot.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Skillvalue {

    @SerializedName("level")
    @Expose
    private Integer level;
    @SerializedName("xp")
    @Expose
    private Integer xp;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("rank")
    @Expose
    private Integer rank;

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getXp() {
        return xp;
    }

    public void setXp(Integer xp) {
        this.xp = xp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

}
