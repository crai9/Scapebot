
package com.craiig.scapebot.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Activity {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("details")
    @Expose
    private String details;
    @SerializedName("text")
    @Expose
    private String text;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
