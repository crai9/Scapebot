
package com.craiig.scapebot.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UrbanDictionaryResult {

    @SerializedName("tags")
    @Expose
    private java.util.List<String> tags = null;
    @SerializedName("result_type")
    @Expose
    private String resultType;
    @SerializedName("list")
    @Expose
    private java.util.List<com.craiig.scapebot.models.List> list = null;
    @SerializedName("sounds")
    @Expose
    private java.util.List<String> sounds = null;

    public java.util.List<String> getTags() {
        return tags;
    }

    public void setTags(java.util.List<String> tags) {
        this.tags = tags;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public java.util.List<com.craiig.scapebot.models.List> getList() {
        return list;
    }

    public void setList(java.util.List<com.craiig.scapebot.models.List> list) {
        this.list = list;
    }

    public java.util.List<String> getSounds() {
        return sounds;
    }

    public void setSounds(java.util.List<String> sounds) {
        this.sounds = sounds;
    }

}
